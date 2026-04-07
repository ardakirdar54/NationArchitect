package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.metric.*;
import io.github.NationArchitect.model.product.ProductType;

import java.util.EnumMap;
import java.util.Map;

import static io.github.NationArchitect.model.component.BuildingConstants.*;

/**
 * Defines the building types available for the Internet component.
 */
public enum InternetBuilding implements BuildingType {

    /**
     * Represents communication tower.
     * It affects {@link Happiness} and influences {@link Office}.
     */
    COMMUNICATION_TOWER("Provides basic internet coverage and a small efficiency boost.",
        14000, 5000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_IN * 2.3
        )),
        new EnumMap<>(Map.of(
            ComponentType.OFFICE, BASE_OFFICE_PERFORMANCE_MULTIPLIER_IN * 0.4
        )),
        500,
        BASE_PERFORMANCE_MULTIPLIER_IN,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 200.0,
            ProductType.TECHNOLOGY, 60.0
        )),
        25
    ),

    /**
     * Represents data center.
     * It affects {@link Happiness} and influences {@link Office} and {@link Education}.
     */
    DATA_CENTER("Processes data and increases overall network efficiency.",
        50000, 21000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_IN
        )),
        new EnumMap<>(Map.of(
            ComponentType.OFFICE, BASE_OFFICE_PERFORMANCE_MULTIPLIER_IN * 3.8,
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

    /**
     * Represents network control center.
     * It affects {@link Happiness} and influences {@link Office} and {@link Security}.
     */
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

    /**
     * Represents edge network hub.
     * It affects {@link Happiness} and influences {@link Office}, {@link Education}, and {@link Security}.
     */
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

    /**
     * Represents ai optimization center.
     * It affects {@link Happiness} and influences {@link Office}, {@link Education}, and {@link Security}.
     */
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

    /**
     * Represents cloud infrastructure hub.
     * It affects {@link Happiness} and influences {@link Office}, {@link Education}, and {@link Security}.
     */
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

    /** Description of the enum value. */
    private final String description;
    /** Construction cost of the building type. */
    private final double constructionCost;
    /** Maintenance cost of the building type. */
    private final double maintenanceCost;
    /** Metric effects applied by the building type. */
    private final EnumMap<MetricType, Double> relatedMetrics;
    /** Component effects applied by the building type. */
    private final EnumMap<ComponentType, Double> relatedComponents;
    /** Land occupied by the building type. */
    private final double occupiedLand;
    /** Performance multiplier provided by the building type. */
    private final double performanceMultiplier;
    /** Resource demand of the building type. */
    private final EnumMap<ProductType, Double> demand;
    /** Maximum worker capacity of the building type. */
    private final int maxWorkerAmount;

    /**
     * Creates the enum value definition for {@link InternetBuilding}.
     *
     * @param description human-readable summary of the enum value
     * @param constructionCost construction cost of the building type
     * @param maintenanceCost maintenance cost of the building type
     * @param relatedMetrics metric effects applied by the building type
     * @param relatedComponents component effects applied by the building type
     * @param occupiedLand land occupied by the building type
     * @param performanceMultiplier performance multiplier provided by the building type
     * @param demand resource demand of the building type
     * @param maxWorkerAmount maximum worker capacity of the building type
     */
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

    public int getCapacity() {
        return 0;
    }
}



