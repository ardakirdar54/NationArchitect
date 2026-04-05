package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.product.ProductType;
import io.github.NationArchitect.model.metric.*;
import java.util.EnumMap;
import java.util.Map;
import static io.github.NationArchitect.model.component.BuildingConstants.*;

public enum OfficeBuilding implements BuildingType {

    SMALL_OFFICE(
        "Produces basic technology services and supports economic activity.",
        10000,
        3500,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_OF
        )),
        400.0,
        180,
        new EnumMap<>(Map.of(
            ProductType.WATER, 120.0,
            ProductType.ENERGY, 180.0
        )),
        40
    ),

    CORPORATE_OFFICE(
        "Produces technology products and improves overall productivity.",
        22000,
        9000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_OF * 1.8
        )),
        750.0,
        210,
        new EnumMap<>(Map.of(
            ProductType.WATER, 250.0,
            ProductType.ENERGY, 420.0,
            ProductType.TECHNOLOGY, 40.0
        )),
        100
    ),

    BUSINESS_CENTER(
        "Hosts multiple companies to increase technology production efficiency.",
        40000,
        16000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_OF * 2.8
        )),
        1300.0,
        375,
        new EnumMap<>(Map.of(
            ProductType.WATER, 420.0,
            ProductType.ENERGY, 650.0,
            ProductType.TECHNOLOGY, 90.0
        )),
        220
    ),

    TECH_OFFICE(
        "Focuses on advanced technology production and innovation.",
        70000,
        26000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_OF * 3.5
        )),
        1600.0,
        600,
        new EnumMap<>(Map.of(
            ProductType.WATER, 500.0,
            ProductType.ENERGY, 900.0,
            ProductType.TECHNOLOGY, 180.0
        )),
        280
    ),

    STARTUP_HUB(
        "Encourages innovation and produces emerging technology solutions.",
        110000,
        42000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_OF * 5
        )),
        2200.0,
        850,
        new EnumMap<>(Map.of(
            ProductType.WATER, 650.0,
            ProductType.ENERGY, 1200.0,
            ProductType.TECHNOLOGY, 300.0
        )),
        420
    ),

    HEADQUARTERS(
        "Centralizes operations to maximize technology production efficiency.",
        160000,
        65000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_OF * 6.5
        )),
        3200.0,
        1300,
        new EnumMap<>(Map.of(
            ProductType.WATER, 900.0,
            ProductType.ENERGY, 1650.0,
            ProductType.TECHNOLOGY, 450.0
        )),
        700
    ),

    OFFICE_COMPLEX(
        "Integrates multiple offices to significantly boost technology output.",
        260000,
        100000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_OF * 8
        )),
        5000.0,
        2100,
        new EnumMap<>(Map.of(
            ProductType.WATER, 1400.0,
            ProductType.ENERGY, 2500.0,
            ProductType.TECHNOLOGY, 700.0
        )),
        1300
    );

    private final String description;
    private final double constructionCost;
    private final double maintenanceCost;
    private final EnumMap<MetricType, Double> relatedMetrics;
    private final double occupiedLand;
    private final double production;
    private final EnumMap<ProductType, Double> demand;
    private final int maxWorkerAmount;

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
}
