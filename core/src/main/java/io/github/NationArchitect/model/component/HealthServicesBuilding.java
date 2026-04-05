package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.metric.*;
import io.github.NationArchitect.model.product.ProductType;

import java.util.EnumMap;
import java.util.Map;

import static io.github.NationArchitect.model.component.BuildingConstants.*;

public enum HealthServicesBuilding implements BuildingType {

    CLINIC("Provides basic healthcare services and improves public well-being.",
        12000, 4500,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_HS,
            MetricType.HEALTH_RATE, BASE_HEALTH_RATE_BOOST_HS
        )),
        700,
        BASE_PERFORMANCE_MULTIPLIER_HS,
        new EnumMap<>(Map.of(
            ProductType.WATER, 180.0,
            ProductType.ENERGY, 220.0,
            ProductType.TECHNOLOGY, 60.0
        )),
        35
    ),

    HOSPITAL("Offers advanced medical care and increases overall health standards.",
        42000, 16000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_HS * 2.5,
            MetricType.HEALTH_RATE, BASE_HEALTH_RATE_BOOST_HS * 3
        )),
        new EnumMap<>(Map.of(
            ComponentType.HEALTH_SERVICES, BASE_PERFORMANCE_MULTIPLIER_HS * 2,
            ComponentType.SECURITY, BASE_PERFORMANCE_MULTIPLIER_HS
        )),
        2200,
        BASE_PERFORMANCE_MULTIPLIER_HS,
        new EnumMap<>(Map.of(
            ProductType.WATER, 500.0,
            ProductType.ENERGY, 700.0,
            ProductType.TECHNOLOGY, 180.0
        )),
        180
    ),

    EMERGENCY_RESPONSE_CENTER("Improves response time to crises and reduces their impact.",
        28000, 11000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_HS * 1.8,
            MetricType.HEALTH_RATE, BASE_HEALTH_RATE_BOOST_HS * 1.9
        )),
        new EnumMap<>(Map.of(
            ComponentType.SECURITY, BASE_PERFORMANCE_MULTIPLIER_HS * 1.5,
            ComponentType.ROAD_NETWORK, BASE_PERFORMANCE_MULTIPLIER_HS
        )),
        1300,
        BASE_PERFORMANCE_MULTIPLIER_HS * 1.4,
        new EnumMap<>(Map.of(
            ProductType.WATER, 260.0,
            ProductType.ENERGY, 420.0,
            ProductType.TECHNOLOGY, 120.0
        )),
        90
    ),

    MEDICAL_RESEARCH_CENTER("Advances medical knowledge and supports long-term health improvements.",
        85000, 28000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_HS * 2.2,
            MetricType.HEALTH_RATE, BASE_HEALTH_RATE_BOOST_HS * 4
        )),
        new EnumMap<>(Map.of(
            ComponentType.EDUCATION, BASE_PERFORMANCE_MULTIPLIER_HS * 2,
            ComponentType.INTERNET, BASE_PERFORMANCE_MULTIPLIER_HS * 1.5,
            ComponentType.HEALTH_SERVICES, BASE_PERFORMANCE_MULTIPLIER_HS * 2.5
        )),
        2600,
        BASE_PERFORMANCE_MULTIPLIER_HS * 2.3,
        new EnumMap<>(Map.of(
            ProductType.WATER, 650.0,
            ProductType.ENERGY, 950.0,
            ProductType.TECHNOLOGY, 320.0
        )),
        220
    ),

    PHARMACEUTICAL_PRODUCTION_FACILITY("Ensures availability of medicines and supports healthcare efficiency.",
        110000, 42000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_HS * 2,
            MetricType.HEALTH_RATE, BASE_HEALTH_RATE_BOOST_HS * 3.2
        )),
        new EnumMap<>(Map.of(
            ComponentType.FACTORY, BASE_PERFORMANCE_MULTIPLIER_HS * 1.4,
            ComponentType.HEALTH_SERVICES, BASE_PERFORMANCE_MULTIPLIER_HS * 2.4
        )),
        3200,
        BASE_PERFORMANCE_MULTIPLIER_HS * 2,
        new EnumMap<>(Map.of(
            ProductType.WATER, 800.0,
            ProductType.ENERGY, 1200.0,
            ProductType.TECHNOLOGY, 420.0,
            ProductType.INDUSTRIAL_GOOD, 240.0
        )),
        260
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

    HealthServicesBuilding(String description, double constructionCost, double maintenanceCost,
        EnumMap<MetricType, Double> relatedMetrics, EnumMap<ComponentType, Double> relatedComponents,
        double occupiedLand, double performanceMultiplier, EnumMap<ProductType, Double> demand,
        int maxWorkerAmount) {
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
}
