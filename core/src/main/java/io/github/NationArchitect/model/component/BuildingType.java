package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.product.ProductType;

import java.util.EnumMap;

public interface BuildingType {

    public String getName();

    public double getConstructionCost();

    public double getMaintenanceCost();

    public EnumMap<MetricType, Double> getRelatedMetrics();

    public EnumMap<ComponentType, Double> getRelatedComponents();

    public double getOccupiedLand();

    public double getPerformanceMultiplier();

    public double getProduction();

    public EnumMap<ProductType, Double> getDemand();

    public String getDescription();

    public int getMaxWorkerAmount();

}
