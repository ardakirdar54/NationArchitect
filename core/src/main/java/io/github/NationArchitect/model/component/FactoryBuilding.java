package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.product.ProductType;
import io.github.NationArchitect.model.metric.*;
import java.util.EnumMap;

public enum FactoryBuilding implements BuildingType  {

    BASIC_FACTORY("Produces standard industrial goods and supports economic activity."),
    ADVANCED_FACTORY("Improves production efficiency and produces higher-value goods."),
    AUTOMATED_FACTORY("Uses automation to maximize production efficiency."),
    HEAVY_INDUSTRY_PLANT("Handles large-scale industrial production."),
    INDUSTRIAL_COMPLEX("Integrates multiple production processes to maximize output."),

    STEEL_FACTORY("Produces steel for industrial use and supports heavy industry."),
    CHEMICAL_PLANT("Produces chemical products used across multiple industries."),
    ELECTRONICS_FACTORY("Manufactures electronic goods and supports advanced industries."),
    TEXTILE_FACTORY("Produces fabrics and consumer goods for the market."),
    FOOD_PROCESSING_FACTORY("Processes raw food into consumable products."),
    AUTOMOBILE_FACTORY("Manufactures vehicles and boosts industrial output."),
    MACHINERY_FACTORY("Produces industrial machinery to support production systems."),
    PHARMACEUTICAL_FACTORY("Produces medical goods and supports healthcare systems."),
    CONSUMER_GOODS_FACTORY("Produces everyday goods to support population needs."),
    HIGH_TECH_FACTORY("Produces advanced technological goods with high efficiency.");

    private final String description;
    private final double constructionCost;
    private final double maintenanceCost;
    private final EnumMap<MetricType, Double> relatedMetrics;
    private final double occupiedLand;
    private final double performanceMultiplier;
    private final double production;
    private final EnumMap<ProductType, Double> demand;

    FactoryBuilding(String description, double constructionCost, double maintenanceCost, EnumMap<MetricType, Double> relatedMetrics, double occupiedLand, double performanceMultiplier, double production, EnumMap<ProductType, Double> demand) {
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
