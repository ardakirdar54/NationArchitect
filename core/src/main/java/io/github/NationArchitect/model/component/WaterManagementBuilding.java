package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.metric.*;
import io.github.NationArchitect.model.product.ProductType;

import java.util.EnumMap;
import java.util.Map;

import static io.github.NationArchitect.model.component.BuildingConstants.*;

/**
 * Defines the building types available for the WaterManagement component.
 */
public enum WaterManagementBuilding implements BuildingType {

    /**
     * Represents water treatment plant.
     * Produces {@link ProductType#WATER}.
     * It affects {@link Happiness} and {@link HealthRate}.
     */
    WATER_TREATMENT_PLANT("Purifies water to improve public health and quality of life.",
        18000, 7000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_WM,
            MetricType.HEALTH_RATE, BASE_HEALTH_RATE_BOOST_WM
        )),
        new EnumMap<>(ComponentType.class),
        1600,
        BASE_PERFORMANCE_MULTIPLIER_WM,
        1000,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 200.0,
            ProductType.TECHNOLOGY, 50.0
        )),
        50
    ),

    /**
     * Represents water pumping station.
     * Produces {@link ProductType#WATER}.
     * It affects {@link Happiness} and influences {@link Agriculture}.
     */
    WATER_PUMPING_STATION("Distributes water efficiently across regions.",
        12000, 4500,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_WM * 0.7
        )),
        new EnumMap<>(Map.of(
            ComponentType.AGRICULTURE, BASE_PERFORMANCE_MULTIPLIER_WM * 0.6
        )),
        900,
        BASE_PERFORMANCE_MULTIPLIER_WM * 0.8,
        750,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 140.0,
            ProductType.TECHNOLOGY, 30.0
        )),
        30
    ),

    /**
     * Represents dam.
     * Produces {@link ProductType#WATER}.
     * It affects {@link Happiness} and {@link HealthRate} and influences {@link Agriculture} and {@link Electricity}.
     */
    DAM("Stabilizes water supply and improves overall water management efficiency.",
        90000, 30000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_WM * 1.6,
            MetricType.HEALTH_RATE, BASE_HEALTH_RATE_BOOST_WM * 1.2
        )),
        new EnumMap<>(Map.of(
            ComponentType.AGRICULTURE, BASE_PERFORMANCE_MULTIPLIER_WM,
            ComponentType.ELECTRICITY, BASE_PERFORMANCE_MULTIPLIER_WM * 0.8
        )),
        7000,
        BASE_PERFORMANCE_MULTIPLIER_WM * 2,
        0,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 500.0,
            ProductType.TECHNOLOGY, 200.0,
            ProductType.INDUSTRIAL_GOOD, 180.0
        )),
        180
    ),

    /**
     * Represents wastewater treatment plant.
     * Produces {@link ProductType#WATER}.
     * It affects {@link Happiness} and {@link HealthRate} and influences {@link HealthServices}.
     */
    WASTEWATER_TREATMENT_PLANT("Processes waste water to reduce environmental impact.",
        26000, 9500,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_WM * 3.1,
            MetricType.HEALTH_RATE, BASE_HEALTH_RATE_BOOST_WM * 2.4
        )),
        new EnumMap<>(Map.of(
            ComponentType.HEALTH_SERVICES, BASE_PERFORMANCE_MULTIPLIER_WM * 0.9
        )),
        1800,
        BASE_PERFORMANCE_MULTIPLIER_WM * 1.2,
        2300,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 260.0,
            ProductType.TECHNOLOGY, 80.0
        )),
        60
    ),

    /**
     * Represents water recycling facility.
     * Produces {@link ProductType#WATER}.
     * It affects {@link Happiness} and {@link HealthRate} and influences {@link Agriculture}.
     */
    WATER_RECYCLING_FACILITY("Reuses water to improve sustainability and efficiency.",
        35000, 14000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_WM * 1.3,
            MetricType.HEALTH_RATE, BASE_HEALTH_RATE_BOOST_WM * 1.2
        )),
        new EnumMap<>(Map.of(
            ComponentType.AGRICULTURE, BASE_PERFORMANCE_MULTIPLIER_WM * 0.8
        )),
        2200,
        BASE_PERFORMANCE_MULTIPLIER_WM * 1.5,
        2700,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 320.0,
            ProductType.TECHNOLOGY, 120.0
        )),
        80
    ),

    /**
     * Represents rainwater harvesting system.
     * Produces {@link ProductType#WATER}.
     * It affects {@link Happiness}.
     */
    RAINWATER_HARVESTING_SYSTEM("Collects rainwater to provide a small supplementary water supply.",
        7000, 2200,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_WM * 0.5
        )),
        new EnumMap<>(ComponentType.class),
        400,
        BASE_PERFORMANCE_MULTIPLIER_WM * 0.4,
        300,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 60.0,
            ProductType.TECHNOLOGY, 10.0
        )),
        12
    ),

    /**
     * Represents desalination plant.
     * Produces {@link ProductType#WATER}.
     * It affects {@link Happiness} and {@link HealthRate} and influences {@link MarineTransport}.
     */
    DESALINATION_PLANT("Converts seawater into usable water, ensuring supply in coastal regions.",
        65000, 26000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_WM * 1.4,
            MetricType.HEALTH_RATE, BASE_HEALTH_RATE_BOOST_WM * 1.6
        )),
        new EnumMap<>(Map.of(
            ComponentType.MARINE_TRANSPORT, BASE_PERFORMANCE_MULTIPLIER_WM
        )),
        2600,
        BASE_PERFORMANCE_MULTIPLIER_WM * 1.8,
        4000,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 900.0,
            ProductType.TECHNOLOGY, 240.0
        )),
        95
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
    /** Production output provided by the building type. */
    private final double production;
    /** Resource demand of the building type. */
    private final EnumMap<ProductType, Double> demand;
    /** Maximum worker capacity of the building type. */
    private final int maxWorkerAmount;

    /**
     * Creates the enum value definition for {@link WaterManagementBuilding}.
     *
     * @param description human-readable summary of the enum value
     * @param constructionCost construction cost of the building type
     * @param maintenanceCost maintenance cost of the building type
     * @param relatedMetrics metric effects applied by the building type
     * @param relatedComponents component effects applied by the building type
     * @param occupiedLand land occupied by the building type
     * @param performanceMultiplier performance multiplier provided by the building type
     * @param production production output provided by the building type
     * @param demand resource demand of the building type
     * @param maxWorkerAmount maximum worker capacity of the building type
     */
    WaterManagementBuilding(
        String description,
        double constructionCost,
        double maintenanceCost,
        EnumMap<MetricType, Double> relatedMetrics,
        EnumMap<ComponentType, Double> relatedComponents,
        double occupiedLand,
        double performanceMultiplier,
        double production,
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
        this.production = production;
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
