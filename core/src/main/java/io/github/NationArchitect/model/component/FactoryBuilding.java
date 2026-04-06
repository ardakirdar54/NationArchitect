package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.product.ProductType;
import io.github.NationArchitect.model.metric.*;
import java.util.EnumMap;
import java.util.Map;

import static io.github.NationArchitect.model.component.BuildingConstants.*;

public enum FactoryBuilding implements BuildingType  {

    BASIC_FACTORY(
        "Produces standard industrial goods and supports economic activity.",
        12000,
        5000,
        new EnumMap<MetricType, Double>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_F
        )),
        null,
        600.0,
        400,
        new EnumMap<ProductType, Double>(Map.of(
            ProductType.WATER, 200.0,
            ProductType.ENERGY, 300.0,
            ProductType.TECHNOLOGY, 50.0
        )),
        60
    ),

    ADVANCED_FACTORY(
        "Improves production efficiency and produces higher-value goods.",
        25000,
        11000,
        new EnumMap<MetricType, Double>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_F * 3
        )),
        null,
        1000.0,
        800,
        new EnumMap<ProductType, Double>(Map.of(
            ProductType.WATER, 500.0,
            ProductType.ENERGY, 700.0,
            ProductType.TECHNOLOGY, 80.0
        )),
        150
    ),

    AUTOMATED_FACTORY(
        "Uses automation to maximize production efficiency.",
        45000,
        24000,
        new EnumMap<MetricType, Double>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_F * 7
        )),
        null,
        1500.0,
        2400,
        new EnumMap<ProductType, Double>(Map.of(
            ProductType.WATER, 900.0,
            ProductType.ENERGY, 1000.0,
            ProductType.TECHNOLOGY, 150.0
        )),
        50
    ),

    HEAVY_INDUSTRY_PLANT(
        "Handles large-scale industrial production.",
        100000,
        50000,
        new EnumMap<MetricType, Double>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_F * 10
        )),
        null,
        3300.0,
        4000,
        new EnumMap<ProductType, Double>(Map.of(
            ProductType.WATER, 900.0,
            ProductType.ENERGY, 1000.0,
            ProductType.TECHNOLOGY, 150.0
        )),
        500
    ),

    INDUSTRIAL_COMPLEX(
        "Integrates multiple production processes to maximize output.",
        200000,
        90000,
        new EnumMap<MetricType, Double>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_F * 25
        )),
        null,
        8000.0,
        10000,
        new EnumMap<ProductType, Double>(Map.of(
            ProductType.WATER, 2500.0,
            ProductType.ENERGY, 3000.0,
            ProductType.TECHNOLOGY, 1000.0
        )),
        2000
    ),

    STEEL_FACTORY(
        "Produces steel for industrial use and supports heavy industry.",
        90000,
        45000,
        new EnumMap<MetricType, Double>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_F * 10
        )),
        new EnumMap<ComponentType, Double>(Map.of(
            ComponentType.ELECTRICITY, BASE_COMPONENT_PERFORMANCE_MULTIPLIER_F * 6,
            ComponentType.RAIL_TRANSPORT, BASE_COMPONENT_PERFORMANCE_MULTIPLIER_F * 8,
            ComponentType.ROAD_NETWORK, BASE_COMPONENT_PERFORMANCE_MULTIPLIER_F * 4
        )),
        2000.0,
        2000,
        new EnumMap<ProductType, Double>(Map.of(
            ProductType.WATER, 900.0,
            ProductType.ENERGY, 1000.0,
            ProductType.TECHNOLOGY, 150.0
        )),
        600
    ),

    CHEMICAL_PLANT(
        "Produces chemical products used across multiple industries.",
        85000,
        40000,
        new EnumMap<MetricType, Double>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_F * 10
        )),
        new EnumMap<ComponentType, Double>(Map.of(
            ComponentType.WATER_MANAGEMENT, BASE_COMPONENT_PERFORMANCE_MULTIPLIER_F * 4,
            ComponentType.ELECTRICITY, BASE_COMPONENT_PERFORMANCE_MULTIPLIER_F * 3,
            ComponentType.AGRICULTURE, BASE_COMPONENT_PERFORMANCE_MULTIPLIER_F * 3
        )),
        1500.0,
        1800,
        new EnumMap<ProductType, Double>(Map.of(
            ProductType.WATER, 850.0,
            ProductType.ENERGY, 950.0,
            ProductType.TECHNOLOGY, 350.0
        )),
        600
    ),

    ELECTRONICS_FACTORY(
        "Manufactures electronic goods and supports advanced industries.",
        150000,
        80000,
        new EnumMap<MetricType, Double>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_F * 10
        )),
        new EnumMap<ComponentType, Double>(Map.of(
            ComponentType.INTERNET, BASE_COMPONENT_PERFORMANCE_MULTIPLIER_F * 4,
            ComponentType.ELECTRICITY, BASE_COMPONENT_PERFORMANCE_MULTIPLIER_F * 3,
            ComponentType.EDUCATION, BASE_COMPONENT_PERFORMANCE_MULTIPLIER_F * 2,
            ComponentType.AIR_TRANSPORT, BASE_COMPONENT_PERFORMANCE_MULTIPLIER_F * 6,
            ComponentType.HEALTH_SERVICES, BASE_COMPONENT_PERFORMANCE_MULTIPLIER_F * 5,
            ComponentType.AGRICULTURE, BASE_COMPONENT_PERFORMANCE_MULTIPLIER_F * 4,
            ComponentType.OFFICE, BASE_COMPONENT_PERFORMANCE_MULTIPLIER_F * 7,
            ComponentType.TOURISM, BASE_COMPONENT_PERFORMANCE_MULTIPLIER_F * 5
        )),
        2100.0,
        2000,
        new EnumMap<ProductType, Double>(Map.of(
            ProductType.WATER, 550.0,
            ProductType.ENERGY, 1350.0,
            ProductType.TECHNOLOGY, 850.0
        )),
        600
    ),

    TEXTILE_FACTORY(
        "Produces fabrics and consumer goods for the market.",
        115000,
        60000,
        new EnumMap<MetricType, Double>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_F * 10
        )),
        new EnumMap<ComponentType, Double>(Map.of(
            ComponentType.HEALTH_SERVICES, BASE_COMPONENT_PERFORMANCE_MULTIPLIER_F * 9
        )),
        1000.0,
        1500,
        new EnumMap<ProductType, Double>(Map.of(
            ProductType.WATER, 1350.0,
            ProductType.ENERGY, 850.0,
            ProductType.TECHNOLOGY, 250.0
        )),
        400
    ),

    FOOD_PROCESSING_FACTORY(
        "Processes raw food into consumable products.",
        1800,
        300,
        new EnumMap<MetricType, Double>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_F * 10
        )),
        new EnumMap<ComponentType, Double>(Map.of(
            ComponentType.AGRICULTURE, BASE_COMPONENT_PERFORMANCE_MULTIPLIER_F * 8,
            ComponentType.WATER_MANAGEMENT, BASE_COMPONENT_PERFORMANCE_MULTIPLIER_F * 5
        )),
        3500,
        1500,
        new EnumMap<ProductType, Double>(Map.of(
            ProductType.WATER, 1500.0,
            ProductType.ENERGY, 1250.0,
            ProductType.TECHNOLOGY, 450.0
        )),
        350
    ),

    AUTOMOBILE_FACTORY(
        "Manufactures vehicles and boosts industrial output.",
        200000,
        80000,
        new EnumMap<MetricType, Double>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_F * 10
        )),
        new EnumMap<ComponentType, Double>(Map.of(
            ComponentType.ROAD_TRANSPORT, BASE_COMPONENT_PERFORMANCE_MULTIPLIER_F * 9,
            ComponentType.RAIL_TRANSPORT, BASE_COMPONENT_PERFORMANCE_MULTIPLIER_F * 9
        )),
        3000.0,
        2500,
        new EnumMap<ProductType, Double>(Map.of(
            ProductType.WATER, 1300.0,
            ProductType.ENERGY, 2000.0,
            ProductType.TECHNOLOGY, 800.0
        )),
        1000
    ),

    MACHINERY_FACTORY(
        "Produces industrial machinery to support production systems.",
        120000,
        50000,
        new EnumMap<MetricType, Double>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_F * 10
        )),
        new EnumMap<ComponentType, Double>(Map.of(
            ComponentType.ELECTRICITY, BASE_COMPONENT_PERFORMANCE_MULTIPLIER_F * 5,
            ComponentType.EDUCATION, BASE_COMPONENT_PERFORMANCE_MULTIPLIER_F * 4
        )),
        2500.0,
        1250,
        new EnumMap<ProductType, Double>(Map.of(
            ProductType.WATER, 1300.0,
            ProductType.ENERGY, 2000.0,
            ProductType.TECHNOLOGY, 800.0
        )),
        900
    ),

    PHARMACEUTICAL_FACTORY(
        "Produces medical goods and supports healthcare systems.",
        140000,
        60000,
        new EnumMap<MetricType, Double>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_F * 10
        )),
        new EnumMap<ComponentType, Double>(Map.of(
            ComponentType.HEALTH_SERVICES, BASE_COMPONENT_PERFORMANCE_MULTIPLIER_F * 9,
            ComponentType.EDUCATION, BASE_COMPONENT_PERFORMANCE_MULTIPLIER_F * 4
        )),
        1600.0,
        1800,
        new EnumMap<ProductType, Double>(Map.of(
            ProductType.WATER, 1200.0,
            ProductType.ENERGY, 1000.0,
            ProductType.TECHNOLOGY, 800.0
        )),
        450
    ),

    HIGH_TECH_FACTORY(
        "Produces advanced technological goods with high efficiency.",
        150000,
        70000,
        new EnumMap<MetricType, Double>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_F * 10
        )),
        new EnumMap<ComponentType, Double>(Map.of(
            ComponentType.INTERNET, BASE_COMPONENT_PERFORMANCE_MULTIPLIER_F * 7,
            ComponentType.EDUCATION, BASE_COMPONENT_PERFORMANCE_MULTIPLIER_F * 8,
            ComponentType.ELECTRICITY, BASE_COMPONENT_PERFORMANCE_MULTIPLIER_F * 5,
            ComponentType.OFFICE, BASE_COMPONENT_PERFORMANCE_MULTIPLIER_F * 5
        )),
        3500.0,
        2200,
        new EnumMap<ProductType, Double>(Map.of(
            ProductType.WATER, 1000.0,
            ProductType.ENERGY, 2000.0,
            ProductType.TECHNOLOGY, 1200.0
        )),
        800
    ),

    SHIPYARD("Maintains and produces ships to support maritime operations.",
        90000,
        26000,
        new EnumMap<>(Map.of(
            MetricType.HAPPINESS, BASE_HAPPINESS_BOOST_F * 10
        )),
        new EnumMap<ComponentType, Double>(Map.of(
            ComponentType.MARINE_TRANSPORT, BASE_COMPONENT_PERFORMANCE_MULTIPLIER_F * 8
        )),
        4000,
        2800,
        new EnumMap<>(Map.of(
            ProductType.ENERGY, 800.0,
            ProductType.TECHNOLOGY, 580.0
        )),
        150
    );

    private final String description;
    private final double constructionCost;
    private final double maintenanceCost;
    private final EnumMap<MetricType, Double> relatedMetrics;
    private final EnumMap<ComponentType, Double> relatedComponents;
    private final double occupiedLand;
    private final double production;
    private final EnumMap<ProductType, Double> demand;
    private final int maxWorkerAmount;

    FactoryBuilding(
        String description,
        double constructionCost,
        double maintenanceCost,
        EnumMap<MetricType, Double> relatedMetrics,
        EnumMap<ComponentType, Double> relatedComponents,
        double occupiedLand,
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
        return relatedComponents;
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

    public int getMaxWorkerAmount() {
        return maxWorkerAmount;
    }
}
