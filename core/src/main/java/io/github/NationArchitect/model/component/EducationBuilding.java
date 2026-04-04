package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.product.ProductType;
import io.github.NationArchitect.model.metric.*;
import java.util.EnumMap;

public enum EducationBuilding implements BuildingType {

    PRIMARY_SCHOOL("Provides basic education and slightly improves workforce skill levels."),
    HIGH_SCHOOL("Improves education level and increases workforce productivity."),
    UNIVERSITY("Boosts higher education and significantly increases innovation and productivity."),
    RESEARCH_INSTITUTE("Drives advanced research and greatly enhances innovation capacity."),
    VOCATIONAL_TRAINING_CENTER("Improves workforce skill levels through practical education and training.");

    private final String description;
    private final double constructionCost;
    private final double maintenanceCost;
    private final EnumMap<MetricType, Double> relatedMetrics;
    private final double occupiedLand;
    private final double performanceMultiplier;
    private final EnumMap<ProductType, Double> demand;

    EducationBuilding(String description, double constructionCost, double maintenanceCost, EnumMap<MetricType, Double> relatedMetrics, double occupiedLand, double performanceMultiplier, double production, EnumMap<ProductType, Double> demand) {
        this.description = description;
        this.constructionCost = constructionCost;
        this.maintenanceCost = maintenanceCost;
        this.relatedMetrics = relatedMetrics;
        this.occupiedLand = occupiedLand;
        this.performanceMultiplier = performanceMultiplier;
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
