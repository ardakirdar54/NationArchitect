package io.github.NationArchitect.controller.savemanager;

import com.badlogic.gdx.utils.Json;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Small local REST server for manually testing cloud save flows.
 */
public class SaveDevServer {
    private static final int DEFAULT_PORT = 8085;
    private static final String DEFAULT_MONGO_URI = "mongodb://127.0.0.1:27017";
    private static final String DEFAULT_DATABASE_NAME = "nationarchitect";

    public static void main(String[] args) throws Exception {
        int port = resolvePort(args);
        String mongoUri = resolveMongoUri(args);
        String databaseName = resolveDatabaseName(args);

        ServerHandle handle = start(port, mongoUri, databaseName);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                handle.close();
            }
        }));
        System.out.println("SaveDevServer listening on http://127.0.0.1:" + port);
        System.out.println("MongoDB connection: " + mongoUri);
        System.out.println("MongoDB database: " + databaseName);
    }

    public static ServerHandle start(int port, String mongoUri, String databaseName) throws IOException {
        DatabaseManager databaseManager;
        try {
            databaseManager = new DatabaseManager(mongoUri, databaseName);
        } catch (RuntimeException exception) {
            databaseManager = new DatabaseManager();
            System.out.println("SaveDevServer falling back to in-memory storage: " + exception.getMessage());
        }
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        registerRoutes(server, databaseManager);
        server.start();
        return new ServerHandle(server, databaseManager, port, mongoUri, databaseName);
    }

    private static int resolvePort(String[] args) {
        if (args.length > 0 && args[0] != null && !args[0].trim().isEmpty()) {
            return Integer.parseInt(args[0]);
        }
        String envPort = System.getenv("SAVE_SERVER_PORT");
        if (envPort != null && !envPort.trim().isEmpty()) {
            return Integer.parseInt(envPort);
        }
        return DEFAULT_PORT;
    }

    private static String resolveMongoUri(String[] args) {
        if (args.length > 1 && args[1] != null && !args[1].trim().isEmpty()) {
            return args[1].trim();
        }
        String envUri = System.getenv("MONGODB_URI");
        if (envUri != null && !envUri.trim().isEmpty()) {
            return envUri.trim();
        }
        return DEFAULT_MONGO_URI;
    }

    private static String resolveDatabaseName(String[] args) {
        if (args.length > 2 && args[2] != null && !args[2].trim().isEmpty()) {
            return args[2].trim();
        }
        String envDatabase = System.getenv("MONGODB_DATABASE");
        if (envDatabase != null && !envDatabase.trim().isEmpty()) {
            return envDatabase.trim();
        }
        return DEFAULT_DATABASE_NAME;
    }

    private static void registerRoutes(HttpServer server, DatabaseManager databaseManager) {
        final Json json = new Json();
        final AuthController authController = new AuthController(databaseManager);
        final SaveController saveController = new SaveController(databaseManager);

        server.createContext("/auth/register", new JsonHandler() {
            @Override
            protected Response handleJson(String requestBody) {
                Credentials credentials = json.fromJson(Credentials.class, requestBody);
                AuthResponse response = authController.register(credentials.username, credentials.password);
                if (response == null) {
                    return new Response(409, "{\"error\":\"username-already-exists\"}");
                }
                return new Response(200, json.toJson(response));
            }
        });

        server.createContext("/auth/login", new JsonHandler() {
            @Override
            protected Response handleJson(String requestBody) {
                Credentials credentials = json.fromJson(Credentials.class, requestBody);
                AuthResponse response = authController.login(credentials.username, credentials.password);
                if (response == null) {
                    return new Response(401, "{\"error\":\"invalid-credentials\"}");
                }
                return new Response(200, json.toJson(response));
            }
        });

        server.createContext("/saves/latest", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                try {
                    String token = bearerToken(exchange.getRequestHeaders());
                    int latestSlot = saveController.getLatestSaveSlot(token);
                    writeResponse(exchange, 200, String.valueOf(latestSlot));
                } catch (RuntimeException exception) {
                    writeResponse(exchange, 401, "{\"error\":\"unauthorized\"}");
                }
            }
        });

        server.createContext("/saves", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                try {
                    String token = bearerToken(exchange.getRequestHeaders());
                    String path = exchange.getRequestURI().getPath();
                    if ("GET".equals(exchange.getRequestMethod()) && "/saves".equals(path)) {
                        List<SaveData> latest = saveController.getLatestSaves(token);
                        writeResponse(exchange, 200, json.toJson(latest.toArray(new SaveData[0])));
                        return;
                    }

                    String[] parts = path.split("/");
                    int slot = Integer.parseInt(parts[2]);
                    if ("POST".equals(exchange.getRequestMethod())) {
                        String requestBody = new String(readAll(exchange), StandardCharsets.UTF_8);
                        SaveData data = json.fromJson(SaveData.class, requestBody);
                        saveController.saveGame(token, slot, data);
                        writeResponse(exchange, 200, "{\"saved\":true}");
                        return;
                    }
                    if ("GET".equals(exchange.getRequestMethod())) {
                        SaveData saveData = saveController.loadGame(token, slot);
                        if (saveData == null) {
                            writeResponse(exchange, 404, "");
                            return;
                        }
                        writeResponse(exchange, 200, saveData.toJson());
                        return;
                    }
                    if ("DELETE".equals(exchange.getRequestMethod())) {
                        saveController.deleteGame(token, slot);
                        writeResponse(exchange, 200, "{\"deleted\":true}");
                        return;
                    }
                    writeResponse(exchange, 405, "");
                } catch (RuntimeException exception) {
                    writeResponse(exchange, 401, "{\"error\":\"unauthorized\"}");
                }
            }
        });
    }

    private static byte[] readAll(HttpExchange exchange) throws IOException {
        byte[] buffer = new byte[4096];
        int read;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while ((read = exchange.getRequestBody().read(buffer)) != -1) {
            outputStream.write(buffer, 0, read);
        }
        return outputStream.toByteArray();
    }

    private static String bearerToken(Headers headers) {
        String authorization = headers.getFirst("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Missing bearer token");
        }
        return authorization.substring("Bearer ".length());
    }

    private static void writeResponse(HttpExchange exchange, int statusCode, String responseBody) throws IOException {
        byte[] bytes = responseBody.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(bytes);
        }
    }

    private abstract static class JsonHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String requestBody = new String(readAll(exchange), StandardCharsets.UTF_8);
            Response response = handleJson(requestBody);
            writeResponse(exchange, response.statusCode, response.body);
        }

        protected abstract Response handleJson(String requestBody);
    }

    private static class Response {
        private final int statusCode;
        private final String body;

        private Response(int statusCode, String body) {
            this.statusCode = statusCode;
            this.body = body;
        }
    }

    private static class Credentials {
        private String username;
        private String password;
    }

    public static final class ServerHandle implements Closeable {
        private final HttpServer server;
        private final DatabaseManager databaseManager;
        private final int port;
        private final String mongoUri;
        private final String databaseName;

        private ServerHandle(HttpServer server, DatabaseManager databaseManager, int port, String mongoUri, String databaseName) {
            this.server = server;
            this.databaseManager = databaseManager;
            this.port = port;
            this.mongoUri = mongoUri;
            this.databaseName = databaseName;
        }

        public int getPort() {
            return port;
        }

        public String getMongoUri() {
            return mongoUri;
        }

        public String getDatabaseName() {
            return databaseName;
        }

        @Override
        public void close() {
            server.stop(0);
            databaseManager.close();
        }
    }
}
