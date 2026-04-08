package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.product.ProductType;
import io.github.NationArchitect.model.metric.*;
import java.util.EnumMap;
import java.util.Map;
import static io.github.NationArchitect.model.component.BuildingConstants.*;

/**
 * Defines the building types available for the MarineTransport component.
 */
public enum MarineTransportBuilding implements BuildingType {

    /**
     * Represents port.
     * It affects {@link Happiness} and influences {@link Tourism}.
     */
    PORT("Supports basic sea transport and improves trade efficiency.",
        55000, 17000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_MT * 0.5
        )),
        new EnumMap<>(Map.of(
            ComponentType.TOURISM, BASE_INDUSTRY_PERFORMANCE_MULTIPLIER_MT * 1.2
        )),
        3500,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 500.0,
            ProductType.TECHNOLOGY, 100.0
        )),
        160
    ),

    /**
     * Represents commercial port.
     * It affects {@link Happiness} and influences {@link Tourism}.
     */
    COMMERCIAL_PORT("Handles large-scale cargo operations and boosts trade capacity.",
        110000, 32000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_MT
        )),
        new EnumMap<>(Map.of(
            ComponentType.TOURISM, BASE_INDUSTRY_PERFORMANCE_MULTIPLIER_MT * 2.5
        )),
        7000,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 900.0,
            ProductType.TECHNOLOGY, 200.0
        )),
        340
    ),

    /**
     * Represents cargo terminal.
     * It affects {@link Happiness} and influences {@link Factory}, {@link Agriculture}, and {@link Office}.
     */
    CARGO_TERMINAL("Facilitates efficient loading and unloading of goods.",
        80000, 24000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_MT * 0.5
        )),
        new EnumMap<>(Map.of(
            ComponentType.FACTORY, BASE_INDUSTRY_PERFORMANCE_MULTIPLIER_MT * 2.9,
            ComponentType.AGRICULTURE, BASE_INDUSTRY_PERFORMANCE_MULTIPLIER_MT * 2.9,
            ComponentType.OFFICE, BASE_INDUSTRY_PERFORMANCE_MULTIPLIER_MT * 2.9
        )),
        5000,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 750.0,
            ProductType.TECHNOLOGY, 150.0
        )),
        240
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
    /** Resource demand of the building type. */
    private final EnumMap<ProductType, Double> demand;
    /** Maximum worker capacity of the building type. */
    private final int maxWorkerAmount;

    /**
     * Creates the enum value definition for {@link MarineTransportBuilding}.
     *
     * @param description human-readable summary of the enum value
     * @param constructionCost construction cost of the building type
     * @param maintenanceCost maintenance cost of the building type
     * @param relatedMetrics metric effects applied by the building type
     * @param relatedComponents component effects applied by the building type
     * @param occupiedLand land occupied by the building type
     * @param demand resource demand of the building type
     * @param maxWorkerAmount maximum worker capacity of the building type
     */
    MarineTransportBuilding(
        String description,
        double constructionCost,
        double maintenanceCost,
        EnumMap<MetricType, Double> relatedMetrics,
        EnumMap<ComponentType, Double> relatedComponents,
        double occupiedLand,
        EnumMap<ProductType, Double> demand,
        int maxWorkerAmount
    ) {
        this.description = description;
        this.constructionCost = constructionCost;
        this.maintenanceCost = maintenanceCost;
        this.relatedMetrics = relatedMetrics;
        this.relatedComponents = relatedComponents;
        this.occupiedLand = occupiedLand;
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
        return 0;
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


