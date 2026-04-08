package io.github.NationArchitect.controller.savemanager;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.MongoCommandException;
import org.bson.Document;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.mongodb.client.model.Filters.eq;

/**
 * Persistence layer with optional MongoDB backing and in-memory fallback for tests.
 */
public class DatabaseManager {

    private final MongoClient client;
    private final MongoDatabase database;
    private final MongoCollection<Document> savesCollection;
    private final MongoCollection<Document> usersCollection;
    private final boolean useMongo;

    private final Map<String, Account> inMemoryUsers = new ConcurrentHashMap<String, Account>();
    private final Map<String, Map<Integer, SaveData>> inMemorySaves = new ConcurrentHashMap<String, Map<Integer, SaveData>>();

    public DatabaseManager() {
        this.client = null;
        this.database = null;
        this.savesCollection = null;
        this.usersCollection = null;
        this.useMongo = false;
    }

    public DatabaseManager(String connectionString, String databaseName) {
        this.client = MongoClients.create(connectionString);
        this.database = client.getDatabase(databaseName);
        this.savesCollection = database.getCollection("saves");
        this.usersCollection = database.getCollection("users");
        this.useMongo = true;

        // Fail fast when MongoDB is unreachable so cloud-save setup errors are obvious.
        this.database.runCommand(new Document("ping", 1));
        ensureIndexes();
    }

    public void saveGame(String userId, String username, int slot, SaveData data) {
        if (useMongo) {
            Document document = data.dataToDocument(slot, username).append("userID", userId);
            savesCollection.deleteOne(new Document("userID", userId).append("slot", slot));
            savesCollection.insertOne(document);
            return;
        }

        Map<Integer, SaveData> userSaves = inMemorySaves.get(userId);
        if (userSaves == null) {
            userSaves = new ConcurrentHashMap<Integer, SaveData>();
            inMemorySaves.put(userId, userSaves);
        }
        userSaves.put(slot, data);
    }

    public SaveData loadGame(String userId, int slot) {
        if (useMongo) {
            Document document = savesCollection.find(new Document("userID", userId).append("slot", slot)).first();
            return SaveData.fromDocument(document);
        }

        Map<Integer, SaveData> userSaves = inMemorySaves.get(userId);
        return userSaves == null ? null : userSaves.get(slot);
    }

    public void deleteGame(String userId, int slot) {
        if (useMongo) {
            savesCollection.deleteOne(new Document("userID", userId).append("slot", slot));
            return;
        }

        Map<Integer, SaveData> userSaves = inMemorySaves.get(userId);
        if (userSaves != null) {
            userSaves.remove(slot);
        }
    }

    public int getLatestSaveSlot(String userId) {
        if (useMongo) {
            Document document = savesCollection.find(eq("userID", userId))
                .sort(new Document("saveTimestamp.epochMillis", -1))
                .first();
            if (document == null) {
                return -1;
            }
            Number slot = document.get("slot", Number.class);
            return slot == null ? -1 : slot.intValue();
        }

        Map<Integer, SaveData> userSaves = inMemorySaves.get(userId);
        if (userSaves == null || userSaves.isEmpty()) {
            return -1;
        }
        int latestSlot = -1;
        long latestTime = Long.MIN_VALUE;
        for (Map.Entry<Integer, SaveData> entry : userSaves.entrySet()) {
            SaveData value = entry.getValue();
            long time = value.getSaveTimestamp() == null ? 0L : value.getSaveTimestamp().getEpochMillis();
            if (time > latestTime) {
                latestTime = time;
                latestSlot = entry.getKey();
            }
        }
        return latestSlot;
    }

    public List<SaveData> getLatestSaves(String userId) {
        List<SaveData> saves = new ArrayList<SaveData>();
        if (useMongo) {
            for (Document document : savesCollection.find(eq("userID", userId))) {
                SaveData saveData = SaveData.fromDocument(document);
                if (saveData != null) {
                    saves.add(saveData);
                }
            }
        } else {
            Map<Integer, SaveData> userSaves = inMemorySaves.get(userId);
            if (userSaves != null) {
                saves.addAll(userSaves.values());
            }
        }

        saves.sort(new Comparator<SaveData>() {
            @Override
            public int compare(SaveData left, SaveData right) {
                long leftTime = left.getSaveTimestamp() == null ? 0L : left.getSaveTimestamp().getEpochMillis();
                long rightTime = right.getSaveTimestamp() == null ? 0L : right.getSaveTimestamp().getEpochMillis();
                return Long.compare(rightTime, leftTime);
            }
        });
        return saves;
    }

    public Account register(String username, String password) {
        if (findAccountByUsername(username) != null) {
            return null;
        }

        Account account = new Account(
            UUID.randomUUID().toString(),
            username,
            hashPassword(password),
            SaveDateTime.now()
        );

        if (useMongo) {
            Document document = new Document()
                .append("userID", account.getUserID())
                .append("username", account.getUsername())
                .append("passwordHash", account.getPasswordHash())
                .append("createdAt", new Document()
                    .append("epochMillis", account.getCreatedAt().getEpochMillis())
                    .append("hour", account.getCreatedAt().getHour())
                    .append("minute", account.getCreatedAt().getMinute()));
            usersCollection.insertOne(document);
        } else {
            inMemoryUsers.put(username, account);
        }

        return account;
    }

    public Account login(String username, String password) {
        Account account = findAccountByUsername(username);
        if (account == null) {
            return null;
        }
        return account.getPasswordHash().equals(hashPassword(password)) ? account : null;
    }

    public void close() {
        if (client != null) {
            client.close();
        }
    }

    private Account findAccountByUsername(String username) {
        if (useMongo) {
            Document document = usersCollection.find(eq("username", username)).first();
            if (document == null) {
                return null;
            }
            Document createdAt = document.get("createdAt", Document.class);
            return new Account(
                document.getString("userID"),
                document.getString("username"),
                document.getString("passwordHash"),
                createdAt == null
                    ? null
                    : new SaveDateTime(
                        createdAt.getLong("epochMillis"),
                        createdAt.getInteger("hour", 0),
                        createdAt.getInteger("minute", 0)
                    )
            );
        }
        return inMemoryUsers.get(username);
    }

    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encoded = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            for (byte value : encoded) {
                builder.append(String.format("%02x", value));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 not available", exception);
        }
    }

    private void ensureIndexes() {
        ensureIndex(
            usersCollection,
            new Document("username", 1),
            new IndexOptions().name("username_unique_idx").unique(true)
        );
        ensureIndex(
            savesCollection,
            new Document("userID", 1).append("slot", 1),
            new IndexOptions().name("user_slot_unique_idx").unique(true)
        );
    }

    private void ensureIndex(MongoCollection<Document> collection, Document keys, IndexOptions options) {
        if (hasEquivalentIndex(collection, keys, options)) {
            return;
        }
        try {
            collection.createIndex(keys, options);
        } catch (MongoCommandException exception) {
            if (!hasEquivalentIndex(collection, keys, options)) {
                throw exception;
            }
        }
    }

    private boolean hasEquivalentIndex(MongoCollection<Document> collection, Document keys, IndexOptions options) {
        Boolean requestedUnique = options.isUnique();
        for (Document index : collection.listIndexes()) {
            Document existingKeys = index.get("key", Document.class);
            if (!keys.equals(existingKeys)) {
                continue;
            }
            boolean existingUnique = Boolean.TRUE.equals(index.getBoolean("unique"));
            boolean expectedUnique = Boolean.TRUE.equals(requestedUnique);
            if (existingUnique == expectedUnique) {
                return true;
            }
        }
        return false;
    }
}
