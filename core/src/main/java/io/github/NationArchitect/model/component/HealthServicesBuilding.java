package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.metric.*;
import io.github.NationArchitect.model.product.ProductType;

import java.util.EnumMap;
import java.util.Map;

import static io.github.NationArchitect.model.component.BuildingConstants.*;

/**
 * Defines the building types available for the HealthServices component.
 */
public enum HealthServicesBuilding implements BuildingType {

    /**
     * Represents clinic.
     * It affects {@link Happiness} and {@link HealthRate}.
     */
    CLINIC("Provides basic healthcare services and improves public well-being.",
        12000, 4500,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_HS,
            MetricType.HEALTH_RATE, BASE_HEALTH_RATE_BOOST_HS
        )),
        700,
        BASE_PERFORMANCE_MULTIPLIER_HS,
        new EnumMap<>(Map.of(
            ProductType.WATER, 180.0,
            ProductType.ENERGY, 220.0,
            ProductType.TECHNOLOGY, 60.0
        )),
        35,
        100
    ),

    /**
     * Represents hospital.
     * It affects {@link Happiness} and {@link HealthRate}.
     */
    HOSPITAL("Offers advanced medical care and increases overall health standards.",
        42000, 16000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_HS * 2.5,
            MetricType.HEALTH_RATE, BASE_HEALTH_RATE_BOOST_HS * 3
        )),
        2200,
        BASE_PERFORMANCE_MULTIPLIER_HS,
        new EnumMap<>(Map.of(
            ProductType.WATER, 500.0,
            ProductType.ENERGY, 700.0,
            ProductType.TECHNOLOGY, 180.0
        )),
        180,
        500
    ),

    /**
     * Represents emergency response center.
     * It affects {@link Happiness} and {@link HealthRate}.
     */
    EMERGENCY_RESPONSE_CENTER("Improves response time to crises and reduces their impact.",
        28000, 11000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_HS * 1.8,
            MetricType.HEALTH_RATE, BASE_HEALTH_RATE_BOOST_HS * 1.9
        )),
        1300,
        BASE_PERFORMANCE_MULTIPLIER_HS * 1.4,
        new EnumMap<>(Map.of(
            ProductType.WATER, 260.0,
            ProductType.ENERGY, 420.0,
            ProductType.TECHNOLOGY, 120.0
        )),
        90,
        0
    ),

    /**
     * Represents medical research center.
     * It affects {@link Happiness} and {@link HealthRate}.
     */
    MEDICAL_RESEARCH_CENTER("Advances medical knowledge and supports long-term health improvements.",
        85000, 28000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_HS * 2.2,
            MetricType.HEALTH_RATE, BASE_HEALTH_RATE_BOOST_HS * 4
        )),
        2600,
        BASE_PERFORMANCE_MULTIPLIER_HS * 2.3,
        new EnumMap<>(Map.of(
            ProductType.WATER, 650.0,
            ProductType.ENERGY, 950.0,
            ProductType.TECHNOLOGY, 320.0
        )),
        220,
        250
    ),

    /**
     * Represents pharmaceutical production facility.
     * It affects {@link Happiness} and {@link HealthRate}.
     */
    PHARMACEUTICAL_PRODUCTION_FACILITY("Ensures availability of medicines and supports healthcare efficiency.",
        110000, 42000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_HS * 2,
            MetricType.HEALTH_RATE, BASE_HEALTH_RATE_BOOST_HS * 3.2
        )),
        3200,
        BASE_PERFORMANCE_MULTIPLIER_HS * 2,
        new EnumMap<>(Map.of(
            ProductType.WATER, 800.0,
            ProductType.ENERGY, 1200.0,
            ProductType.TECHNOLOGY, 420.0,
            ProductType.INDUSTRIAL_GOOD, 240.0
        )),
        260,
        0
    );

    /** Description of the enum value. */
    private final String description;
    /** Construction cost of the building type. */
    private final double constructionCost;
    /** Maintenance cost of the building type. */
    private final double maintenanceCost;
    /** Metric effects applied by the building type. */
    private final EnumMap<MetricType, Double> relatedMetrics;
    /** Land occupied by the building type. */
    private final double occupiedLand;
    /** Performance multiplier provided by the building type. */
    private final double performanceMultiplier;
    /** Resource demand of the building type. */
    private final EnumMap<ProductType, Double> demand;
    /** Maximum worker capacity of the building type. */
    private final int maxWorkerAmount;
    /** Maximum student capacity of the building type. */
    private final int capacity;

    /**
     * Creates the enum value definition for {@link HealthServicesBuilding}.
     *
     * @param description human-readable summary of the enum value
     * @param constructionCost construction cost of the building type
     * @param maintenanceCost maintenance cost of the building type
     * @param relatedMetrics metric effects applied by the building type
     * @param occupiedLand land occupied by the building type
     * @param performanceMultiplier performance multiplier provided by the building type
     * @param demand resource demand of the building type
     * @param maxWorkerAmount maximum worker capacity of the building type
     * @param capacity capacity value used by the enum entry
     */
    HealthServicesBuilding(
        String description,
        double constructionCost,
        double maintenanceCost,
        EnumMap<MetricType, Double> relatedMetrics,
        double occupiedLand,
        double performanceMultiplier,
        EnumMap<ProductType, Double> demand,
        int maxWorkerAmount,
        int capacity
    ) {
        this.description = description;
        this.constructionCost = constructionCost;
        this.maintenanceCost = maintenanceCost;
        this.relatedMetrics = relatedMetrics;
        this.occupiedLand = occupiedLand;
        this.performanceMultiplier = performanceMultiplier;
        this.demand = demand;
        this.maxWorkerAmount = maxWorkerAmount;
        this.capacity = capacity;
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
        return null;
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
        return capacity;
    }
}



