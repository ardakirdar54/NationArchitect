package io.github.NationArchitect.model.land;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;

import io.github.NationArchitect.model.component.Building;
import io.github.NationArchitect.model.component.Component;
import io.github.NationArchitect.model.component.ComponentType;
import io.github.NationArchitect.model.component.Education;
import io.github.NationArchitect.model.component.EducationBuilding;
import io.github.NationArchitect.model.component.HealthServices;
import io.github.NationArchitect.model.economy.Economy;
import io.github.NationArchitect.model.economy.RegionEconomy;
import io.github.NationArchitect.model.metric.*;
import io.github.NationArchitect.model.population.Age;
import io.github.NationArchitect.model.population.Population;

public class Region extends Land {

    private TerrainType terrainType;
    private EnumMap<ResourceType, Double> undergroundResources;
    private EnumMap<ComponentType, Component> components;
    // private ArrayList<Policy> activePolicies;
    private double landValue;
    private double baseCrimeRate;

    public Region(String name, RegionEconomy economy, Population population) {
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
    
    public double getComponentPerformance(ComponentType type) {
        return this.components.get(type).getPerformance();
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

    public int getTotalEmploymentCapacity() {
        int totalWorkers = 0;

        for(Component component : this.components.values()){
            for(Building building : component.getBuildings()){
                totalWorkers += building.getWorkerAmount();
            }
        }
        
        return totalWorkers;
    }

    public int getWorkingAgePopulation(){
        return this.getPopulation().getWorkingAgePopulation();
    }

    public double getBaseCrimeRate(){return this.baseCrimeRate;}

    public int getEducationBuildingCapacity(EducationBuilding type){
        int totalCapacity = 0;
        Education education = (Education) this.components.get(ComponentType.EDUCATION);
        for(Building building : education.getBuildings()){
            if(building.getType() == type){
                totalCapacity += type.getCapacity();
            }
        }
    }

    public int getTotalHealthServiceCapacity(){
        int totalCapacity = 0;
        HealthServices healthServices = (HealthServices) this.components.get(ComponentType.HEALTH_SERVICES);
        for(Building building : healthServices.getBuildings()){
            totalCapacity += building.getCapacity();
        }
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
        
        for (Metric metric : this.metrics.values()) {
            metric.calculateForRegion(this);
        }
        double birthRate = this.getMetricValue(MetricType.HAPPINESS);
        this.population.updateLifeCycle(birthRate, this.getMetricValue(MetricType.HEALTH_RATE));
    }
}
