package io.github.NationArchitect.model.Effect;

import io.github.NationArchitect.model.land.Country;
import io.github.NationArchitect.model.metric.MetricType;

public class TriggerCondition {

    public enum Operator {
        LESS_THAN, GREATER_THAN, EQUALS
    }

    private MetricType metricType;
    private Operator operator;
    private double value;

    public TriggerCondition() {

    }

    public boolean evaluate(Country country) {
        double targetMetricValue = country.getMetricValue(metricType);

        if (operator == null) return false;

        switch (operator) {
            case LESS_THAN:
                return targetMetricValue < value;
            case GREATER_THAN:
                return targetMetricValue > value;
            case EQUALS:
                return targetMetricValue == value;
            default:
                return false;
        }
    }
}
