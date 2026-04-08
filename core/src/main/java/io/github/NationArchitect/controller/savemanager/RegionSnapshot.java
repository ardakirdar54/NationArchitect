package io.github.NationArchitect.controller.savemanager;

import io.github.NationArchitect.model.land.ResourceType;
import io.github.NationArchitect.model.land.TerrainType;

import java.util.Map;

public class RegionSnapshot {
    public String name;
    public TerrainType terrainType;
    public double landValue;
    public double baseCrimeRate;
    public PopulationSnapshot population;
    public MetricSnapshot[] metrics;
    public TaxSnapshot tax;
    public Map<String, Double> undergroundResources;
    public ComponentSnapshot[] components;

    public RegionSnapshot() {
    }
}
