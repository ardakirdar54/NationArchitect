package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.product.ProductType;
import io.github.NationArchitect.model.metric.*;
import java.util.EnumMap;
import java.util.Map;
import static io.github.NationArchitect.model.component.BuildingConstants.*;

public enum RailTransportBuilding implements BuildingType {

    TRAIN_STATION("Supports passenger rail transport and improves mobility.",
        25000, 9000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TR * 1.5
        )),
        1800,
        BASE_PERFORMANCE_MULTIPLIER_TR * 3,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 250.0,
            ProductType.TECHNOLOGY, 50.0
        ))
    ),

    CARGO_RAIL_TERMINAL("Facilitates large-scale goods transportation via rail.",
        40000, 13000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TR * 0.5
        )),
        3000,
        BASE_PERFORMANCE_MULTIPLIER_TR * 4,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 400.0,
            ProductType.TECHNOLOGY, 80.0
        ))
    ),

    RAILWAY_DEPOT("Maintains trains and improves overall rail system efficiency.",
        30000, 10000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TR
        )),
        2000,
        BASE_PERFORMANCE_MULTIPLIER_TR * 3.5,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 300.0,
            ProductType.TECHNOLOGY, 60.0
        ))
    ),

    FREIGHT_YARD("Handles cargo sorting and boosts logistics efficiency.",
        35000, 12000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TR * 0.5
        )),
        2500,
        BASE_PERFORMANCE_MULTIPLIER_TR * 4.5,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 350.0,
            ProductType.TECHNOLOGY, 70.0
        ))
    ),

    HIGH_SPEED_RAIL_HUB("Enables fast rail connections and improves transport efficiency.",
        60000, 20000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TR * 2
        )),
        4000,
        BASE_PERFORMANCE_MULTIPLIER_TR * 5,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 500.0,
            ProductType.TECHNOLOGY, 120.0
        ))
    ),

    RAIL_LOGISTICS_CENTER("Optimizes large-scale rail logistics and distribution.",
        55000, 18000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TR
        )),
        3500,
        BASE_PERFORMANCE_MULTIPLIER_TR * 5.5,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 480.0,
            ProductType.TECHNOLOGY, 110.0
        ))
    ),

    METRO_STATION("Supports urban rail transport and improves city mobility.",
        30000, 11000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TR * 1.5
        )),
        2000,
        BASE_PERFORMANCE_MULTIPLIER_TR * 3.5,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 300.0,
            ProductType.TECHNOLOGY, 60.0
        ))
    ),

    RAILWAY_CONTROL_CENTER("Coordinates rail traffic and improves overall efficiency.",
        45000, 15000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TR
        )),
        3000,
        BASE_PERFORMANCE_MULTIPLIER_TR * 5,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 450.0,
            ProductType.TECHNOLOGY, 100.0
        ))
    );

    private final String description;
    private final double constructionCost;
    private final double maintenanceCost;
    private final EnumMap<MetricType, Double> relatedMetrics;
    private final double occupiedLand;
    private final double performanceMultiplier;
    private final EnumMap<ProductType, Double> demand;

    RailTransportBuilding(String description, double constructionCost, double maintenanceCost, EnumMap<MetricType, Double> relatedMetrics, double occupiedLand, double performanceMultiplier, double production, EnumMap<ProductType, Double> demand) {
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
