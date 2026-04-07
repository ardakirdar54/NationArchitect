package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.product.ProductType;
import io.github.NationArchitect.model.metric.*;
import java.util.EnumMap;
import java.util.Map;
import static io.github.NationArchitect.model.component.BuildingConstants.*;

/**
 * Defines the building types available for the RailTransport component.
 */
public enum RailTransportBuilding implements BuildingType {

    /**
     * Represents train station.
     * It affects {@link Happiness} and influences {@link Tourism}.
     */
    TRAIN_STATION("Supports passenger rail transport and improves mobility.",
        25000, 9000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_RAIL * 2
        )),
        new EnumMap<>(Map.of(
            ComponentType.TOURISM, BASE_TOURISM_PERFORMANCE_MULTIPLIER_RAIL * 1.2
        )),
        1800,
        0,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 250.0,
            ProductType.TECHNOLOGY, 50.0
        )),
        90
    ),

    /**
     * Represents cargo rail terminal.
     * It affects {@link Happiness} and influences {@link Factory}, {@link Agriculture}, and {@link Office}.
     */
    CARGO_RAIL_TERMINAL("Facilitates large-scale goods transportation via rail.",
        40000, 13000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_RAIL * 0.5
        )),
        new EnumMap<>(Map.of(
            ComponentType.FACTORY, BASE_INDUSTRY_PERFORMANCE_MULTIPLIER_RAIL * 1.4,
            ComponentType.AGRICULTURE, BASE_INDUSTRY_PERFORMANCE_MULTIPLIER_RAIL * 1.4,
            ComponentType.OFFICE, BASE_INDUSTRY_PERFORMANCE_MULTIPLIER_RAIL * 1.4
        )),
        3000,
        0,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 400.0,
            ProductType.TECHNOLOGY, 80.0
        )),
        140
    ),

    /**
     * Represents railway depot.
     * It affects {@link Happiness}.
     */
    RAILWAY_DEPOT("Maintains trains and improves overall rail system efficiency.",
        30000, 10000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_RAIL
        )),
        null,
        2000,
        BASE_PERFORMANCE_MULTIPLIER_RAIL * 3.5,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 300.0,
            ProductType.TECHNOLOGY, 60.0
        )),
        100
    ),

    /**
     * Represents freight yard.
     * It affects {@link Happiness} and influences {@link Factory}, {@link Agriculture}, and {@link Office}.
     */
    FREIGHT_YARD("Handles cargo sorting and boosts logistics efficiency.",
        35000, 12000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_RAIL * 0.5
        )),
        new EnumMap<>(Map.of(
            ComponentType.FACTORY, BASE_INDUSTRY_PERFORMANCE_MULTIPLIER_RAIL * 0.5,
            ComponentType.AGRICULTURE, BASE_INDUSTRY_PERFORMANCE_MULTIPLIER_RAIL * 0.5,
            ComponentType.OFFICE, BASE_INDUSTRY_PERFORMANCE_MULTIPLIER_RAIL * 0.5
        )),
        2500,
        BASE_PERFORMANCE_MULTIPLIER_RAIL * 4.5,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 350.0,
            ProductType.TECHNOLOGY, 70.0
        )),
        130
    ),

    /**
     * Represents high speed rail hub.
     * It affects {@link Happiness} and influences {@link Tourism}.
     */
    HIGH_SPEED_RAIL_HUB("Enables fast rail connections and improves transport efficiency.",
        60000, 20000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_RAIL * 5
        )),
        new EnumMap<>(Map.of(
            ComponentType.TOURISM, BASE_TOURISM_PERFORMANCE_MULTIPLIER_RAIL * 3.7
        )),
        4000,
        BASE_PERFORMANCE_MULTIPLIER_RAIL * 5,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 500.0,
            ProductType.TECHNOLOGY, 120.0
        )),
        180
    ),

    /**
     * Represents rail logistics center.
     * It affects {@link Happiness} and influences {@link Factory}, {@link Agriculture}, and {@link Office}.
     */
    RAIL_LOGISTICS_CENTER("Optimizes large-scale rail logistics and distribution.",
        55000, 18000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_RAIL
        )),
        new EnumMap<>(Map.of(
            ComponentType.FACTORY, BASE_INDUSTRY_PERFORMANCE_MULTIPLIER_RAIL,
            ComponentType.AGRICULTURE, BASE_INDUSTRY_PERFORMANCE_MULTIPLIER_RAIL,
            ComponentType.OFFICE, BASE_INDUSTRY_PERFORMANCE_MULTIPLIER_RAIL
        )),
        3500,
        BASE_PERFORMANCE_MULTIPLIER_RAIL * 6.5,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 480.0,
            ProductType.TECHNOLOGY, 110.0
        )),
        190
    ),

    /**
     * Represents metro station.
     * It affects {@link Happiness} and influences {@link Tourism}.
     */
    METRO_STATION("Supports urban rail transport and improves city mobility.",
        30000, 11000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_RAIL * 1.5
        )),
        new EnumMap<>(Map.of(
            ComponentType.TOURISM, BASE_TOURISM_PERFORMANCE_MULTIPLIER_RAIL * 1.6
        )),
        600,
        0,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 300.0,
            ProductType.TECHNOLOGY, 60.0
        )),
        110
    ),

    /**
     * Represents railway control center.
     * It affects {@link Happiness} and influences {@link Factory}, {@link Agriculture}, and {@link Office}.
     */
    RAILWAY_CONTROL_CENTER("Coordinates rail traffic and improves overall efficiency.",
        45000, 15000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_RAIL
        )),
        new EnumMap<>(Map.of(
            ComponentType.FACTORY, BASE_INDUSTRY_PERFORMANCE_MULTIPLIER_RAIL * 0.6,
            ComponentType.AGRICULTURE, BASE_INDUSTRY_PERFORMANCE_MULTIPLIER_RAIL * 0.6,
            ComponentType.OFFICE, BASE_INDUSTRY_PERFORMANCE_MULTIPLIER_RAIL * 0.6
        )),
        3000,
        BASE_PERFORMANCE_MULTIPLIER_RAIL * 5,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 450.0,
            ProductType.TECHNOLOGY, 100.0
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
    /** Performance multiplier provided by the building type. */
    private final double performanceMultiplier;
    /** Resource demand of the building type. */
    private final EnumMap<ProductType, Double> demand;
    /** Maximum worker capacity of the building type. */
    private final int maxWorkerAmount;

    /**
     * Creates the enum value definition for {@link RailTransportBuilding}.
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
    RailTransportBuilding(
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


