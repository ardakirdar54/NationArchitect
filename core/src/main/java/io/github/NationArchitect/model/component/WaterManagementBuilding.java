package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.product.ProductType;
import io.github.NationArchitect.model.metric.*;
import java.util.EnumMap;

public enum WaterManagementBuilding implements BuildingType {

    WATER_TREATMENT_PLANT("Purifies water to improve public health and quality of life."),
    WATER_PUMPING_STATION("Distributes water efficiently across regions."),
    DAM("Stabilizes water supply and improves overall water management efficiency."),
    WASTEWATER_TREATMENT_PLANT("Processes waste water to reduce environmental impact."),
    WATER_RECYCLING_FACILITY("Reuses water to improve sustainability and efficiency."),
    RAINWATER_HARVESTING_SYSTEM("Collects rainwater to provide a small supplementary water supply."),
    DESALINATION_PLANT("Converts seawater into usable water, ensuring supply in coastal regions.");

    private final String description;
    private final double constructionCost;
    private final double maintenanceCost;
    private final EnumMap<MetricType, Double> relatedMetrics;
    private final double occupiedLand;
    private final double performanceMultiplier;
    private final double production;
    private final EnumMap<ProductType, Double> demand;

    WaterManagementBuilding(String description, double constructionCost, double maintenanceCost, EnumMap<MetricType, Double> relatedMetrics, double occupiedLand, double performanceMultiplier, double production, EnumMap<ProductType, Double> demand) {
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

    public double getProduction() {
        return production;
    }

    public EnumMap<ProductType, Double> getDemand() {
        return new EnumMap<>(demand);
    }

    public String getDescription() {
        return description;
    }
}
