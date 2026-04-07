package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.product.ProductType;
import io.github.NationArchitect.model.metric.*;
import java.util.EnumMap;
import java.util.Map;
import static io.github.NationArchitect.model.component.BuildingConstants.*;

/**
 * Defines the building types available for the Office component.
 */
public enum OfficeBuilding implements BuildingType {

    /**
     * Represents small office.
     * Produces {@link ProductType#TECHNOLOGY}.
     * It affects {@link Happiness}.
     */
    SMALL_OFFICE(
        "Produces basic technology services and supports economic activity.",
        10000,
        3500,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_OF
        )),
        400.0,
        100,
        new EnumMap<>(Map.of(
            ProductType.WATER, 120.0,
            ProductType.ENERGY, 180.0
        )),
        40
    ),

    /**
     * Represents corporate office.
     * Produces {@link ProductType#TECHNOLOGY}.
     * It affects {@link Happiness}.
     */
    CORPORATE_OFFICE(
        "Produces technology products and improves overall productivity.",
        22000,
        9000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_OF * 1.8
        )),
        1000.0,
        280,
        new EnumMap<>(Map.of(
            ProductType.WATER, 250.0,
            ProductType.ENERGY, 420.0,
            ProductType.TECHNOLOGY, 40.0
        )),
        100
    ),

    /**
     * Represents business center.
     * Produces {@link ProductType#TECHNOLOGY}.
     * It affects {@link Happiness}.
     */
    BUSINESS_CENTER(
        "Hosts multiple companies to increase technology production efficiency.",
        40000,
        16000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_OF * 2.8
        )),
        1300.0,
        700,
        new EnumMap<>(Map.of(
            ProductType.WATER, 420.0,
            ProductType.ENERGY, 650.0,
            ProductType.TECHNOLOGY, 90.0
        )),
        220
    ),

    /**
     * Represents tech office.
     * Produces {@link ProductType#TECHNOLOGY}.
     * It affects {@link Happiness}.
     */
    TECH_OFFICE(
        "Focuses on advanced technology production and innovation.",
        70000,
        36000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_OF * 5.5
        )),
        1600.0,
        1000,
        new EnumMap<>(Map.of(
            ProductType.WATER, 500.0,
            ProductType.ENERGY, 900.0,
            ProductType.TECHNOLOGY, 180.0
        )),
        280
    ),

    /**
     * Represents startup hub.
     * Produces {@link ProductType#TECHNOLOGY}.
     * It affects {@link Happiness}.
     */
    STARTUP_HUB(
        "Encourages innovation and produces emerging technology solutions.",
        110000,
        42000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_OF * 5
        )),
        2200.0,
        1500,
        new EnumMap<>(Map.of(
            ProductType.WATER, 650.0,
            ProductType.ENERGY, 1200.0,
            ProductType.TECHNOLOGY, 300.0
        )),
        420
    ),

    /**
     * Represents headquarters.
     * Produces {@link ProductType#TECHNOLOGY}.
     * It affects {@link Happiness}.
     */
    HEADQUARTERS(
        "Centralizes operations to maximize technology production efficiency.",
        160000,
        65000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_OF * 6.5
        )),
        3200.0,
        2500,
        new EnumMap<>(Map.of(
            ProductType.WATER, 1400.0,
            ProductType.ENERGY, 1650.0,
            ProductType.TECHNOLOGY, 450.0
        )),
        700
    ),

    /**
     * Represents office complex.
     * Produces {@link ProductType#TECHNOLOGY}.
     * It affects {@link Happiness}.
     */
    OFFICE_COMPLEX(
        "Integrates multiple offices to significantly boost technology output.",
        260000,
        120000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_OF * 8
        )),
        5000.0,
        5000,
        new EnumMap<>(Map.of(
            ProductType.WATER, 2400.0,
            ProductType.ENERGY, 3500.0,
            ProductType.TECHNOLOGY, 1000.0
        )),
        1300
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
     * Creates the enum value definition for {@link OfficeBuilding}.
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
    OfficeBuilding(
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

    @Override
    public int getMaxWorkerAmount() {
        return maxWorkerAmount;
    }

    public int getCapacity() {
        return 0;
    }
}



