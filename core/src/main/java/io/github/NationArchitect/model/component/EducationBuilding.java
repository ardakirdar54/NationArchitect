package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.product.ProductType;
import io.github.NationArchitect.model.metric.*;
import java.util.EnumMap;
import java.util.Map;
import static io.github.NationArchitect.model.component.BuildingConstants.*;

public enum EducationBuilding implements BuildingType {

    PRIMARY_SCHOOL("Provides basic education and slightly improves workforce skill levels.",
        8000, 4000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_ED,
            MetricType.EDUCATION_LEVEL, BASE_EDUCATION_LEVEL_BOOST_ED * 0.5
        )),
        800,
        0,
        new EnumMap<>(Map.of(
            ProductType.WATER, 200.0,
            ProductType.ENERGY, 300.0,
            ProductType.TECHNOLOGY, 50.0
        )),
        30,
        250
    ),

    HIGH_SCHOOL("Improves education level and increases workforce productivity.",
        16000, 6000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_ED * 2.5,
            MetricType.EDUCATION_LEVEL, BASE_EDUCATION_LEVEL_BOOST_ED * 2
        )),
        1500,
        0,
        new EnumMap<>(Map.of(
            ProductType.WATER, 300.0,
            ProductType.ENERGY, 450.0,
            ProductType.TECHNOLOGY, 70.0
        )),
        70,
        500
    ),

    UNIVERSITY("Boosts higher education and significantly increases innovation and productivity.",
        60000, 20000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_ED * 6,
            MetricType.EDUCATION_LEVEL, BASE_EDUCATION_LEVEL_BOOST_ED * 5
        )),
        5000,
        0,
        new EnumMap<>(Map.of(
            ProductType.WATER, 1000.0,
            ProductType.ENERGY, 1300.0,
            ProductType.TECHNOLOGY, 250.0
        )),
        500,
        3000
    ),

    RESEARCH_INSTITUTE("Drives advanced research and greatly enhances innovation capacity.",
        110000, 28000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_ED * 5,
            MetricType.EDUCATION_LEVEL, BASE_EDUCATION_LEVEL_BOOST_ED * 5
        )),
        4000,
        BASE_PERFORMANCE_MULTIPLIER_ED * 10,
        new EnumMap<>(Map.of(
            ProductType.WATER, 700.0,
            ProductType.ENERGY, 900.0,
            ProductType.TECHNOLOGY, 450.0
        )),
        400,
        0
    ),

    VOCATIONAL_TRAINING_CENTER("Provides practical training to improve workforce efficiency.",
        60000, 25000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_ED * 3,
            MetricType.EDUCATION_LEVEL, BASE_EDUCATION_LEVEL_BOOST_ED * 2.5
        )),
        2000,
        BASE_PERFORMANCE_MULTIPLIER_ED * 2.5,
        new EnumMap<>(Map.of(
            ProductType.WATER, 600.0,
            ProductType.ENERGY, 600.0,
            ProductType.TECHNOLOGY, 150.0
        )),
        200,
        0
    );

    private final String description;
    private final double constructionCost;
    private final double maintenanceCost;
    private final EnumMap<MetricType, Double> relatedMetrics;
    private final double occupiedLand;
    private final double performanceMultiplier;
    private final EnumMap<ProductType, Double> demand;
    private final int maxWorkerAmount;
    private final int capacity;

    EducationBuilding(
        String description,
        double constructionCost,
        double maintenanceCost,
        EnumMap<MetricType, Double> relatedMetrics,
        double occupiedLand,
        double performanceMultiplier,
        EnumMap<ProductType, Double> demand,
        int maxWorkerAmount,
        int capacity
    ) {
        this.description = description;
        this.constructionCost = constructionCost;
        this.maintenanceCost = maintenanceCost;
        this.relatedMetrics = relatedMetrics;
        this.occupiedLand = occupiedLand;
        this.performanceMultiplier = performanceMultiplier;
        this.demand = demand;
        this.maxWorkerAmount = maxWorkerAmount;
        this.capacity = capacity;
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
        return null;
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
        return capacity;
    }
}
