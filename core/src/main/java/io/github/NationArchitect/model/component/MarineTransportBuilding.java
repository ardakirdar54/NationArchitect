package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.product.ProductType;
import io.github.NationArchitect.model.metric.*;
import java.util.EnumMap;
import java.util.Map;
import static io.github.NationArchitect.model.component.BuildingConstants.*;

public enum MarineTransportBuilding implements BuildingType {

    PORT("Supports basic sea transport and improves trade efficiency.",
        55000, 17000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TR * 0.5
        )),
        3500,
        BASE_PERFORMANCE_MULTIPLIER_TR * 6,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 500.0,
            ProductType.TECHNOLOGY, 100.0
        ))
    ),

    COMMERCIAL_PORT("Handles large-scale cargo operations and boosts trade capacity.",
        110000, 32000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TR
        )),
        7000,
        BASE_PERFORMANCE_MULTIPLIER_TR * 8.5,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 900.0,
            ProductType.TECHNOLOGY, 200.0
        ))
    ),

    CARGO_TERMINAL("Facilitates efficient loading and unloading of goods.",
        80000, 24000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TR * 0.5
        )),
        5000,
        BASE_PERFORMANCE_MULTIPLIER_TR * 7.5,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 750.0,
            ProductType.TECHNOLOGY, 150.0
        ))
    ),

    SHIPYARD("Maintains and produces ships to support maritime operations.",
        90000, 26000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TR
        )),
        4000,
        BASE_PERFORMANCE_MULTIPLIER_TR * 7,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 800.0,
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

    MarineTransportBuilding(String description, double constructionCost, double maintenanceCost, EnumMap<MetricType, Double> relatedMetrics, double occupiedLand, double performanceMultiplier, double production, EnumMap<ProductType, Double> demand) {
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

    public EnumMap<ProductType, Double> getDemand() {
        return new EnumMap<>(demand);
    }

    public String getDescription() {
        return description;
    }
}
