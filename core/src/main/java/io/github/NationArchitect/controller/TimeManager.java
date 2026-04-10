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

    private final GameManager gameManager;

    private GameSpeed currentSpeed;

    private boolean isRunning;

    private float monthDuration;

    private float updateInterval;

    private float cycleAccumulator;

    private float updateAccumulator;

    private float dayAccumulator;

    private final Country country;

    private boolean autoSave;

    private int currentDay;
    private int currentMonth;
    private int currentYear;

    public TimeManager(GameManager gameManager, Country country) {
        this.gameManager = gameManager;
        this.monthDuration = 60f; // 60 seconds in real life = 1 month in the game.
        this.updateInterval = 1f;
        this.currentSpeed = GameSpeed.NORMAL;
        this.isRunning = false;
        this.cycleAccumulator = 0f;
        this.updateAccumulator = 0f;
        this.dayAccumulator = 0f;
        this.country = country;
        this.currentDay = 1;
        this.currentMonth = 1;
        this.currentYear = 2026;
    }

    public GameSpeed getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(GameSpeed speed) {
        this.currentSpeed = speed;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public float getMonthDuration() {
        return monthDuration;
    }

    public void setMonthDuration(float duration) {
        this.monthDuration = duration;
    }

    public float getUpdateInterval() {
        return updateInterval;
    }

    public void setUpdateInterval(float interval) {
        this.updateInterval = interval;
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public int getCurrentMonth() {
        return currentMonth;
    }

    public int getCurrentYear() {
        return currentYear;
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
        if (!isRunning || currentSpeed == GameSpeed.PAUSED) {
            return;
        }

        float scaledDelta = delta * currentSpeed.multiplier;

        cycleAccumulator += scaledDelta;
        updateAccumulator += delta;
        dayAccumulator += scaledDelta;

        updateSimulationStep();
    }

    public void updateSimulationStep() {
        while (updateAccumulator >= updateInterval) {
            updateAccumulator -= updateInterval;
            gameManager.applyEconomyStep(updateInterval);
        }

        float dayDuration = monthDuration / 30f;
        while (dayAccumulator >= dayDuration) {
            dayAccumulator -= dayDuration;
            onDayEnd();
        }

    }

    public void onDayEnd() {
        currentDay++;
        if (currentDay > 30) {
            currentDay = 1;
            onMonthEnd(null);
            gameManager.simulationStep();
            if (gameManager.getSettings().getAutoSave()) {
                triggerAutoSave();
            }
        }
    }

    public void onMonthEnd(Date date) {
        currentMonth++;
        if (currentMonth > 12) {
            currentMonth = 1;
            currentYear++;
        }
    }

    public void triggerAutoSave() {
        gameManager.saveGame();
    }

}
