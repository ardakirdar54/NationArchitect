package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.product.ProductType;
import io.github.NationArchitect.model.metric.*;
import java.util.EnumMap;
import java.util.Map;
import static io.github.NationArchitect.model.component.BuildingConstants.*;

public enum RoadTransportBuilding implements BuildingType {

    BUS_TERMINAL("Supports public transportation and improves overall mobility.",
        10000, 4000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TR * 1.2
        )),
        1000,
        BASE_PERFORMANCE_MULTIPLIER_TR * 1.3,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 120.0,
            ProductType.TECHNOLOGY, 20.0
        ))
    ),

    TRUCK_TERMINAL("Handles freight transport and boosts logistics efficiency.",
        16000, 5500,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TR * 0.5
        )),
        1500,
        BASE_PERFORMANCE_MULTIPLIER_TR * 1.9,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 200.0,
            ProductType.TECHNOLOGY, 35.0
        ))
    ),

    PARKING_COMPLEX("Reduces congestion and improves urban traffic conditions.",
        8000, 2500,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_TR * 1.5
        )),
        700,
        BASE_PERFORMANCE_MULTIPLIER_TR * 1.2,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 80.0,
            ProductType.TECHNOLOGY, 10.0
        ))
    );

    private final String description;
    private final double constructionCost;
    private final double maintenanceCost;
    private final EnumMap<MetricType, Double> relatedMetrics;
    private final double occupiedLand;
    private final double performanceMultiplier;
    private final EnumMap<ProductType, Double> demand;

    RoadTransportBuilding(String description, double constructionCost, double maintenanceCost, EnumMap<MetricType, Double> relatedMetrics, double occupiedLand, double performanceMultiplier, double production, EnumMap<ProductType, Double> demand) {
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
