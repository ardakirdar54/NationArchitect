package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.metric.MetricType;
import java.util.EnumMap;
import java.util.Map;

public enum ComponentType {

    FACTORY("Produces industrial goods.",
        new EnumMap<>(Map.of()),
        new EnumMap<>(Map.of())
    ),

    OFFICE("Supports administration and service sector.",
        new EnumMap<>(Map.of()),
        new EnumMap<>(Map.of())
    ),

    TOURISM("Generates income from visitors.",
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, 0.6
        )),
        new EnumMap<>(Map.of())
    ),

    AGRICULTURE("Produces food and raw materials.",
        new EnumMap<>(Map.of(
            MetricType.HEALTH_RATE, 0.5,
            MetricType.HAPPINESS, 0.3
        )),
        new EnumMap<>(Map.of())
    ),

    HEALTH_SERVICES("Improves population health and lifespan.",
        new EnumMap<>(Map.of(
            MetricType.HEALTH_RATE, 1.0,
            MetricType.HAPPINESS, 0.3
        )),
        new EnumMap<>(Map.of(
            ComponentType.AGRICULTURE, 0.2
        ))
    ),

    EDUCATION("Increases knowledge and workforce quality.",
        new EnumMap<>(Map.of(
            MetricType.EDUCATION_LEVEL, 1.0,
            MetricType.HAPPINESS, 0.1
        )),
        new EnumMap<>(Map.of(
            ComponentType.OFFICE, 0.5,
            ComponentType.FACTORY, 0.4,
            ComponentType.AGRICULTURE, 0.3,
            ComponentType.HEALTH_SERVICES, 0.4
        ))
    ),

    SECURITY("Maintains public order and safety.",
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, 0.3
        )),
        new EnumMap<>(Map.of(
            ComponentType.TOURISM, 0.5,
            ComponentType.OFFICE, 0.2
        ))
    ),

    ROAD_TRANSPORT("Enables transport via trucks and cars.",
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, 0.3
        )),
        new EnumMap<>(Map.of(
            ComponentType.FACTORY, 0.3,
            ComponentType.AGRICULTURE, 0.3
        ))
    ),

    RAIL_TRANSPORT("Supports large-scale rail transportation.",
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, 0.4
        )),
        new EnumMap<>(Map.of(
            ComponentType.FACTORY, 0.5,
            ComponentType.AGRICULTURE, 0.4
        ))
    ),

    MARINE_TRANSPORT("Allows sea transport and trade.",
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, 0.2
        )),
        new EnumMap<>(Map.of(
            ComponentType.FACTORY, 0.7,
            ComponentType.AGRICULTURE, 0.6
        ))
    ),

    AIR_TRANSPORT("Enables fast air transportation.",
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, 0.6
        )),
        new EnumMap<>(Map.of(
            ComponentType.EDUCATION, 0.3,
            ComponentType.OFFICE, 0.4
        ))
    ),

    ROAD_NETWORK("Enables land transportation and logistics.",
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, 0.2
        )),
        new EnumMap<>(Map.of(
            ComponentType.ROAD_TRANSPORT, 0.4,
            ComponentType.RAIL_TRANSPORT, 0.3,
            ComponentType.SECURITY, 0.2,
            ComponentType.HEALTH_SERVICES, 0.2
        ))
    ),

    ELECTRICITY("Provides power for industry and services.",
        new EnumMap<>(Map.of()),
        new EnumMap<>(Map.of(
            ComponentType.FACTORY, 0.5,
            ComponentType.OFFICE, 0.4,
            ComponentType.AGRICULTURE, 0.3,
            ComponentType.HEALTH_SERVICES, 0.3,
            ComponentType.EDUCATION, 0.3,
            ComponentType.SECURITY, 0.2
        ))
        ),

    WATER_MANAGEMENT("Ensures water supply and sanitation.",
        new EnumMap<>(Map.of(
            MetricType.HEALTH_RATE, 0.5,
            MetricType.HAPPINESS, 0.2
        )),
        new EnumMap<>(Map.of(
            ComponentType.AGRICULTURE, 0.5,
            ComponentType.HEALTH_SERVICES, 0.4
        ))
    ),

    INTERNET("Improves communication and digital access.",
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, 0.2
        )),
        new EnumMap<>(Map.of(
            ComponentType.OFFICE, 0.6,
            ComponentType.EDUCATION, 0.4,
            ComponentType.SECURITY, 0.2
        ))
    );

    private final String description;

    private final EnumMap<MetricType, Double> relatedMetrics;

    private final EnumMap<ComponentType, Double> relatedComponents;

    ComponentType(String description, EnumMap<MetricType, Double> relatedMetrics, EnumMap<ComponentType, Double> relatedComponents) {
        this.description = description;
        this.relatedMetrics = relatedMetrics;
        this.relatedComponents = relatedComponents;
    }

    public String getDescription() {
        return description;
    }

    public EnumMap<MetricType, Double> getRelatedMetrics() {
        return new EnumMap<>(relatedMetrics);
    }

    public EnumMap<ComponentType, Double> getRelatedComponents() {
        return new EnumMap<>(relatedComponents);
    }
}
