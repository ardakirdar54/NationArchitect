package io.github.NationArchitect.model.Effect;

import java.util.EnumMap;

import io.github.NationArchitect.model.component.ComponentType;
import io.github.NationArchitect.model.metric.MetricType;

public class PopUpChoice extends Effect{

    int duration;

    public PopUpChoice() {
        super();
    }

    public PopUpChoice(String name, String description,
        EnumMap<ComponentType, Double> relatedComponents, EnumMap<MetricType, Double> relatedMetrics) {
        super(name, description, relatedComponents, relatedMetrics);
    }

    public int getDuration(){
        return duration;
    }
}
