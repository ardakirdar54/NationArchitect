package io.github.NationArchitect.model.component;

import java.util.EnumMap;

public enum ComponentType {
    ROAD_NETWORK("Enables land transportation and logistics."),
    ELECTRICITY("Provides power for industry and services."),
    WATER_MANAGEMENT("Ensures water supply and sanitation."),
    INTERNET("Improves communication and digital access."),
    EDUCATION("Increases knowledge and workforce quality."),
    SECURITY("Maintains public order and safety."),
    HEALTH_SERVICES("Improves population health and lifespan."),
    ROAD_TRANSPORT("Enables transport via trucks and cars."),
    RAILROAD_TRANSPORT("Supports large-scale rail transportation."),
    MARINE_TRANSPORT("Allows sea transport and trade."),
    AIR_TRANSPORT("Enables fast air transportation."),
    FACTORY("Produces industrial goods."),
    OFFICE("Supports administration and service sector."),
    TOURISM("Generates income from visitors."),
    AGRICULTURE("Produces food and raw materials.");

    private final String description;

    private final EnumMap<MetricType, Double> relatedMetrics;

    private final EnumMap<ComponentType, Double> relatedComponents;

    ComponentType(String description, EnumMap<MetricType, Double> relatedMetrics, EnumMap<ComponentType, Double> relatedComponents) {

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
