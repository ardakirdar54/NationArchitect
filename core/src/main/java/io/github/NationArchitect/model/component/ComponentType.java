package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.metric.MetricType;
import java.util.EnumMap;
import java.util.Map;

/**
 * Lists all component types and their default metric and component relationships.
 */
public enum ComponentType {

    FACTORY("Produces industrial goods.",
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, -0.2
        )),
        new EnumMap<>(Map.of())
    ),

    OFFICE("Supports administration and service sector.",
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, 0.3
        )),
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
        new EnumMap<>(Map.of())
    ),

    ROAD_TRANSPORT("Enables transport via trucks and cars.",
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, 0.3
        )),
        new EnumMap<>(Map.of())
    ),

    RAIL_TRANSPORT("Supports large-scale rail transportation.",
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, 0.4
        )),
        new EnumMap<>(Map.of())
    ),

    MARINE_TRANSPORT("Allows sea transport and trade.",
        new EnumMap<>(Map.of()),
        new EnumMap<>(Map.of())
    ),

    AIR_TRANSPORT("Enables fast air transportation.",
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, 0.6
        )),
        new EnumMap<>(Map.of())
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

    /** Description of the component type. */
    private final String description;

    /** Metric relationships defined for the component type. */
    private final EnumMap<MetricType, Double> relatedMetrics;

    /** Component relationships defined for the component type. */
    private final EnumMap<ComponentType, Double> relatedComponents;

    /**
     * Creates the enum value definition for {@link ComponentType}.
     *
     * @param description human-readable summary of the enum value
     * @param relatedMetrics metric effects applied by the building type
     * @param relatedComponents component effects applied by the building type
     */
    ComponentType(String description, EnumMap<MetricType, Double> relatedMetrics, EnumMap<ComponentType, Double> relatedComponents) {
        this.description = description;
        this.relatedMetrics = relatedMetrics;
        this.relatedComponents = relatedComponents;
    }

    /**
     * Returns the component description
     *
     * @return description text
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the metric effects provided by component
     *
     * @return metric effect map
     */
    public EnumMap<MetricType, Double> getRelatedMetrics() {
        return new EnumMap<>(relatedMetrics);
    }

    /**
     * Returns the component effects provided by component
     *
     * @return component effect map
     */
    public EnumMap<ComponentType, Double> getRelatedComponents() {
        return new EnumMap<>(relatedComponents);
    }
}
