package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.product.ProductType;
import io.github.NationArchitect.model.metric.*;
import java.util.EnumMap;

public enum ElectricityBuilding implements BuildingType {

    COAL_POWER_PLANT("Generates high electricity output at low cost but increases pollution and risk."),
    NATURAL_GAS_POWER_PLANT("Produces efficient and cleaner energy with moderate output."),
    OIL_POWER_PLANT("Provides reliable energy but comes with higher operational costs."),
    WIND_POWER_PLANT("Generates clean energy with variable output depending on conditions."),
    SOLAR_POWER_PLANT("Produces sustainable energy with lower but consistent output."),
    HYDROELECTRIC_POWER_PLANT("Generates stable and strong energy using water resources."),
    GEOTHERMAL_POWER_PLANT("Provides stable and continuous energy from underground heat sources."),
    NUCLEAR_POWER_PLANT("Produces massive energy output with high cost and potential risks.");

    private final String description;
    private final double constructionCost;
    private final double maintenanceCost;
    private final EnumMap<MetricType, Double> relatedMetrics;
    private final double occupiedLand;
    private final double performanceMultiplier;
    private final double production;
    private final EnumMap<ProductType, Double> demand;

    ElectricityBuilding(String description, double constructionCost, double maintenanceCost, EnumMap<MetricType, Double> relatedMetrics, double occupiedLand, double performanceMultiplier, double production, EnumMap<ProductType, Double> demand) {
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
