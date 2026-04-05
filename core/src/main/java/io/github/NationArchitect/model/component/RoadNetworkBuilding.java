package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.metric.*;
import io.github.NationArchitect.model.product.ProductType;

import java.util.EnumMap;
import java.util.Map;

import static io.github.NationArchitect.model.component.BuildingConstants.*;

public enum RoadNetworkBuilding implements BuildingType {

    DISTRIBUTION_CENTER("Improves goods distribution and boosts trade efficiency.",
        22000, 8000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_RN
        )),
        new EnumMap<>(Map.of(
            ComponentType.FACTORY, BASE_INDUSTRY_PERFORMANCE_MULTIPLIER_RN,
            ComponentType.ROAD_TRANSPORT, BASE_TRANSPORT_PERFORMANCE_MULTIPLIER_RN
        )),
        1600,
        BASE_PERFORMANCE_MULTIPLIER_RN * 1.2,
        new EnumMap<>(Map.of(
            ProductType.WATER, 120.0,
            ProductType.ENERGY, 260.0,
            ProductType.TECHNOLOGY, 50.0
        )),
        55
    ),

    ROAD_MAINTENANCE_FACILITY("Maintains road quality and reduces infrastructure-related issues.",
        18000, 6500,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_RN * 0.8
        )),
        new EnumMap<>(Map.of(
            ComponentType.ROAD_TRANSPORT, BASE_TRANSPORT_PERFORMANCE_MULTIPLIER_RN * 1.3,
            ComponentType.RAIL_TRANSPORT, BASE_TRANSPORT_PERFORMANCE_MULTIPLIER_RN * 0.8
        )),
        1400,
        BASE_PERFORMANCE_MULTIPLIER_RN,
        new EnumMap<>(Map.of(
            ProductType.WATER, 80.0,
            ProductType.ENERGY, 220.0,
            ProductType.TECHNOLOGY, 40.0
        )),
        45
    ),

    TRAFFIC_CONTROL_CENTER("Optimizes traffic flow and increases transportation efficiency.",
        30000, 12000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_RN * 1.3
        )),
        new EnumMap<>(Map.of(
            ComponentType.ROAD_TRANSPORT, BASE_TRANSPORT_PERFORMANCE_MULTIPLIER_RN * 1.8,
            ComponentType.RAIL_TRANSPORT, BASE_TRANSPORT_PERFORMANCE_MULTIPLIER_RN,
            ComponentType.TOURISM, BASE_TRANSPORT_PERFORMANCE_MULTIPLIER_RN * 0.5
        )),
        1800,
        BASE_PERFORMANCE_MULTIPLIER_RN * 1.8,
        new EnumMap<>(Map.of(
            ProductType.WATER, 120.0,
            ProductType.ENERGY, 420.0,
            ProductType.TECHNOLOGY, 100.0
        )),
        85
    ),

    LOGISTICS_HUB("Enhances large-scale logistics and significantly boosts trade operations.",
        65000, 24000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_RN * 1.5
        )),
        new EnumMap<>(Map.of(
            ComponentType.FACTORY, BASE_INDUSTRY_PERFORMANCE_MULTIPLIER_RN * 2.5,
            ComponentType.ROAD_TRANSPORT, BASE_TRANSPORT_PERFORMANCE_MULTIPLIER_RN * 1.8,
            ComponentType.RAIL_TRANSPORT, BASE_TRANSPORT_PERFORMANCE_MULTIPLIER_RN * 1.5
        )),
        3200,
        BASE_PERFORMANCE_MULTIPLIER_RN * 2.6,
        new EnumMap<>(Map.of(
            ProductType.WATER, 220.0,
            ProductType.ENERGY, 850.0,
            ProductType.TECHNOLOGY, 220.0,
            ProductType.INDUSTRIAL_GOOD, 180.0
        )),
        170
    ),

    NAVIGATION_ROUTING_CENTER("Optimizes route planning to improve overall transport efficiency.",
        48000, 18000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_RN * 1.2
        )),
        new EnumMap<>(Map.of(
            ComponentType.ROAD_TRANSPORT, BASE_TRANSPORT_PERFORMANCE_MULTIPLIER_RN * 2,
            ComponentType.RAIL_TRANSPORT, BASE_TRANSPORT_PERFORMANCE_MULTIPLIER_RN * 1.4,
            ComponentType.FACTORY, BASE_INDUSTRY_PERFORMANCE_MULTIPLIER_RN * 1.3
        )),
        2500,
        BASE_PERFORMANCE_MULTIPLIER_RN * 2.1,
        new EnumMap<>(Map.of(
            ProductType.WATER, 180.0,
            ProductType.ENERGY, 600.0,
            ProductType.TECHNOLOGY, 180.0
        )),
        120
    );

    private final String description;
    private final double constructionCost;
    private final double maintenanceCost;
    private final EnumMap<MetricType, Double> relatedMetrics;
    private final EnumMap<ComponentType, Double> relatedComponents;
    private final double occupiedLand;
    private final double performanceMultiplier;
    private final EnumMap<ProductType, Double> demand;
    private final int maxWorkerAmount;

    RoadNetworkBuilding(String description, double constructionCost, double maintenanceCost,
        EnumMap<MetricType, Double> relatedMetrics, EnumMap<ComponentType, Double> relatedComponents,
        double occupiedLand, double performanceMultiplier, EnumMap<ProductType, Double> demand,
        int maxWorkerAmount) {
        this.description = description;
        this.constructionCost = constructionCost;
        this.maintenanceCost = maintenanceCost;
        this.relatedMetrics = relatedMetrics;
        this.relatedComponents = relatedComponents;
        this.occupiedLand = occupiedLand;
        this.performanceMultiplier = performanceMultiplier;
        this.demand = demand;
        this.maxWorkerAmount = maxWorkerAmount;
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

    public EnumMap<ComponentType, Double> getRelatedComponents() {
        return new EnumMap<>(relatedComponents);
    }

    public double getOccupiedLand() {
        return occupiedLand;
    }

    public double getPerformanceMultiplier() {
        return performanceMultiplier;
    }

    public double getProduction() {
        return 0;
    }

    public EnumMap<ProductType, Double> getDemand() {
        return new EnumMap<>(demand);
    }

    public String getDescription() {
        return description;
    }

    public int getMaxWorkerAmount() {
        return maxWorkerAmount;
    }
}
