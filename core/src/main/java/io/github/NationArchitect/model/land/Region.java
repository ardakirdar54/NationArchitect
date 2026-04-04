package io.github.NationArchitect.model.land;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;

import io.github.NationArchitect.model.component.Component;
import io.github.NationArchitect.model.component.ComponentType;
import io.github.NationArchitect.model.economy.Economy;
import io.github.NationArchitect.model.metric.*;
import io.github.NationArchitect.model.population.Population;

public class Region extends Land {

    private TerrainType terrainType;
    private EnumMap<ResourceType, Double> undergroundResources;
    private EnumMap<ComponentType, Component> components;
    // private ArrayList<Policy> activePolicies;
    private double landValue;

    public Region(String name, Economy economy, Population population) {
        super(name, economy, population);
        // this.activePolicies = new ArrayList<>();

    }

    public Region setTerrainType(TerrainType terrainType) {
        this.terrainType = terrainType;
        return this;
    }

    public Region setLandValue(double landValue) {
        this.landValue = landValue;
        return this;
    }

    public Region setComponents(EnumMap<ComponentType, Component> components) {
        this.components = components;
        return this;
    }

    public double getSecurityPerformance() {
        return this.components.get(ComponentType.SECURITY).getPerformance();
    }

    public double getEducationPerformance() {
        return this.components.get(ComponentType.EDUCATION).getPerformance();
    }

    public double getHealthServicesPerformance() {
        return this.components.get(ComponentType.HEALTH_SERVICES).getPerformance();
    }

    public double getInfrastructurePerformance() {
        double sumOfInfrastructurePerformances = 0;
        ArrayList<Double> listOfPerformances = new ArrayList<>();

        listOfPerformances.add(this.components.get(ComponentType.ELECTRICITY).getPerformance());
        listOfPerformances.add(this.components.get(ComponentType.WATER_MANAGEMENT).getPerformance());
        listOfPerformances.add(this.components.get(ComponentType.INTERNET).getPerformance());
        
        Collections.sort(listOfPerformances);

        int totalWeight = 0;
        
        int currentWeight = listOfPerformances.size();

        for (double performance : listOfPerformances) {
            sumOfInfrastructurePerformances += performance * currentWeight;
            totalWeight += currentWeight;
            currentWeight--;
        }

        return sumOfInfrastructurePerformances / totalWeight;
    }

    public int getUnemployedPeople() {
        
    }

    @Override
    public void implementPolicy(Policy policy) {
        this.activePolicies.add(policy);
    }

    @Override
    public void cancelPolicy(Policy policy) {
        this.activePolicies.remove(policy);
    }

    @Override
    public void update() {
        EnumMap<MetricType, Metric> currentRegionMetrics = this.getMetrics();
        for (Metric metric : currentRegionMetrics.values()) {
            metric.calculateForRegion(this);
        }
        double birthRate = this.getHappiness().getValue();
        this.getPopulation().updateLifeCycle(birthRate, this.getHealthRate().getValue());
    }
}
