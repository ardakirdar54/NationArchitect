package io.github.NationArchitect.model.Effect;

import java.util.EnumMap;

import io.github.NationArchitect.model.component.ComponentType;
import io.github.NationArchitect.model.metric.MetricType;

public class Policy extends Effect{

    public Policy(String name, String description, 
        EnumMap<ComponentType, Double> relatedComponents, EnumMap<MetricType, Double> relatedMetrics) {
        super(name, description, relatedComponents, relatedMetrics);
    }
}
