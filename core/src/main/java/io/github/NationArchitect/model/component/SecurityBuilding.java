package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.metric.*;
import io.github.NationArchitect.model.product.ProductType;

import java.util.EnumMap;
import java.util.Map;

import static io.github.NationArchitect.model.component.BuildingConstants.*;

public enum SecurityBuilding implements BuildingType {

    POLICE_STATION("Maintains law enforcement and improves public safety.",
        16000, 6000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_SC,
            MetricType.CRIME_RATE, BASE_CRIME_RATE_BOOST_SC
        )),
        new EnumMap<>(Map.of(
            ComponentType.SECURITY, BASE_PERFORMANCE_MULTIPLIER_SC
        )),
        900,
        0,
        new EnumMap<>(Map.of(
            ProductType.WATER, 120.0,
            ProductType.ENERGY, 180.0,
            ProductType.TECHNOLOGY, 40.0
        )),
        50
    ),

    SURVEILLANCE_CENTER("Enhances monitoring capabilities and helps prevent criminal activities.",
        30000, 12000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_SC * 1.2,
            MetricType.CRIME_RATE, BASE_CRIME_RATE_BOOST_SC * 1.4
        )),
        new EnumMap<>(Map.of(
            ComponentType.SECURITY, BASE_PERFORMANCE_MULTIPLIER_SC * 1.5,
            ComponentType.OFFICE, BASE_PERFORMANCE_MULTIPLIER_SC * 0.7
        )),
        1400,
        BASE_PERFORMANCE_MULTIPLIER_SC,
        new EnumMap<>(Map.of(
            ProductType.WATER, 150.0,
            ProductType.ENERGY, 320.0,
            ProductType.TECHNOLOGY, 120.0
        )),
        80
    ),

    SPECIAL_FORCES_UNIT("Handles high-risk situations and reduces the impact of major threats.",
        60000, 22000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_SC * 1.5,
            MetricType.CRIME_RATE, BASE_CRIME_RATE_BOOST_SC * 2
        )),
        new EnumMap<>(Map.of(
            ComponentType.SECURITY, BASE_PERFORMANCE_MULTIPLIER_SC * 2,
            ComponentType.AIR_TRANSPORT, BASE_PERFORMANCE_MULTIPLIER_SC
        )),
        2200,
        BASE_PERFORMANCE_MULTIPLIER_SC * 1.4,
        new EnumMap<>(Map.of(
            ProductType.WATER, 200.0,
            ProductType.ENERGY, 550.0,
            ProductType.TECHNOLOGY, 220.0
        )),
        120
    ),

    INTELLIGENCE_AGENCY("Gathers critical information to improve national security and stability.",
        90000, 34000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_SC * 1.6,
            MetricType.CRIME_RATE, BASE_CRIME_RATE_BOOST_SC * 2.4,
            MetricType.STABILITY, BASE_HAPPINESS_BOOST_SC * 1.4
        )),
        new EnumMap<>(Map.of(
            ComponentType.SECURITY, BASE_PERFORMANCE_MULTIPLIER_SC * 2.2,
            ComponentType.INTERNET, BASE_PERFORMANCE_MULTIPLIER_SC * 1.8,
            ComponentType.OFFICE, BASE_PERFORMANCE_MULTIPLIER_SC
        )),
        2600,
        BASE_PERFORMANCE_MULTIPLIER_SC * 2,
        new EnumMap<>(Map.of(
            ProductType.WATER, 260.0,
            ProductType.ENERGY, 700.0,
            ProductType.TECHNOLOGY, 320.0
        )),
        170
    ),

    CYBER_SECURITY_CENTER("Protects digital infrastructure and reduces cyber-related risks.",
        105000, 42000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_SC * 1.4,
            MetricType.CRIME_RATE, BASE_CRIME_RATE_BOOST_SC * 2,
            MetricType.STABILITY, BASE_HAPPINESS_BOOST_SC * 1.8
        )),
        new EnumMap<>(Map.of(
            ComponentType.INTERNET, BASE_PERFORMANCE_MULTIPLIER_SC * 2.4,
            ComponentType.OFFICE, BASE_PERFORMANCE_MULTIPLIER_SC * 1.5,
            ComponentType.SECURITY, BASE_PERFORMANCE_MULTIPLIER_SC * 1.8
        )),
        2000,
        BASE_PERFORMANCE_MULTIPLIER_SC * 2.4,
        new EnumMap<>(Map.of(
            ProductType.WATER, 200.0,
            ProductType.ENERGY, 850.0,
            ProductType.TECHNOLOGY, 450.0
        )),
        150
    ),

    PRISON_FACILITY("Manages offenders and supports long-term crime reduction.",
        75000, 28000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_SC * 0.7,
            MetricType.CRIME_RATE, BASE_CRIME_RATE_BOOST_SC * 2.7
        )),
        new EnumMap<>(Map.of(
            ComponentType.SECURITY, BASE_PERFORMANCE_MULTIPLIER_SC * 1.7
        )),
        3500,
        0,
        new EnumMap<>(Map.of(
            ProductType.WATER, 500.0,
            ProductType.ENERGY, 650.0,
            ProductType.TECHNOLOGY, 120.0
        )),
        210
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

    SecurityBuilding(String description, double constructionCost, double maintenanceCost,
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
