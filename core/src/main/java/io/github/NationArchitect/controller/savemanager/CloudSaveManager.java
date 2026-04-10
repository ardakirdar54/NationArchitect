package io.github.NationArchitect.controller.savemanager;

import com.badlogic.gdx.utils.Json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Cloud save manager that talks to a REST API and stores data in Mongo-backed controllers.
 */
public class CloudSaveManager extends SaveManager {

    private final AuthService authService;

    public CloudSaveManager(String apiBaseUrl, AuthService authService, int maxSlots, long autoSaveInterval) {
        super(apiBaseUrl, maxSlots, autoSaveInterval);
        this.authService = authService;
    }

    @Override
    public boolean save(SaveData data, int slot) {
        validateSlot(slot);
        ensureLoggedIn();
        try {
            sendPostRequest("/saves/" + slot, data.toJson());
            currentSlot = slot;
            lastSaveTime = System.currentTimeMillis();
            return true;
        } catch (IOException exception) {
            return false;
        }
    }

    @Override
    public SaveData load(int slot) {
        validateSlot(slot);
        ensureLoggedIn();
        try {
            String body = sendGetRequest("/saves/" + slot);
            return body == null || body.isEmpty() ? null : SaveData.fromJson(body);
        } catch (IOException exception) {
            return null;
        }
    }

    @Override
    public void deleteSave(int slot) {
        validateSlot(slot);
        ensureLoggedIn();
        try {
            sendDeleteRequest("/saves/" + slot);
        } catch (IOException exception) {
            throw new IllegalStateException("Cloud delete failed", exception);
        }
    }

    @Override
    public PreviewData getSlotPreview(int slot) {
        SaveData data = load(slot);
        return data == null ? null : data.toPreviewData();
    }

    @Override
    public List<SaveData> getLatestSaves() {
        ensureLoggedIn();
        try {
            String body = sendGetRequest("/saves");
            if (body == null || body.isEmpty()) {
                return new ArrayList<SaveData>();
            }
            Json json = new Json();
            SaveData[] saves = json.fromJson(SaveData[].class, body);
            return saves == null ? new ArrayList<SaveData>() : new ArrayList<SaveData>(Arrays.asList(saves));
        } catch (IOException exception) {
            return new ArrayList<SaveData>();
        }
    }

    public int getLatestSaveSlot() {
        ensureLoggedIn();
        try {
            String body = sendGetRequest("/saves/latest");
            return Integer.parseInt(body.trim());
        } catch (IOException exception) {
            return -1;
        }
    }

    public String sendPostRequest(String endpoint, String body) throws IOException {
        return authService.sendRequest("POST", endpoint, body, authService.getAuthToken());
    }

    public String sendGetRequest(String endpoint) throws IOException {
        return authService.sendRequest("GET", endpoint, null, authService.getAuthToken());
    }

    public void sendDeleteRequest(String endpoint) throws IOException {
        authService.sendRequest("DELETE", endpoint, null, authService.getAuthToken());
    }

    private void ensureLoggedIn() {
        if (!authService.isLoggedIn()) {
            throw new IllegalStateException("You must login before using cloud saves.");
        }
    }
}
