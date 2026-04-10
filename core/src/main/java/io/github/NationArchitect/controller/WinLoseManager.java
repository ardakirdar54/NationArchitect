package io.github.NationArchitect.controller;

import java.sql.Date;

import io.github.NationArchitect.model.land.Country;
import io.github.NationArchitect.model.metric.MetricType;

public class WinLoseManager {

    private static final int REQUIRED_CONSECUTIVE_LOSE_CHECKS = 3;

    public enum GameResult {
        CONTINUE,
        LOSE,
        SANDBOX,
        WIN;
    }

    private Country country;
    private int consecutiveLoseChecks;
    private String lastLoseReason;

    public static final double MIN_HAPPINESS = 10.0;
    public static final double MIN_STABILITY = 20.0;
    public static final double MAX_CRIME = 60.0;
    public static final double MAX_UNEMPLOYMENT = 60.0;
    public static final Date REQUIRED_DATE_TO_WIN = Date.valueOf("2031-01-01");

    WinLoseManager(Country country) {
        this.country = country;
        this.consecutiveLoseChecks = 0;
        this.lastLoseReason = "";
    }

    public GameResult checkGameState(Date date) {
        if (checkWinCondition(date)) {
            return GameResult.WIN;
        } else if (checkLoseCondition()) {
            consecutiveLoseChecks++;
            return consecutiveLoseChecks >= REQUIRED_CONSECUTIVE_LOSE_CHECKS
                ? GameResult.LOSE
                : GameResult.CONTINUE;
        } else {
            consecutiveLoseChecks = 0;
            lastLoseReason = "";
            return GameResult.CONTINUE;
        }
    }

    public boolean checkLoseCondition() {
        if (country == null) {
            lastLoseReason = "";
            return false;
        }

        double happiness = country.getMetricValue(MetricType.HAPPINESS);
        double stability = country.getMetricValue(MetricType.STABILITY);
        double crimeRate = country.getMetricValue(MetricType.CRIME_RATE);
        double unemployment = country.getMetricValue(MetricType.UNEMPLOYMENT);

        if (happiness < MIN_HAPPINESS) {
            lastLoseReason = String.format("National happiness collapsed to %.1f.", happiness);
            return true;
        }
        if (stability < MIN_STABILITY) {
            lastLoseReason = String.format("National stability collapsed to %.1f.", stability);
            return true;
        }
        if (crimeRate > MAX_CRIME) {
            lastLoseReason = String.format("Crime rate surged to %.1f.", crimeRate);
            return true;
        }
        if (unemployment > MAX_UNEMPLOYMENT) {
            lastLoseReason = String.format("Unemployment surged to %.1f.", unemployment);
            return true;
        }

        lastLoseReason = "";
        return false;
    }

    public boolean checkWinCondition(Date date) {
        if (date.after(REQUIRED_DATE_TO_WIN)) {
            return true;
        }
        return false;
    }

    public String getLastLoseReason() {
        return lastLoseReason == null ? "" : lastLoseReason;
    }

}
