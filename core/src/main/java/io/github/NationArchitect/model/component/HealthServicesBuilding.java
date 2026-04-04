package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.product.ProductType;
import io.github.NationArchitect.model.metric.*;
import java.util.EnumMap;

public enum HealthServicesBuilding implements BuildingType {

    CLINIC("Provides basic healthcare services and improves public well-being."),
    HOSPITAL("Offers advanced medical care and increases overall health standards."),
    EMERGENCY_RESPONSE_CENTER("Improves response time to crises and reduces their impact."),
    MEDICAL_RESEARCH_CENTER("Advances medical knowledge and supports long-term health improvements."),
    PHARMACEUTICAL_PRODUCTION_FACILITY("Ensures availability of medicines and supports healthcare efficiency.");

    private final String description;
    private final double constructionCost;
    private final double maintenanceCost;
    private final EnumMap<MetricType, Double> relatedMetrics;
    private final double occupiedLand;
    private final double performanceMultiplier;
    private final EnumMap<ProductType, Double> demand;

    HealthServicesBuilding(String description, double constructionCost, double maintenanceCost, EnumMap<MetricType, Double> relatedMetrics, double occupiedLand, double performanceMultiplier, double production, EnumMap<ProductType, Double> demand) {
        this.description = description;
        this.constructionCost = constructionCost;
        this.maintenanceCost = maintenanceCost;
        this.relatedMetrics = relatedMetrics;
        this.occupiedLand = occupiedLand;
        this.performanceMultiplier = performanceMultiplier;
        this.production = production;
        this.demand = demand;
    }

    @Override
    public String getName() {
        return this.name();
    }

    public double getConstructionCost() {
        return constructionCost;
    }

    public double getMaintenanceCost() {
        return maintenanceCost;
    }

    public EnumMap<MetricType, Double> getRelatedMetrics() {
        return new EnumMap<>(relatedMetrics);
    }

    public double getOccupiedLand() {
        return occupiedLand;
    }

    public double getPerformanceMultiplier() {
        return performanceMultiplier;
    }

    public EnumMap<ProductType, Double> getDemand() {
        return new EnumMap<>(demand);
    }

    public String getDescription() {
        return description;
    }
}
