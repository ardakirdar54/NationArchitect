package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.product.ProductType;
import io.github.NationArchitect.model.metric.*;
import java.util.EnumMap;
import java.util.Map;
import static io.github.NationArchitect.model.component.BuildingConstants.*;

/**
 * Defines the building types available for the AirTransport component.
 */
public enum AirTransportBuilding implements BuildingType {

    /**
     * Represents airport.
     * It affects {@link Happiness} and influences {@link Education}.
     */
    AIRPORT("Supports air travel and improves long-distance transportation efficiency.",
        100000, 40000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_AT
        )),
        new EnumMap<>(Map.of(
            ComponentType.EDUCATION, BASE_EDUCATION_PERFORMANCE_MULTIPLIER_AT
        )),
        30000,
        BASE_PERFORMANCE_MULTIPLIER_AT,
        new EnumMap<>(Map.of(
            ProductType.INDUSTRIAL_GOOD, 600.0,
            ProductType.WATER, 1000.0,
            ProductType.ENERGY, 1000.0,
            ProductType.TECHNOLOGY, 300.0
        )),
        1000
    ),

    /**
     * Represents international airport.
     * It affects {@link Happiness}.
     */
    INTERNATIONAL_AIRPORT("Handles large-scale air traffic and significantly improves global connectivity.",
        250000, 70000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_AT * 3
        )),
        null,
        70000,
        BASE_PERFORMANCE_MULTIPLIER_AT * 3,
        new EnumMap<>(Map.of(
            ProductType.INDUSTRIAL_GOOD, 1500.0,
            ProductType.WATER, 2400.0,
            ProductType.ENERGY, 2400.0,
            ProductType.TECHNOLOGY, 700.0
        )),
        2500
    ),

    /**
     * Represents cargo air terminal.
     * It affects {@link Happiness}.
     */
    CARGO_AIR_TERMINAL("Facilitates air freight operations and boosts trade efficiency.",
        80000, 24000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_AT * 0.3
        )),
        null,
        45000,
        BASE_PERFORMANCE_MULTIPLIER_AT * 7,
        new EnumMap<>(Map.of(
            ProductType.INDUSTRIAL_GOOD, 1000.0,
            ProductType.WATER, 1400.0,
            ProductType.ENERGY, 1500.0,
            ProductType.TECHNOLOGY, 500.0
        )),
        1200
    ),

    /**
     * Represents aircraft maintenance hangar.
     * It affects {@link Happiness}.
     */
    AIRCRAFT_MAINTENANCE_HANGAR("Ensures aircraft reliability and improves operational efficiency.",
        50000, 16000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_AT
        )),
        null, 4000,
        BASE_PERFORMANCE_MULTIPLIER_AT * 6.5,
        new EnumMap<>(Map.of(
            ProductType.INDUSTRIAL_GOOD, 400.0,
            ProductType.WATER, 600.0,
            ProductType.ENERGY, 800.0,
            ProductType.TECHNOLOGY, 200.0
        )),
        150
    ),

    /**
     * Represents aviation training center.
     * It affects {@link Happiness}.
     */
    AVIATION_TRAINING_CENTER("Improves workforce expertise and enhances overall air transport efficiency.",
        55000,
        17000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_AT * 1.5
        )),
        null, 8000,
        BASE_PERFORMANCE_MULTIPLIER_AT * 6,
        new EnumMap<>(Map.of(
            ProductType.INDUSTRIAL_GOOD, 280.0,
            ProductType.WATER, 350.0,
            ProductType.ENERGY, 450.0,
            ProductType.TECHNOLOGY, 180.0
        )),
        100
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
     * Creates the enum value definition for {@link AirTransportBuilding}.
     *
     * @param description human-readable summary of the enum value
     * @param constructionCost construction cost of the building type
     * @param maintenanceCost maintenance cost of the building type
     * @param relatedMetrics metric effects applied by the building type
     * @param relatedComponents component effects applied by the building type
     * @param occupiedLand land occupied by the building type
     * @param performanceMultiplier performance multiplier provided by the building type
     * @param demand resource demand of the building type
     * @param maxWorkerCount maximum worker capacity of the building type
     */
    AirTransportBuilding(
        String description,
        double constructionCost,
        double maintenanceCost,
        EnumMap<MetricType, Double> relatedMetrics,
        EnumMap<ComponentType, Double> relatedComponents,
        double occupiedLand,
        double performanceMultiplier,
        EnumMap<ProductType, Double> demand,
        int maxWorkerCount
    ) {
        this.description = description;
        this.constructionCost = constructionCost;
        this.maintenanceCost = maintenanceCost;
        this.relatedMetrics = relatedMetrics;
        this.relatedComponents = relatedComponents;
        this.occupiedLand = occupiedLand;
        this.performanceMultiplier = performanceMultiplier;
        this.demand = demand;
        this.maxWorkerAmount = maxWorkerCount;
    }

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
        return relatedComponents;
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
