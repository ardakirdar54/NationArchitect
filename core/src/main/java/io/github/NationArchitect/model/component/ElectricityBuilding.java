package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.product.ProductType;
import io.github.NationArchitect.model.metric.*;
import java.util.EnumMap;
import java.util.Map;
import static io.github.NationArchitect.model.component.BuildingConstants.*;

/**
 * Defines the building types available for the Electricity component.
 */
public enum ElectricityBuilding implements BuildingType {

    /**
     * Represents coal power plant.
     * Produces {@link ProductType#ENERGY}.
     * It affects {@link Happiness}.
     */
    COAL_POWER_PLANT("Generates high electricity output at low cost but increases pollution and risk.",
        10000, 3000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_PENALTY_EL * 4
        )),
        2500,
        500,
        new EnumMap<>(Map.of(
            ProductType.WATER, 300.0,
            ProductType.TECHNOLOGY, 50.0
        )),
        100
    ),

    /**
     * Represents natural gas power plant.
     * Produces {@link ProductType#ENERGY}.
     * It affects {@link Happiness}.
     */
    NATURAL_GAS_POWER_PLANT("Produces efficient and cleaner energy with moderate output.",
        20000, 12000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_PENALTY_EL
        )),
        2200,
        1300,
        new EnumMap<>(Map.of(
            ProductType.WATER, 200.0,
            ProductType.TECHNOLOGY, 80.0
        )),
        100
    ),

    /**
     * Represents oil power plant.
     * Produces {@link ProductType#ENERGY}.
     * It affects {@link Happiness}.
     */
    OIL_POWER_PLANT("Provides reliable energy but comes with higher operational costs.",
        45000, 20000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_PENALTY_EL * 3
        )),
        5000,
        3500,
        new EnumMap<>(Map.of(
            ProductType.WATER, 250.0,
            ProductType.TECHNOLOGY, 70.0
        )),
        100
    ),

    /**
     * Represents wind power plant.
     * Produces {@link ProductType#ENERGY}.
     * It affects {@link Happiness}.
     */
    WIND_POWER_PLANT("Generates clean energy with variable output depending on conditions.",
        35000, 8000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_EL * 4
        )),
        3500,
        1000,
        new EnumMap<>(Map.of(
            ProductType.TECHNOLOGY, 400.0
        )),
        100
    ),

    /**
     * Represents solar power plant.
     * Produces {@link ProductType#ENERGY}.
     * It affects {@link Happiness}.
     */
    SOLAR_POWER_PLANT("Produces sustainable energy with lower but consistent output.",
        30000, 7000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_EL * 5
        )),
        5000,
        1000,
        new EnumMap<>(Map.of(
            ProductType.TECHNOLOGY, 150.0
        )),
        300
    ),

    /**
     * Represents hydroelectric power plant.
     * Produces {@link ProductType#ENERGY}.
     * It affects {@link Happiness}.
     */
    HYDROELECTRIC_POWER_PLANT("Generates stable and strong energy using water resources.",
        80000, 20000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_EL * 6
        )),
        9000,
        1000,
        new EnumMap<>(Map.of(
            ProductType.WATER, 1000.0,
            ProductType.TECHNOLOGY, 120.0
        )),
        500
    ),

    /**
     * Represents nuclear power plant.
     * Produces {@link ProductType#ENERGY}.
     * It affects {@link Happiness}.
     */
    NUCLEAR_POWER_PLANT("Produces massive energy output with high cost and potential risks.",
        150000, 40000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_PENALTY_EL * 2
        )),
        15000,
        30000,
        new EnumMap<>(Map.of(
            ProductType.WATER, 2000.0,
            ProductType.TECHNOLOGY, 1500.0
        )),
        1000
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
    /** Production output provided by the building type. */
    private final double production;
    /** Resource demand of the building type. */
    private final EnumMap<ProductType, Double> demand;
    /** Maximum worker capacity of the building type. */
    private final int maxWorkerAmount;

    /**
     * Creates the enum value definition for {@link ElectricityBuilding}.
     *
     * @param description human-readable summary of the enum value
     * @param constructionCost construction cost of the building type
     * @param maintenanceCost maintenance cost of the building type
     * @param relatedMetrics metric effects applied by the building type
     * @param occupiedLand land occupied by the building type
     * @param production production output provided by the building type
     * @param demand resource demand of the building type
     * @param maxWorkerAmount maximum worker capacity of the building type
     */
    ElectricityBuilding(
        String description,
        double constructionCost,
        double maintenanceCost,
        EnumMap<MetricType, Double> relatedMetrics,
        double occupiedLand,
        double production,
        EnumMap<ProductType, Double> demand,
        int maxWorkerAmount
    ) {
        this.description = description;
        this.constructionCost = constructionCost;
        this.maintenanceCost = maintenanceCost;
        this.relatedMetrics = relatedMetrics;
        this.occupiedLand = occupiedLand;
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

    @Override
    public EnumMap<ComponentType, Double> getRelatedComponents() {
        return null;
    }

    public double getOccupiedLand() {
        return occupiedLand;
    }

    public double getPerformanceMultiplier() {
        return 0;
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
        return this.maxWorkerAmount;
    }

    public int getCapacity() {
        return 0;
    }
}



