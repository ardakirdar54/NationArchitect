package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.product.ProductType;
import io.github.NationArchitect.model.metric.*;
import java.util.EnumMap;
import java.util.Map;
import static io.github.NationArchitect.model.component.BuildingConstants.*;

/**
 * Defines the building types available for the RoadTransport component.
 */
public enum RoadTransportBuilding implements BuildingType {

    /**
     * Represents bus terminal.
     * It affects {@link Happiness} and influences {@link Tourism}.
     */
    BUS_TERMINAL("Supports public transportation and improves overall mobility.",
        10000, 4000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_RT * 1.2
        )),
        new EnumMap<>(Map.of(
            ComponentType.TOURISM, BASE_TOURISM_PERFORMANCE_MULTIPLIER_RT * 0.8
        )),
        1000,
        BASE_PERFORMANCE_MULTIPLIER_RT * 1.3,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 120.0,
            ProductType.TECHNOLOGY, 20.0
        )),
        45
    ),

    /**
     * Represents truck terminal.
     * It affects {@link Happiness} and influences {@link Factory}, {@link Agriculture}, and {@link Office}.
     */
    TRUCK_TERMINAL("Handles freight transport and boosts logistics efficiency.",
        16000, 5500,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_RT * 0.5
        )),
        new EnumMap<>(Map.of(
            ComponentType.FACTORY, BASE_INDUSTRY_PERFORMANCE_MULTIPLIER_RT,
            ComponentType.AGRICULTURE, BASE_INDUSTRY_PERFORMANCE_MULTIPLIER_RT,
            ComponentType.OFFICE, BASE_INDUSTRY_PERFORMANCE_MULTIPLIER_RT
        )),
        1500,
        BASE_PERFORMANCE_MULTIPLIER_RT * 1.9,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 200.0,
            ProductType.TECHNOLOGY, 35.0
        )),
        60
    ),

    /**
     * Represents parking complex.
     * It affects {@link Happiness} and influences {@link Tourism}.
     */
    PARKING_COMPLEX("Reduces congestion and improves urban traffic conditions.",
        8000, 2500,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_RT * 1.5
        )),
        new EnumMap<>(Map.of(
            ComponentType.TOURISM, BASE_TOURISM_PERFORMANCE_MULTIPLIER_RT * 1.2
        )),
        700,
        BASE_PERFORMANCE_MULTIPLIER_RT * 1.2,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 80.0,
            ProductType.TECHNOLOGY, 10.0
        )),
        20
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
     * Creates the enum value definition for {@link RoadTransportBuilding}.
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
    RoadTransportBuilding(
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



