package io.github.NationArchitect.controller;

import java.sql.Date;

import io.github.NationArchitect.model.land.Country;
import io.github.NationArchitect.model.metric.MetricType;

public class WinLoseManager {

    public enum GameResult {
        CONTINUE,
        LOSE,
        SANDBOX,
        WIN;
    }

    private Country country;

    public static final double MIN_HAPPINESS = 10.0;
    public static final double MIN_STABILITY = 20.0;
    public static final double MAX_CRIME = 60.0;
    public static final double MAX_UNEMPLOYMENT = 60.0;
    public static final Date REQUIRED_DATE_TO_WIN = Date.valueOf("2031-01-01");

    WinLoseManager(Country country) {
        this.country = country;
    }

    public GameResult checkGameState(Date date) {
        if (checkWinCondition(date)) {
            return GameResult.WIN;
        } else if (checkLoseCondition()) {
            return GameResult.LOSE;
        } else {
            return GameResult.CONTINUE;
        }
    }

    public boolean checkLoseCondition() {
        if (country.getMetricValue(MetricType.HAPPINESS) < MIN_HAPPINESS
                || country.getMetricValue(MetricType.STABILITY) < MIN_STABILITY
                || country.getMetricValue(MetricType.CRIME_RATE) > MAX_CRIME
                || country.getMetricValue(MetricType.UNEMPLOYMENT) > MAX_UNEMPLOYMENT) {
            return true;
        }
        return false;

    }

    public boolean checkWinCondition(Date date) {
        if (date.after(REQUIRED_DATE_TO_WIN)) {
            return true;
        }
        return false;
    }

}
