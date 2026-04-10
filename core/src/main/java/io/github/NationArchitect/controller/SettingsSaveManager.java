package io.github.NationArchitect.controller;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.io.File;
import java.util.Map;

public class SettingsSaveManager {

    private static final String SETTINGS_FILE_NAME = "settings.json";

    private final File settingsFile;
    private final Json json;

    public SettingsSaveManager() {
        this(resolveProjectRoot());
    }

    public SettingsSaveManager(File rootDirectory) {
        this.settingsFile = new File(rootDirectory, SETTINGS_FILE_NAME);
        this.json = new Json();
        this.json.setOutputType(JsonWriter.OutputType.json);
        this.json.setTypeName(null);
        this.json.setUsePrototypes(false);
    }

    public Settings load() {
        if (!settingsFile.isFile()) {
            return new Settings();
        }

        try {
            SettingsState state = json.fromJson(SettingsState.class, new FileHandle(settingsFile));
            return toSettings(state);
        } catch (Exception exception) {
            return new Settings();
        }
    }

    public void save(Settings settings) {
        SettingsState state = SettingsState.fromSettings(settings);
        settingsFile.getParentFile().mkdirs();
        json.toJson(state, new FileHandle(settingsFile));
    }

    private Settings toSettings(SettingsState state) {
        Settings settings = new Settings();
        if (state == null) {
            return settings;
        }

        settings.setSoundRate((int) Math.round(state.soundRate));
        settings.setMusicRate((int) Math.round(state.musicRate));
        settings.setAutoSave(state.autoSave);
        settings.setAutoSaveDurationMinutes((int) Math.round(state.autoSaveDurationMinutes <= 0 ? 5 : state.autoSaveDurationMinutes));
        settings.setKeyBindings(state.keyBindings);
        return settings;
    }

    private static File resolveProjectRoot() {
        File cursor = new File(System.getProperty("user.dir")).getAbsoluteFile();
        while (cursor != null) {
            if (new File(cursor, "settings.gradle").isFile()) {
                return cursor;
            }
            cursor = cursor.getParentFile();
        }
        return new File(System.getProperty("user.dir")).getAbsoluteFile();
    }

    private static class SettingsState {
        public double soundRate;
        public double musicRate;
        public boolean autoSave;
        public double autoSaveDurationMinutes;
        public Map<Settings.GameAction, Integer> keyBindings;

        static SettingsState fromSettings(Settings settings) {
            SettingsState state = new SettingsState();
            state.soundRate = settings.getSoundRate();
            state.musicRate = settings.getMusicRate();
            state.autoSave = settings.getAutoSave();
            state.autoSaveDurationMinutes = settings.getAutoSaveDurationMinutes();
            state.keyBindings = settings.getKeyBindings();
            return state;
        }
    }
}
