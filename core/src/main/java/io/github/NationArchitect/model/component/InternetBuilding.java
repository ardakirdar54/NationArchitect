package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.metric.*;
import io.github.NationArchitect.model.product.ProductType;

import java.util.EnumMap;
import java.util.Map;

import static io.github.NationArchitect.model.component.BuildingConstants.*;

public enum InternetBuilding implements BuildingType {

    COMMUNICATION_TOWER("Provides basic internet coverage and a small efficiency boost.",
        14000, 5000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_IN
        )),
        new EnumMap<>(Map.of(
            ComponentType.OFFICE, BASE_OFFICE_PERFORMANCE_MULTIPLIER_IN
        )),
        500,
        BASE_PERFORMANCE_MULTIPLIER_IN,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 200.0,
            ProductType.TECHNOLOGY, 60.0
        )),
        25
    ),

    DATA_CENTER("Processes data and increases overall network efficiency.",
        50000, 21000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_IN * 1.5
        )),
        new EnumMap<>(Map.of(
            ComponentType.OFFICE, BASE_OFFICE_PERFORMANCE_MULTIPLIER_IN * 1.8,
            ComponentType.EDUCATION, BASE_EDUCATION_PERFORMANCE_MULTIPLIER_IN
        )),
        1600,
        BASE_PERFORMANCE_MULTIPLIER_IN * 2.2,
        new EnumMap<>(Map.of(
            ProductType.WATER, 180.0,
            ProductType.ENERGY, 800.0,
            ProductType.TECHNOLOGY, 260.0
        )),
        90
    ),

    NETWORK_CONTROL_CENTER("Optimizes network operations and improves overall system performance.",
        70000, 26000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_IN * 1.8
        )),
        new EnumMap<>(Map.of(
            ComponentType.OFFICE, BASE_OFFICE_PERFORMANCE_MULTIPLIER_IN * 2.2,
            ComponentType.SECURITY, BASE_SECURITY_PERFORMANCE_MULTIPLIER_IN * 1.5
        )),
        2000,
        BASE_PERFORMANCE_MULTIPLIER_IN * 2.8,
        new EnumMap<>(Map.of(
            ProductType.WATER, 220.0,
            ProductType.ENERGY, 1000.0,
            ProductType.TECHNOLOGY, 340.0
        )),
        130
    ),

    EDGE_NETWORK_HUB("Distributes data processing closer to users, improving efficiency.",
        90000, 32000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_IN * 2
        )),
        new EnumMap<>(Map.of(
            ComponentType.OFFICE, BASE_OFFICE_PERFORMANCE_MULTIPLIER_IN * 2.5,
            ComponentType.EDUCATION, BASE_EDUCATION_PERFORMANCE_MULTIPLIER_IN * 1.6,
            ComponentType.SECURITY, BASE_SECURITY_PERFORMANCE_MULTIPLIER_IN
        )),
        2300,
        BASE_PERFORMANCE_MULTIPLIER_IN * 3.4,
        new EnumMap<>(Map.of(
            ProductType.WATER, 260.0,
            ProductType.ENERGY, 1200.0,
            ProductType.TECHNOLOGY, 420.0
        )),
        160
    ),

    AI_OPTIMIZATION_CENTER("Uses AI to maximize network efficiency across all systems.",
        140000, 52000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_IN * 2.4
        )),
        new EnumMap<>(Map.of(
            ComponentType.OFFICE, BASE_OFFICE_PERFORMANCE_MULTIPLIER_IN * 3.5,
            ComponentType.EDUCATION, BASE_EDUCATION_PERFORMANCE_MULTIPLIER_IN * 2.3,
            ComponentType.SECURITY, BASE_SECURITY_PERFORMANCE_MULTIPLIER_IN * 2
        )),
        3200,
        BASE_PERFORMANCE_MULTIPLIER_IN * 4.5,
        new EnumMap<>(Map.of(
            ProductType.WATER, 350.0,
            ProductType.ENERGY, 1700.0,
            ProductType.TECHNOLOGY, 700.0
        )),
        240
    ),

    CLOUD_INFRASTRUCTURE_HUB("Enables scalable computing and provides a strong global efficiency boost.",
        220000, 85000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_IN * 3
        )),
        new EnumMap<>(Map.of(
            ComponentType.OFFICE, BASE_OFFICE_PERFORMANCE_MULTIPLIER_IN * 5,
            ComponentType.EDUCATION, BASE_EDUCATION_PERFORMANCE_MULTIPLIER_IN * 3,
            ComponentType.SECURITY, BASE_SECURITY_PERFORMANCE_MULTIPLIER_IN * 2.5
        )),
        5000,
        BASE_PERFORMANCE_MULTIPLIER_IN * 6,
        new EnumMap<>(Map.of(
            ProductType.WATER, 500.0,
            ProductType.ENERGY, 2500.0,
            ProductType.TECHNOLOGY, 1100.0
        )),
        400
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

    InternetBuilding(
        String description,
        double constructionCost,
        double maintenanceCost,
        EnumMap<MetricType, Double> relatedMetrics,
        EnumMap<ComponentType, Double> relatedComponents,
        double occupiedLand,
        double performanceMultiplier,
        EnumMap<ProductType, Double> demand,
        int maxWorkerAmount
    ) {
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
