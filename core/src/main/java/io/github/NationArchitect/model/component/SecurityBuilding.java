package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.metric.*;
import io.github.NationArchitect.model.product.ProductType;

import java.util.EnumMap;
import java.util.Map;

import static io.github.NationArchitect.model.component.BuildingConstants.*;

/**
 * Defines the building types available for the Security component.
 */
public enum SecurityBuilding implements BuildingType {

    /**
     * Represents police station.
     * It affects {@link Happiness} and {@link CrimeRate}.
     */
    POLICE_STATION("Maintains law enforcement and improves public safety.",
        16000, 6000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_SC,
            MetricType.CRIME_RATE, BASE_CRIME_RATE_BOOST_SC
        )),
        900,
        0,
        new EnumMap<>(Map.of(
            ProductType.WATER, 120.0,
            ProductType.ENERGY, 180.0,
            ProductType.TECHNOLOGY, 40.0
        )),
        50
    ),

    /**
     * Represents surveillance center.
     * It affects {@link Happiness} and {@link CrimeRate}.
     */
    SURVEILLANCE_CENTER("Enhances monitoring capabilities and helps prevent criminal activities.",
        30000, 12000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_SC * 1.2,
            MetricType.CRIME_RATE, BASE_CRIME_RATE_BOOST_SC * 1.4
        )),
        1400,
        BASE_PERFORMANCE_MULTIPLIER_SC,
        new EnumMap<>(Map.of(
            ProductType.WATER, 150.0,
            ProductType.ENERGY, 320.0,
            ProductType.TECHNOLOGY, 120.0
        )),
        80
    ),

    /**
     * Represents special forces unit.
     * It affects {@link Happiness} and {@link CrimeRate}.
     */
    SPECIAL_FORCES_UNIT("Handles high-risk situations and reduces the impact of major threats.",
        60000, 22000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_SC * 1.5,
            MetricType.CRIME_RATE, BASE_CRIME_RATE_BOOST_SC * 2
        )),
        2200,
        BASE_PERFORMANCE_MULTIPLIER_SC * 1.4,
        new EnumMap<>(Map.of(
            ProductType.WATER, 200.0,
            ProductType.ENERGY, 550.0,
            ProductType.TECHNOLOGY, 220.0
        )),
        120
    ),

    /**
     * Represents intelligence agency.
     * It affects {@link Happiness}, {@link CrimeRate}, and {@link Stability}.
     */
    INTELLIGENCE_AGENCY("Gathers critical information to improve national security and stability.",
        90000, 34000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_SC * 1.6,
            MetricType.CRIME_RATE, BASE_CRIME_RATE_BOOST_SC * 2.4,
            MetricType.STABILITY, BASE_HAPPINESS_BOOST_SC * 1.4
        )),
        2600,
        BASE_PERFORMANCE_MULTIPLIER_SC * 2,
        new EnumMap<>(Map.of(
            ProductType.WATER, 260.0,
            ProductType.ENERGY, 700.0,
            ProductType.TECHNOLOGY, 320.0
        )),
        170
    ),

    /**
     * Represents cyber security center.
     * It affects {@link Happiness}, {@link CrimeRate}, and {@link Stability}.
     */
    CYBER_SECURITY_CENTER("Protects digital infrastructure and reduces cyber-related risks.",
        105000, 42000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_SC * 1.4,
            MetricType.CRIME_RATE, BASE_CRIME_RATE_BOOST_SC * 2,
            MetricType.STABILITY, BASE_HAPPINESS_BOOST_SC * 1.8
        )),
        2000,
        BASE_PERFORMANCE_MULTIPLIER_SC * 2.4,
        new EnumMap<>(Map.of(
            ProductType.WATER, 200.0,
            ProductType.ENERGY, 850.0,
            ProductType.TECHNOLOGY, 450.0
        )),
        150
    ),

    /**
     * Represents prison facility.
     * It affects {@link Happiness} and {@link CrimeRate}.
     */
    PRISON_FACILITY("Manages offenders and supports long-term crime reduction.",
        75000, 28000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_SC * 0.7,
            MetricType.CRIME_RATE, BASE_CRIME_RATE_BOOST_SC * 2.7
        )),
        3500,
        0,
        new EnumMap<>(Map.of(
            ProductType.WATER, 500.0,
            ProductType.ENERGY, 650.0,
            ProductType.TECHNOLOGY, 120.0
        )),
        210
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

    /**
     * Creates the enum value definition for {@link SecurityBuilding}.
     *
     * @param description human-readable summary of the enum value
     * @param constructionCost construction cost of the building type
     * @param maintenanceCost maintenance cost of the building type
     * @param relatedMetrics metric effects applied by the building type
     * @param occupiedLand land occupied by the building type
     * @param performanceMultiplier performance multiplier provided by the building type
     * @param demand resource demand of the building type
     * @param maxWorkerAmount maximum worker capacity of the building type
     */
    SecurityBuilding(
        String description,
        double constructionCost,
        double maintenanceCost,
        EnumMap<MetricType, Double> relatedMetrics,
        double occupiedLand,
        double performanceMultiplier,
        EnumMap<ProductType, Double> demand,
        int maxWorkerAmount
    ) {
        this.description = description;
        this.constructionCost = constructionCost;
        this.maintenanceCost = maintenanceCost;
        this.relatedMetrics = relatedMetrics;
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
        return 0;
    }
}

