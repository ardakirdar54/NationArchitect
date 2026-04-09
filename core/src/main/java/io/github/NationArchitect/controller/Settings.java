package io.github.NationArchitect.controller;

import java.util.EnumMap;
import java.util.Map;

import com.badlogic.gdx.Input;

public class Settings {

    public enum GameAction {
        INTERACT(Input.Keys.LEFT),
        PAUSE(Input.Keys.ESCAPE),
        SPEED_UP(Input.Keys.SPACE);

        public final int key;

        GameAction(int key) {
            this.key = key;
        }
    }

    private EnumMap<GameAction, Integer> keyBindings;

    private double soundRate;

    private double musicRate;

    private boolean autoSave;
    private int autoSaveDurationMinutes;

    public Settings() {
        soundRate = 70;
        musicRate = 70;
        autoSave = true;
        autoSaveDurationMinutes = 5;
        keyBindings = new EnumMap<>(GameAction.class);
        for (GameAction action : GameAction.values()) {
            keyBindings.put(action, action.key);
        }
    }

    public void setKeyBinding(GameAction action, int key) {
        keyBindings.put(action, key);
    }

    public int getKeyBinding(GameAction action) {
        return keyBindings.getOrDefault(action, action.key);
    }

    public EnumMap<GameAction, Integer> getKeyBindings() {
        return new EnumMap<>(keyBindings);
    }

    public void setKeyBindings(Map<GameAction, Integer> keyBindings) {
        this.keyBindings.clear();
        for (GameAction action : GameAction.values()) {
            this.keyBindings.put(action, action.key);
        }
        if (keyBindings == null) {
            return;
        }
        this.keyBindings.putAll(keyBindings);
    }

    public double getSoundRate() {
        return soundRate;
    }

    public void setSoundRate(int soundRate) {
        this.soundRate = soundRate;
    }

    public double getMusicRate() {
        return musicRate;
    }

    public void setMusicRate(int musicRate) {
        this.musicRate = musicRate;
    }

    public boolean getAutoSave() {
        return autoSave;
    }

    public void setAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
    }

    public int getAutoSaveDurationMinutes() {
        return autoSaveDurationMinutes;
    }

    public void setAutoSaveDurationMinutes(int autoSaveDurationMinutes) {
        this.autoSaveDurationMinutes = Math.max(1, Math.min(60, autoSaveDurationMinutes));
    }

}
