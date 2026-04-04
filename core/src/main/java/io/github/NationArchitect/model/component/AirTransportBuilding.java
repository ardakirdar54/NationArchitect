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
        30000,
        BASE_PERFORMANCE_MULTIPLIER_AT,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 650.0,
            ProductType.TECHNOLOGY, 150.0
        ))
    ),

    INTERNATIONAL_AIRPORT("Handles large-scale air traffic and significantly improves global connectivity.",
        250000, 70000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_AT * 3
        )),
        70000,
        BASE_PERFORMANCE_MULTIPLIER_AT * 3,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 1000.0,
            ProductType.TECHNOLOGY, 300.0
        ))
    ),

    CARGO_AIR_TERMINAL("Facilitates air freight operations and boosts trade efficiency.",
        80000, 24000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_AT * 0.3
        )),
        45000,
        BASE_PERFORMANCE_MULTIPLIER_AT * 7,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 900.0,
            ProductType.TECHNOLOGY, 220.0
        ))
    ),

    AIRCRAFT_MAINTENANCE_HANGAR("Ensures aircraft reliability and improves operational efficiency.",
        50000, 16000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_AT
        )),
        3000,
        BASE_PERFORMANCE_MULTIPLIER_AT * 6.5,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 500.0,
            ProductType.TECHNOLOGY, 200.0
        ))
    ),

    AVIATION_TRAINING_CENTER("Improves workforce expertise and enhances overall air transport efficiency.",
        55000, 17000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_AT * 1.5
        )),
        3500,
        BASE_PERFORMANCE_MULTIPLIER_AT * 6,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 450.0,
            ProductType.TECHNOLOGY, 180.0
        ))
    );

    private final String description;
    private final double constructionCost;
    private final double maintenanceCost;
    private final EnumMap<MetricType, Double> relatedMetrics;
    private final double occupiedLand;
    private final double performanceMultiplier;
    private final EnumMap<ProductType, Double> demand;

    AirTransportBuilding(String description, double constructionCost, double maintenanceCost, EnumMap<MetricType, Double> relatedMetrics, double occupiedLand, double performanceMultiplier, double production, EnumMap<ProductType, Double> demand) {
        this.description = description;
        this.constructionCost = constructionCost;
        this.maintenanceCost = maintenanceCost;
        this.relatedMetrics = relatedMetrics;
        this.occupiedLand = occupiedLand;
        this.performanceMultiplier = performanceMultiplier;
        this.demand = demand;
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
}
