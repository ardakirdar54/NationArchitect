package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.metric.*;
import io.github.NationArchitect.model.product.ProductType;

import java.util.EnumMap;
import java.util.Map;

import static io.github.NationArchitect.model.component.BuildingConstants.*;

/**
 * Defines the building types available for the Tourism component.
 */
public enum TourismBuilding implements BuildingType {

    /**
     * Represents hotel.
     * It affects {@link Happiness}.
     */
    HOTEL("Provides accommodation and supports tourism activity.",
        18000, 7000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TO
        )),
        new EnumMap<>(ComponentType.class),
        900,
        250,
        BASE_PERFORMANCE_MULTIPLIER_TO * 0.8,
        new EnumMap<>(Map.of(
            ProductType.WATER, 200.0,
            ProductType.ENERGY, 260.0,
            ProductType.TECHNOLOGY, 40.0
        )),
        45
    ),

    /**
     * Represents resort.
     * It affects {@link Happiness} and influences {@link AirTransport}.
     */
    RESORT("Offers high-quality leisure services and improves tourism appeal.",
        50000, 19000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TO * 2.2
        )),
        new EnumMap<>(Map.of(
            ComponentType.AIR_TRANSPORT, BASE_PERFORMANCE_MULTIPLIER_TO * 0.8
        )),
        2600,
        700,
        BASE_PERFORMANCE_MULTIPLIER_TO * 2.1,
        new EnumMap<>(Map.of(
            ProductType.WATER, 700.0,
            ProductType.ENERGY, 800.0,
            ProductType.TECHNOLOGY, 140.0
        )),
        140
    ),

    /**
     * Represents tourist attraction.
     * It affects {@link Happiness}.
     */
    TOURIST_ATTRACTION("Draws visitors and increases overall tourism activity.",
        30000, 12000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TO * 3.7
        )),
        new EnumMap<>(ComponentType.class),
        1500,
        0,
        BASE_PERFORMANCE_MULTIPLIER_TO * 4.4,
        new EnumMap<>(Map.of(
            ProductType.WATER, 250.0,
            ProductType.ENERGY, 350.0,
            ProductType.TECHNOLOGY, 70.0
        )),
        70
    ),

    /**
     * Represents amusement park.
     * It affects {@link Happiness} and influences {@link RoadTransport}.
     */
    AMUSEMENT_PARK("Provides entertainment and significantly boosts tourism attractiveness.",
        70000, 26000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TO * 3
        )),
        new EnumMap<>(Map.of(
            ComponentType.ROAD_TRANSPORT, BASE_PERFORMANCE_MULTIPLIER_TO
        )),
        3200,
        1000,
        BASE_PERFORMANCE_MULTIPLIER_TO * 2,
        new EnumMap<>(Map.of(
            ProductType.WATER, 800.0,
            ProductType.ENERGY, 1200.0,
            ProductType.TECHNOLOGY, 180.0
        )),
        180
    ),

    /**
     * Represents cultural site.
     * It affects {@link Happiness} and influences {@link Education}.
     */
    CULTURAL_SITE("Preserves heritage and attracts cultural tourism.",
        40000, 15000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TO * 2
        )),
        new EnumMap<>(Map.of(
            ComponentType.EDUCATION, BASE_PERFORMANCE_MULTIPLIER_TO * 0.7
        )),
        1800,
        700,
        BASE_PERFORMANCE_MULTIPLIER_TO * 1.3,
        new EnumMap<>(Map.of(
            ProductType.WATER, 240.0,
            ProductType.ENERGY, 280.0,
            ProductType.TECHNOLOGY, 60.0
        )),
        65
    ),

    /**
     * Represents convention center.
     * It affects {@link Happiness} and influences {@link Office}.
     */
    CONVENTION_CENTER("Hosts large events and boosts business tourism.",
        85000, 32000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TO * 2.4
        )),
        new EnumMap<>(Map.of(
            ComponentType.OFFICE, BASE_PERFORMANCE_MULTIPLIER_TO
        )),
        2800,
        0,
        BASE_PERFORMANCE_MULTIPLIER_TO * 2.1,
        new EnumMap<>(Map.of(
            ProductType.WATER, 500.0,
            ProductType.ENERGY, 850.0,
            ProductType.TECHNOLOGY, 200.0
        )),
        160
    ),

    /**
     * Represents tourism center.
     * It affects {@link Happiness} and influences {@link Internet}.
     */
    TOURISM_CENTER("Coordinates tourism services and improves overall efficiency.",
        55000, 21000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TO * 1.8
        )),
        new EnumMap<>(Map.of(
            ComponentType.INTERNET, BASE_PERFORMANCE_MULTIPLIER_TO * 0.8
        )),
        1700,
        0,
        BASE_PERFORMANCE_MULTIPLIER_TO * 1.7,
        new EnumMap<>(Map.of(
            ProductType.WATER, 260.0,
            ProductType.ENERGY, 420.0,
            ProductType.TECHNOLOGY, 120.0
        )),
        90
    ),

    /**
     * Represents luxury hotel.
     * It affects {@link Happiness} and influences {@link AirTransport} and {@link RoadTransport}.
     */
    LUXURY_HOTEL("Provides premium accommodation and greatly enhances tourism value.",
        110000, 42000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TO * 3.2
        )),
        new EnumMap<>(Map.of(
            ComponentType.AIR_TRANSPORT, BASE_PERFORMANCE_MULTIPLIER_TO,
            ComponentType.ROAD_TRANSPORT, BASE_PERFORMANCE_MULTIPLIER_TO * 0.6
        )),
        2400,
        2000,
        BASE_PERFORMANCE_MULTIPLIER_TO * 2.4,
        new EnumMap<>(Map.of(
            ProductType.WATER, 620.0,
            ProductType.ENERGY, 950.0,
            ProductType.TECHNOLOGY, 240.0
        )),
        150
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
    /** Production output provided by the building type. */
    private final double production;
    /** Performance multiplier provided by the building type. */
    private final double performanceMultiplier;
    /** Resource demand of the building type. */
    private final EnumMap<ProductType, Double> demand;
    /** Maximum worker capacity of the building type. */
    private final int maxWorkerAmount;

    /**
     * Creates the enum value definition for {@link TourismBuilding}.
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
    TourismBuilding(
        String description,
        double constructionCost,
        double maintenanceCost,
        EnumMap<MetricType, Double> relatedMetrics,
        EnumMap<ComponentType, Double> relatedComponents,
        double occupiedLand, double production,
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
        this.production = production;
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
        return production;
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
