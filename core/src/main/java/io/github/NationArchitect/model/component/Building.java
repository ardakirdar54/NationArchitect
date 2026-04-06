package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.metric.MetricType;
import io.github.NationArchitect.model.product.ProductType;

import java.util.EnumMap;

public class Building {

    private final String name;

    private final String description;

    private final BuildingType type;

    private final double constructionCost;

    private final double maintenanceCost;

    private final EnumMap<MetricType, Double> relatedMetrics;

    private final EnumMap<ComponentType, Double> relatedComponents;

    private final double occupiedLand;

    private final double performanceMultiplier;

    private final EnumMap<ProductType, Double> demand;

    private int workerAmount;

    private final int maxWorkerAmount;

    public Building(String name, BuildingType type) {
        this.name = name;
        this.type = type;
        this.description = type.getDescription();
        this.constructionCost = type.getConstructionCost();
        this.maintenanceCost = type.getMaintenanceCost();
        this.relatedMetrics = type.getRelatedMetrics();
        this.relatedComponents = type.getRelatedComponents();
        this.occupiedLand = type.getOccupiedLand();
        this.performanceMultiplier = type.getPerformanceMultiplier();
        this.demand = type.getDemand();
        this.maxWorkerAmount = type.getMaxWorkerAmount();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BuildingType getType() {
        return type;
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

    public EnumMap<ComponentType, Double> getRelatedComponents() {
        return new EnumMap<>(relatedComponents);
    }

    public double getOccupiedLand() {
        return occupiedLand;
    }

    public double getPerformanceMultiplier() {
        return performanceMultiplier;
    }

    public EnumMap<ProductType, Double> getDemand() {
        return demand == null ? null : new EnumMap<>(demand);
    }

    public int getWorkerAmount() {
        return workerAmount;
    }

    void increaseWorkerAmount(int amount) {
        workerAmount += amount;
    }

    void decreaseWorkerAmount(int amount) {
        workerAmount = Math.max(0, workerAmount - amount);
    }

    public int getMaxWorkerAmount() {
        return maxWorkerAmount;
    }
}
