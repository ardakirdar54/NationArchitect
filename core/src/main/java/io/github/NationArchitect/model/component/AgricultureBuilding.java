package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.product.ProductType;
import io.github.NationArchitect.model.metric.*;
import java.util.EnumMap;
import java.util.Map;

import static io.github.NationArchitect.model.component.BuildingConstants.*;

public enum AgricultureBuilding implements BuildingType {

    SMALL_FARM("Produces essential agricultural goods and supports the food supply.",
        10000,
        4000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_AG
        )),
        1000,
        0,
        BASE_PRODUCTION_AG,
        new EnumMap<>(Map.of(
            ProductType.WATER, 100.0,
            ProductType.ENERGY, 150.0,
            ProductType.TECHNOLOGY, 15.0
        )),
        10
    ),

    MEDIUM_FARM("Produces essential agricultural goods and supports the food supply.",
        25000,
        9500,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_AG * 4
        )),
        3500,
        0,
        BASE_PRODUCTION_AG * 3.5,
        new EnumMap<>(Map.of(
            ProductType.WATER, 250.0,
            ProductType.ENERGY, 300.0,
            ProductType.TECHNOLOGY, 35.0
        )),
        35
    ),

    LARGE_FARM("Produces essential agricultural goods and supports the food supply.",
        55000,
        20000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_AG * 10
        )),
        8000,
        0,
        BASE_PRODUCTION_AG * 10,
        new EnumMap<>(Map.of(
            ProductType.WATER, 650.0,
            ProductType.ENERGY, 800.0,
            ProductType.TECHNOLOGY, 90.0
        )),
        80
    ),

    SMALL_GREENHOUSE("Produces agricultural goods in a controlled environment and improves production efficiency.",
        20000,
        5500,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_AG * 2
        )),
        1500,
        BASE_PERFORMANCE_MULTIPLIER_AG,
        BASE_PRODUCTION_AG * 2.5,
        new EnumMap<>(Map.of(
            ProductType.WATER, 325.0,
            ProductType.ENERGY, 350.0,
            ProductType.TECHNOLOGY, 45.0
        )),
        15
    ),

    MEDIUM_GREENHOUSE("Produces agricultural goods in a controlled environment and improves production efficiency.",
        45000,
        10000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_AG * 7
        )),
        5000,
        BASE_PERFORMANCE_MULTIPLIER_AG * 4,
        BASE_PRODUCTION_AG * 6,
        new EnumMap<>(Map.of(
            ProductType.WATER, 600.0,
            ProductType.ENERGY, 800.0,
            ProductType.TECHNOLOGY, 75.0
        )),
        40
    ),

    LARGE_GREENHOUSE("Produces agricultural goods in a controlled environment and improves production efficiency.",
        100000,
        18000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_AG * 8
        )),
        6500,
        BASE_PERFORMANCE_MULTIPLIER_AG * 7.5,
        BASE_PRODUCTION_AG * 11,
        new EnumMap<>(Map.of(
            ProductType.WATER, 1000.0,
            ProductType.ENERGY, 1300.0,
            ProductType.TECHNOLOGY, 200.0
        )),
        100
    ),

    IRRIGATION_SYSTEM("Improves water distribution to increase farming productivity.",
        80000,
        25000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_AG * 5
        )),
        900,
        BASE_PERFORMANCE_MULTIPLIER_AG * 5,
        0,
        new EnumMap<>(Map.of(
            ProductType.WATER, 250.0,
            ProductType.ENERGY, 300.0,
            ProductType.TECHNOLOGY, 50.0
        )),
        40
    ),

    FERTILIZER_PLANT("Enhances soil productivity and boosts agricultural output.",
        35000,
        10000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_AG * 2.5
        )),
        1500,
        BASE_PERFORMANCE_MULTIPLIER_AG * 1.5,
        0,
        new EnumMap<>(Map.of(
            ProductType.WATER, 500.0,
            ProductType.ENERGY, 250.0,
            ProductType.TECHNOLOGY, 70.0
        )),
        60
    ),

    AGRICULTURAL_RESEARCH_CENTER("Develops advanced farming techniques to improve efficiency.",
        250000,
        85000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_AG * 8
        )),
        800,
        BASE_PERFORMANCE_MULTIPLIER_AG * 13.5,
        0,
        new EnumMap<>(Map.of(
            ProductType.WATER, 1200.0,
            ProductType.ENERGY, 1500.0,
            ProductType.TECHNOLOGY, 700.0
        )),
        150
    );

    private final String description;
    private final double constructionCost;
    private final double maintenanceCost;
    private final EnumMap<MetricType, Double> relatedMetrics;
    private final double occupiedLand;
    private final double performanceMultiplier;
    private final double production;
    private final EnumMap<ProductType, Double> demand;
    private final int maxWorkerAmount;

    AgricultureBuilding(
        String description,
        double constructionCost,
        double maintenanceCost,
        EnumMap<MetricType, Double> relatedMetrics,
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
        return null;
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
}
