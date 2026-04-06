package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.metric.*;
import io.github.NationArchitect.model.product.ProductType;

import java.util.EnumMap;
import java.util.Map;

import static io.github.NationArchitect.model.component.BuildingConstants.*;

public enum TourismBuilding implements BuildingType {

    HOTEL("Provides accommodation and supports tourism activity.",
        18000, 7000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TO
        )),
        new EnumMap<>(Map.of(
            ComponentType.TOURISM, BASE_PERFORMANCE_MULTIPLIER_TO
        )),
        900,
        BASE_PERFORMANCE_MULTIPLIER_TO * 0.8,
        new EnumMap<>(Map.of(
            ProductType.WATER, 200.0,
            ProductType.ENERGY, 260.0,
            ProductType.TECHNOLOGY, 40.0
        )),
        45
    ),

    RESORT("Offers high-quality leisure services and improves tourism appeal.",
        50000, 19000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TO * 2.2
        )),
        new EnumMap<>(Map.of(
            ComponentType.TOURISM, BASE_PERFORMANCE_MULTIPLIER_TO * 1.8,
            ComponentType.AIR_TRANSPORT, BASE_PERFORMANCE_MULTIPLIER_TO * 0.8
        )),
        2600,
        BASE_PERFORMANCE_MULTIPLIER_TO * 1.8,
        new EnumMap<>(Map.of(
            ProductType.WATER, 700.0,
            ProductType.ENERGY, 800.0,
            ProductType.TECHNOLOGY, 140.0
        )),
        140
    ),

    TOURIST_ATTRACTION("Draws visitors and increases overall tourism activity.",
        30000, 12000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TO * 1.6
        )),
        new EnumMap<>(Map.of(
            ComponentType.TOURISM, BASE_PERFORMANCE_MULTIPLIER_TO * 1.5
        )),
        1500,
        BASE_PERFORMANCE_MULTIPLIER_TO * 1.2,
        new EnumMap<>(Map.of(
            ProductType.WATER, 250.0,
            ProductType.ENERGY, 350.0,
            ProductType.TECHNOLOGY, 70.0
        )),
        70
    ),

    AMUSEMENT_PARK("Provides entertainment and significantly boosts tourism attractiveness.",
        70000, 26000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TO * 3
        )),
        new EnumMap<>(Map.of(
            ComponentType.TOURISM, BASE_PERFORMANCE_MULTIPLIER_TO * 2.4,
            ComponentType.ROAD_TRANSPORT, BASE_PERFORMANCE_MULTIPLIER_TO
        )),
        3200,
        BASE_PERFORMANCE_MULTIPLIER_TO * 2,
        new EnumMap<>(Map.of(
            ProductType.WATER, 800.0,
            ProductType.ENERGY, 1200.0,
            ProductType.TECHNOLOGY, 180.0
        )),
        180
    ),

    CULTURAL_SITE("Preserves heritage and attracts cultural tourism.",
        40000, 15000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TO * 2
        )),
        new EnumMap<>(Map.of(
            ComponentType.TOURISM, BASE_PERFORMANCE_MULTIPLIER_TO * 1.7,
            ComponentType.EDUCATION, BASE_PERFORMANCE_MULTIPLIER_TO * 0.7
        )),
        1800,
        BASE_PERFORMANCE_MULTIPLIER_TO * 1.3,
        new EnumMap<>(Map.of(
            ProductType.WATER, 240.0,
            ProductType.ENERGY, 280.0,
            ProductType.TECHNOLOGY, 60.0
        )),
        65
    ),

    CONVENTION_CENTER("Hosts large events and boosts business tourism.",
        85000, 32000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TO * 2.4
        )),
        new EnumMap<>(Map.of(
            ComponentType.TOURISM, BASE_PERFORMANCE_MULTIPLIER_TO * 2.2,
            ComponentType.OFFICE, BASE_PERFORMANCE_MULTIPLIER_TO
        )),
        2800,
        BASE_PERFORMANCE_MULTIPLIER_TO * 2.1,
        new EnumMap<>(Map.of(
            ProductType.WATER, 500.0,
            ProductType.ENERGY, 850.0,
            ProductType.TECHNOLOGY, 200.0
        )),
        160
    ),

    TOURISM_CENTER("Coordinates tourism services and improves overall efficiency.",
        55000, 21000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TO * 1.8
        )),
        new EnumMap<>(Map.of(
            ComponentType.TOURISM, BASE_PERFORMANCE_MULTIPLIER_TO * 2,
            ComponentType.INTERNET, BASE_PERFORMANCE_MULTIPLIER_TO * 0.8
        )),
        1700,
        BASE_PERFORMANCE_MULTIPLIER_TO * 1.7,
        new EnumMap<>(Map.of(
            ProductType.WATER, 260.0,
            ProductType.ENERGY, 420.0,
            ProductType.TECHNOLOGY, 120.0
        )),
        90
    ),

    LUXURY_HOTEL("Provides premium accommodation and greatly enhances tourism value.",
        110000, 42000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TO * 3.2
        )),
        new EnumMap<>(Map.of(
            ComponentType.TOURISM, BASE_PERFORMANCE_MULTIPLIER_TO * 2.8,
            ComponentType.AIR_TRANSPORT, BASE_PERFORMANCE_MULTIPLIER_TO,
            ComponentType.ROAD_TRANSPORT, BASE_PERFORMANCE_MULTIPLIER_TO * 0.6
        )),
        2400,
        BASE_PERFORMANCE_MULTIPLIER_TO * 2.4,
        new EnumMap<>(Map.of(
            ProductType.WATER, 620.0,
            ProductType.ENERGY, 950.0,
            ProductType.TECHNOLOGY, 240.0
        )),
        150
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

    TourismBuilding(
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
}
