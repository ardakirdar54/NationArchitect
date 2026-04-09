package io.github.NationArchitect.controller.savemanager;

import com.badlogic.gdx.files.FileHandle;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Local save manager backed by libGDX JSON files.
 */
public class JsonSaveManager extends SaveManager {

    public JsonSaveManager(int maxSlots, long autoSaveInterval) {
        this(SavePaths.PROJECT_SAVE_DIR, maxSlots, autoSaveInterval);
    }

    public JsonSaveManager(String savePath, int maxSlots, long autoSaveInterval) {
        super(savePath, maxSlots, autoSaveInterval);
        new File(savePath).mkdirs();
    }

    @Override
    public boolean save(SaveData data, int slot) {
        validateSlot(slot);
        FileHandle handle = getFileHandle(slot);
        handle.parent().mkdirs();
        handle.writeString(data.toJson(), false, "UTF-8");
        currentSlot = slot;
        lastSaveTime = System.currentTimeMillis();
        return true;
    }

    @Override
    public SaveData load(int slot) {
        validateSlot(slot);
        FileHandle handle = getFileHandle(slot);
        if (!handle.exists()) {
            return null;
        }
        try {
            currentSlot = slot;
            return SaveData.fromJson(handle.readString("UTF-8"));
        } catch (RuntimeException exception) {
            return null;
        }
    }

    @Override
    public void deleteSave(int slot) {
        validateSlot(slot);
        FileHandle handle = getFileHandle(slot);
        if (handle.exists()) {
            handle.delete();
        }
    }

    @Override
    public PreviewData getSlotPreview(int slot) {
        SaveData data = load(slot);
        return data == null ? null : data.toPreviewData();
    }

    @Override
    public List<SaveData> getLatestSaves() {
        List<SaveData> saves = new ArrayList<SaveData>();
        File directory = new File(savePath);
        if (!directory.exists()) {
            return saves;
        }
        File[] files = directory.listFiles();
        if (files == null) {
            return saves;
        }
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".json")) {
                try {
                    SaveData saveData = SaveData.fromJson(new FileHandle(file).readString("UTF-8"));
                    if (saveData != null) {
                        saves.add(saveData);
                    }
                } catch (RuntimeException ignored) {
                }
            }
        }
        saves.sort((left, right) -> {
            long leftTime = left.getSaveTimestamp() == null ? 0L : left.getSaveTimestamp().getEpochMillis();
            long rightTime = right.getSaveTimestamp() == null ? 0L : right.getSaveTimestamp().getEpochMillis();
            return Long.compare(rightTime, leftTime);
        });
        return saves;
    }

    @Override
    public int getLatestSaveSlot() {
        File directory = new File(savePath);
        if (!directory.exists()) {
            return -1;
        }
        File[] files = directory.listFiles();
        if (files == null) {
            return -1;
        }
        File latest = null;
        for (File file : files) {
            if (!file.isFile() || !file.getName().endsWith(".json")) {
                continue;
            }
            if (latest == null || file.lastModified() > latest.lastModified()) {
                latest = file;
            }
        }
        if (latest == null) {
            return -1;
        }
        String name = latest.getName().replace("slot-", "").replace(".json", "");
        return Integer.parseInt(name);
    }

    private FileHandle getFileHandle(int slot) {
        return new FileHandle(new File(savePath, "slot-" + slot + ".json"));
    }
}
