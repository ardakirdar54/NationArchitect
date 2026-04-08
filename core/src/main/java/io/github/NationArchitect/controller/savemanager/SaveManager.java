package io.github.NationArchitect.controller.savemanager;

import java.util.List;

/**
 * Shared contract for local and cloud save implementations.
 */
public abstract class SaveManager {

    protected String savePath;
    protected int currentSlot;
    protected int maxSlots;
    protected long lastSaveTime;
    protected long autoSaveInterval;

    protected SaveManager(String savePath, int maxSlots, long autoSaveInterval) {
        this.savePath = savePath;
        this.maxSlots = maxSlots;
        this.autoSaveInterval = autoSaveInterval;
        this.currentSlot = 0;
    }

    public abstract boolean save(SaveData data, int slot);

    public abstract SaveData load(int slot);

    public abstract void deleteSave(int slot);

    public abstract PreviewData getSlotPreview(int slot);

    public abstract List<SaveData> getLatestSaves();

    public int getLatestSaveSlot() {
        List<SaveData> saves = getLatestSaves();
        if (saves.isEmpty()) {
            return -1;
        }
        return currentSlot;
    }

    protected void validateSlot(int slot) {
        if (slot < 0 || slot >= maxSlots) {
            throw new IllegalArgumentException("Invalid save slot: " + slot);
        }
    }
}
