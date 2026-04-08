package io.github.NationArchitect.controller.savemanager;

import io.github.NationArchitect.model.metric.MetricType;

public class MetricSnapshot {
    public MetricType type;
    public double value;
    public double lastMonthValue;

    public MetricSnapshot() {
    }

    public MetricSnapshot(MetricType type, double value, double lastMonthValue) {
        this.type = type;
        this.value = value;
        this.lastMonthValue = lastMonthValue;
    }
}
