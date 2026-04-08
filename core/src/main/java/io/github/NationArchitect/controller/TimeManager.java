package io.github.NationArchitect.controller;

import java.util.Date;

import io.github.NationArchitect.model.land.Country;

public class TimeManager {
    public enum GameSpeed {
        PAUSED(0f), // stopped game
        NORMAL(1f), // 1x speed
        FAST(2f), // 2x speed
        VERY_FAST(4f); // 4x speed

        public final float multiplier;

        GameSpeed(float multiplier) {
            this.multiplier = multiplier;
        }

    }

    private GameManager gameManager;

    private GameSpeed currentSpeed;

    private boolean isRunning;

    private float monthDuration;

    private float updateInterval;

    private float cycleAccumulator;

    private float updateAccumulator;

    private Country country;

    private boolean autoSave;

    private boolean autoSaveInterval;// ??

    private int currentDay;
    private int currentMonth;
    private int currentYear;

    TimeManager(GameManager gameManager) {
        this.gameManager = gameManager;
        this.monthDuration = 60f; // 60 seconds in real life = 1 month in the game.
        this.updateInterval = 1f;
        this.currentSpeed = GameSpeed.NORMAL;
        this.isRunning = false;
        this.cycleAccumulator = 0f;
        this.updateAccumulator = 0f;

    }

    public void start() {
        isRunning = true;
        currentSpeed = GameSpeed.NORMAL;
    }

    public void stop() {
        isRunning = false;
    }

    public void speedUp() {
        GameSpeed[] gameSpeeds = GameSpeed.values();
        int index = currentSpeed.ordinal();
        if (index < gameSpeeds.length - 1) {
            currentSpeed = gameSpeeds[index + 1];
        } else {
            currentSpeed = GameSpeed.NORMAL;
        }

    }

    public void update(float delta) {
        if (!isRunning || currentSpeed == GameSpeed.PAUSED)
            return;

        float scaledDelta = delta * currentSpeed.multiplier;

        cycleAccumulator += scaledDelta;
        updateAccumulator += scaledDelta;

        updateSimulationStep();
    }

    public void updateSimulationStep() {

    }

    public void onMonthEnd(Date date) {
        currentMonth++;
        if (currentMonth > 12) {
            currentMonth = 1;
            currentYear++;
        }
        currentDay = 1;
    }

    public void triggerAutoSave() {

    }

}
