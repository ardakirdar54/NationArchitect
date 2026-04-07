package io.github.NationArchitect.model.Effect;

import io.github.NationArchitect.model.component.ComponentType;
import io.github.NationArchitect.model.metric.MetricType;

import java.util.EnumMap;

public class Effect {

    private final String name;
    private final String description;
    private final EnumMap<ComponentType, Double> relatedComponents;
    private final EnumMap<MetricType, Double> relatedMetrics;

    public Effect() {
        this.name = null;
        this.description = null;
        this.relatedComponents = new EnumMap<>(ComponentType.class);
        this.relatedMetrics = new EnumMap<>(MetricType.class);
    }

    public Effect(String name, String description,
        EnumMap<ComponentType, Double> relatedComponents, EnumMap<MetricType, Double> relatedMetrics) {
        this.name = name;
        this.description = description;
        this.relatedComponents = relatedComponents == null
            ? new EnumMap<>(ComponentType.class)
            : new EnumMap<>(relatedComponents);
        this.relatedMetrics = relatedMetrics == null
            ? new EnumMap<>(MetricType.class)
            : new EnumMap<>(relatedMetrics);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    protected void addMetricModifier(MetricType type, double value) {
        this.relatedMetrics.put(type, value);
    }

    protected void addComponentModifier(ComponentType type, double value) {
        this.relatedComponents.put(type, value);
    }

    public double getMetricModifier(MetricType type) {
        return this.relatedMetrics.getOrDefault(type, 0.0);
    }

    public double getComponentModifier(ComponentType type) {
        return this.relatedComponents.getOrDefault(type, 0.0);
    }

    public EnumMap<ComponentType, Double> getRelatedComponents() {
        return new EnumMap<>(relatedComponents);
    }

    public EnumMap<MetricType, Double> getRelatedMetrics() {
        return new EnumMap<>(relatedMetrics);
    }
}
