package io.github.NationArchitect.controller;

import java.util.EnumMap;

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

    public Settings() {
        soundRate = 70;
        musicRate = 70;
        autoSave = true;
        keyBindings = new EnumMap<>(GameAction.class);
    }

    public void setKeyBinding(GameAction action, int key) {
        keyBindings.put(action, key);
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

}
