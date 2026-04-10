package io.github.NationArchitect.controller.savemanager;

import java.util.List;

/**
 * Server-side save controller for cloud endpoints.
 */
public class SaveController {

    private final DatabaseManager databaseManager;

    public SaveController(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public boolean saveGame(String token, int slot, SaveData data) {
        String userId = extractValidatedUserId(token);
        String username = TokenService.extractUsername(token);
        databaseManager.saveGame(userId, username, slot, data);
        return true;
    }

    public SaveData loadGame(String token, int slot) {
        String userId = extractValidatedUserId(token);
        return databaseManager.loadGame(userId, slot);
    }

    public void deleteGame(String token, int slot) {
        String userId = extractValidatedUserId(token);
        databaseManager.deleteGame(userId, slot);
    }

    public int getLatestSaveSlot(String token) {
        String userId = extractValidatedUserId(token);
        return databaseManager.getLatestSaveSlot(userId);
    }

    public List<SaveData> getLatestSaves(String token) {
        String userId = extractValidatedUserId(token);
        return databaseManager.getLatestSaves(userId);
    }

    private String extractValidatedUserId(String token) {
        if (!TokenService.validateToken(token)) {
            throw new IllegalArgumentException("Invalid token");
        }
        return TokenService.extractUserId(token);
    }
}
