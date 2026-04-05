package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.product.ProductType;
import io.github.NationArchitect.model.metric.*;
import java.util.EnumMap;
import java.util.Map;
import static io.github.NationArchitect.model.component.BuildingConstants.*;

public enum AirTransportBuilding implements BuildingType {

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

    INTERNATIONAL_AIRPORT("Handles large-scale air traffic and significantly improves global connectivity.",
        250000, 70000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_AT * 3
        )),
        null, 70000,
        BASE_PERFORMANCE_MULTIPLIER_AT * 3,
        new EnumMap<>(Map.of(
            ProductType.INDUSTRIAL_GOOD, 1500.0,
            ProductType.WATER, 2400.0,
            ProductType.ENERGY, 2400.0,
            ProductType.TECHNOLOGY, 700.0
        )),
        2500
    ),

    CARGO_AIR_TERMINAL("Facilitates air freight operations and boosts trade efficiency.",
        80000, 24000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_AT * 0.3
        )),
        null, 45000,
        BASE_PERFORMANCE_MULTIPLIER_AT * 7,
        new EnumMap<>(Map.of(
            ProductType.INDUSTRIAL_GOOD, 1000.0,
            ProductType.WATER, 1400.0,
            ProductType.ENERGY, 1500.0,
            ProductType.TECHNOLOGY, 500.0
        )),
        1200
    ),

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

    private final String description;
    private final double constructionCost;
    private final double maintenanceCost;
    private final EnumMap<MetricType, Double> relatedMetrics;
    private final EnumMap<ComponentType, Double> relatedComponents;
    private final double occupiedLand;
    private final double performanceMultiplier;
    private final EnumMap<ProductType, Double> demand;
    private final int maxWorkerAmount;

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
}
