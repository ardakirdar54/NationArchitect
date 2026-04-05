package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.metric.*;
import io.github.NationArchitect.model.product.ProductType;

import java.util.EnumMap;
import java.util.Map;

import static io.github.NationArchitect.model.component.BuildingConstants.*;

public enum WaterManagementBuilding implements BuildingType {

    WATER_TREATMENT_PLANT("Purifies water to improve public health and quality of life.",
        18000, 7000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_WM,
            MetricType.HEALTH_RATE, BASE_HEALTH_RATE_BOOST_WM
        )),
        new EnumMap<>(Map.of(
            ComponentType.WATER_MANAGEMENT, BASE_PERFORMANCE_MULTIPLIER_WM
        )),
        1600,
        BASE_PERFORMANCE_MULTIPLIER_WM,
        BASE_PRODUCTION_WM,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 200.0,
            ProductType.TECHNOLOGY, 50.0
        )),
        50
    ),

    WATER_PUMPING_STATION("Distributes water efficiently across regions.",
        12000, 4500,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_WM * 0.7
        )),
        new EnumMap<>(Map.of(
            ComponentType.WATER_MANAGEMENT, BASE_PERFORMANCE_MULTIPLIER_WM * 0.8,
            ComponentType.AGRICULTURE, BASE_PERFORMANCE_MULTIPLIER_WM * 0.6
        )),
        900,
        BASE_PERFORMANCE_MULTIPLIER_WM * 0.8,
        BASE_PRODUCTION_WM * 0.8,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 140.0,
            ProductType.TECHNOLOGY, 30.0
        )),
        30
    ),

    DAM("Stabilizes water supply and improves overall water management efficiency.",
        90000, 30000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_WM * 1.6,
            MetricType.HEALTH_RATE, BASE_HEALTH_RATE_BOOST_WM * 1.2
        )),
        new EnumMap<>(Map.of(
            ComponentType.WATER_MANAGEMENT, BASE_PERFORMANCE_MULTIPLIER_WM * 2.2,
            ComponentType.AGRICULTURE, BASE_PERFORMANCE_MULTIPLIER_WM,
            ComponentType.ELECTRICITY, BASE_PERFORMANCE_MULTIPLIER_WM * 0.8
        )),
        7000,
        BASE_PERFORMANCE_MULTIPLIER_WM * 2,
        BASE_PRODUCTION_WM * 4.5,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 500.0,
            ProductType.TECHNOLOGY, 200.0,
            ProductType.INDUSTRIAL_GOOD, 180.0
        )),
        180
    ),

    WASTEWATER_TREATMENT_PLANT("Processes waste water to reduce environmental impact.",
        26000, 9500,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_WM * 1.1,
            MetricType.HEALTH_RATE, BASE_HEALTH_RATE_BOOST_WM * 1.4
        )),
        new EnumMap<>(Map.of(
            ComponentType.WATER_MANAGEMENT, BASE_PERFORMANCE_MULTIPLIER_WM * 1.4,
            ComponentType.HEALTH_SERVICES, BASE_PERFORMANCE_MULTIPLIER_WM * 0.9
        )),
        1800,
        BASE_PERFORMANCE_MULTIPLIER_WM * 1.2,
        BASE_PRODUCTION_WM * 1.1,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 260.0,
            ProductType.TECHNOLOGY, 80.0
        )),
        60
    ),

    WATER_RECYCLING_FACILITY("Reuses water to improve sustainability and efficiency.",
        35000, 14000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_WM * 1.3,
            MetricType.HEALTH_RATE, BASE_HEALTH_RATE_BOOST_WM * 1.2
        )),
        new EnumMap<>(Map.of(
            ComponentType.WATER_MANAGEMENT, BASE_PERFORMANCE_MULTIPLIER_WM * 1.7,
            ComponentType.AGRICULTURE, BASE_PERFORMANCE_MULTIPLIER_WM * 0.8
        )),
        2200,
        BASE_PERFORMANCE_MULTIPLIER_WM * 1.5,
        BASE_PRODUCTION_WM * 1.6,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 320.0,
            ProductType.TECHNOLOGY, 120.0
        )),
        80
    ),

    RAINWATER_HARVESTING_SYSTEM("Collects rainwater to provide a small supplementary water supply.",
        7000, 2200,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_WM * 0.5
        )),
        new EnumMap<>(Map.of(
            ComponentType.WATER_MANAGEMENT, BASE_PERFORMANCE_MULTIPLIER_WM * 0.5
        )),
        400,
        BASE_PERFORMANCE_MULTIPLIER_WM * 0.4,
        BASE_PRODUCTION_WM * 0.35,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 60.0,
            ProductType.TECHNOLOGY, 10.0
        )),
        12
    ),

    DESALINATION_PLANT("Converts seawater into usable water, ensuring supply in coastal regions.",
        65000, 26000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_WM * 1.4,
            MetricType.HEALTH_RATE, BASE_HEALTH_RATE_BOOST_WM * 1.6
        )),
        new EnumMap<>(Map.of(
            ComponentType.WATER_MANAGEMENT, BASE_PERFORMANCE_MULTIPLIER_WM * 2,
            ComponentType.MARINE_TRANSPORT, BASE_PERFORMANCE_MULTIPLIER_WM
        )),
        2600,
        BASE_PERFORMANCE_MULTIPLIER_WM * 1.8,
        BASE_PRODUCTION_WM * 2.4,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 900.0,
            ProductType.TECHNOLOGY, 240.0
        )),
        95
    );

    private final String description;
    private final double constructionCost;
    private final double maintenanceCost;
    private final EnumMap<MetricType, Double> relatedMetrics;
    private final EnumMap<ComponentType, Double> relatedComponents;
    private final double occupiedLand;
    private final double performanceMultiplier;
    private final double production;
    private final EnumMap<ProductType, Double> demand;
    private final int maxWorkerAmount;

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
}
