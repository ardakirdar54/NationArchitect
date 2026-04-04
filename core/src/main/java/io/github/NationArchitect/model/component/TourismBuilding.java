package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.product.ProductType;
import io.github.NationArchitect.model.metric.*;
import java.util.EnumMap;

public enum TourismBuilding implements BuildingType {

    HOTEL("Provides accommodation and supports tourism activity."),
    RESORT("Offers high-quality leisure services and improves tourism appeal."),
    TOURIST_ATTRACTION("Draws visitors and increases overall tourism activity."),
    AMUSEMENT_PARK("Provides entertainment and significantly boosts tourism attractiveness."),
    CULTURAL_SITE("Preserves heritage and attracts cultural tourism."),
    CONVENTION_CENTER("Hosts large events and boosts business tourism."),
    TOURISM_CENTER("Coordinates tourism services and improves overall efficiency."),
    LUXURY_HOTEL("Provides premium accommodation and greatly enhances tourism value.");

    private final String description;
    private final double constructionCost;
    private final double maintenanceCost;
    private final EnumMap<MetricType, Double> relatedMetrics;
    private final double occupiedLand;
    private final double performanceMultiplier;
    private final EnumMap<ProductType, Double> demand;

    TourismBuilding(String description, double constructionCost, double maintenanceCost, EnumMap<MetricType, Double> relatedMetrics, double occupiedLand, double performanceMultiplier, double production, EnumMap<ProductType, Double> demand) {
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
