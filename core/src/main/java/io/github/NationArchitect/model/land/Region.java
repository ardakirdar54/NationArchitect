package io.github.NationArchitect.model.land;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;

import io.github.NationArchitect.model.Effect.*;
import io.github.NationArchitect.model.component.*;
import io.github.NationArchitect.model.economy.RegionEconomy;
import io.github.NationArchitect.model.metric.*;
import io.github.NationArchitect.model.population.Population;
import io.github.NationArchitect.model.product.ProductType;

public class Region extends Land {

    private TerrainType terrainType;
    private EnumMap<ResourceType, Double> resources;
    private EnumMap<ComponentType, Component> components;
    private ArrayList<Policy> activePolicies;
    private ArrayList<ActiveEffect> activeEffects;
    private double landValue;
    private double totalLand;
    private double baseCrimeRate;

    public Region() {
        super();
        this.activePolicies = new ArrayList<>();
        this.activeEffects = new ArrayList<>();
    }

    public Region(String name, RegionEconomy economy, Population population) {
        super(name, economy, population);
        this.activePolicies = new ArrayList<>();
        this.activeEffects = new ArrayList<>();
        this.resources = new EnumMap<>(ResourceType.class);
        this.components = new EnumMap<>(ComponentType.class);

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

    public Region setBaseCrimeRate(double baseCrimeRate) {
        this.baseCrimeRate = baseCrimeRate;
        return this;
    }

    public Region setResources(EnumMap<ResourceType, Double> undergroundResources) {
        this.resources = undergroundResources;
        return this;
    }

    public TerrainType getTerrainType() {
        return terrainType;
    }

    public EnumMap<ResourceType, Double> getResources() {
        return resources;
    }

    public EnumMap<ComponentType, Component> getComponents() {
        return components;
    }

    public ArrayList<Policy> getActivePolicies() {
        return activePolicies;
    }

    public ArrayList<ActiveEffect> getActiveEffects() {
        return activeEffects;
    }

    public double getComponentEffect(ComponentType affected, ComponentType affecter) {
        if (components.get(affecter).getRelatedComponents().containsKey(affected)) {
            return components.get(affecter).getRelatedComponents().get(affected)
                    * components.get(affecter).getPerformance();
        }
        return 0;
    }

    public int getManufacturerComponentProduction(ProductType productType) {
        switch (productType) {
            case FOOD:
                return ((ManufacturerComponent) components.get(ComponentType.AGRICULTURE)).getProductionAmount();
            case TECHNOLOGY:
                return ((ManufacturerComponent) components.get(ComponentType.OFFICE)).getProductionAmount();
            case INDUSTRIAL_GOOD:
                return ((ManufacturerComponent) components.get(ComponentType.FACTORY)).getProductionAmount();
            case TOURISM_SERVICE:
                return ((ManufacturerComponent) components.get(ComponentType.TOURISM)).getProductionAmount();
            case WATER:
                return ((ManufacturerComponent) components.get(ComponentType.WATER_MANAGEMENT)).getProductionAmount();
            case ENERGY:
                return ((ManufacturerComponent) components.get(ComponentType.ELECTRICITY)).getProductionAmount();
            default:
                return 0;
        }
    }

    public int getProductDemand(ProductType productType) {
        int demand = 0;
        for (ComponentType componentType : components.keySet()) {
            demand += components.get(componentType).getProductDemand(productType);
        }
        return demand;
    }

    public double getComponentBudget(ComponentType type) {
        return components.get(type).getFinalMonthlyBudget();
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

        for (Component component : this.components.values()) {
            for (Building building : component.getBuildings()) {
                totalWorkers += building.getWorkerAmount();
            }
        }

        return totalWorkers;
    }

    public int getWorkingAgePopulation() {
        return this.getPopulation().getWorkingAgePopulation();
    }

    public double getBaseCrimeRate() {
        return this.baseCrimeRate;
    }

    public int getEducationBuildingCapacity(EducationBuilding type) {
        int totalCapacity = 0;
        Education education = (Education) this.components.get(ComponentType.EDUCATION);
        for (Building building : education.getBuildings()) {
            if (building.getType() == type) {
                totalCapacity += type.getCapacity();
            }
        }
        return totalCapacity;
    }

    public int getTotalHealthServiceCapacity() {
        int totalCapacity = 0;
        HealthServices healthServices = (HealthServices) this.components.get(ComponentType.HEALTH_SERVICES);
        for (Building building : healthServices.getBuildings()) {
            totalCapacity += building.getType().getCapacity();
        }

        return totalCapacity;
    }

    @Override
    public void implementPolicy(Policy policy) {
        this.activePolicies.add(policy);
    }

    @Override
    public void cancelPolicy(Policy policy) {
        this.activePolicies.remove(policy);
    }

    public void addTemporaryEffect(Effect effect, int duration) {
        if (duration > 0) {
            this.activeEffects.add(new ActiveEffect(effect, duration));
        }
    }

    public double getTotalActiveEffectModifierForMetric(MetricType type) {
        double totalModifier = 0.0;
        for (ActiveEffect effect : activeEffects) {
            totalModifier += effect.getEffect().getMetricModifier(type);
        }
        return totalModifier;
    }

    public double getTotalPolicyModifierForMetric(MetricType type) {
        double totalModifier = 0.0;
        for (Policy policy : activePolicies) {
            totalModifier += policy.getMetricModifier(type);
        }
        return totalModifier;
    }

    public double getTotalActiveEffectModifierForComponent(ComponentType type) {
        double totalModifier = 0.0;
        for (ActiveEffect effect : activeEffects) {
            totalModifier += effect.getEffect().getComponentModifier(type);
        }
        return totalModifier;
    }

    public double getTotalPolicyModifierForComponent(ComponentType type) {
        double totalModifier = 0.0;
        for (Policy policy : activePolicies) {
            totalModifier += policy.getComponentModifier(type);
        }
        return totalModifier;
    }

    public double getTotalOccupiedLand(){
        double totalLand = 0;
        for(Component component : components.values()){
            for(Building building : component.getBuildings()){
                totalLand += building.getOccupiedLand();
            }
        }
        return totalLand;
    }

    public void calculateLandValue(){
        double happiness = this.getMetricValue(MetricType.HAPPINESS);
        double landRatio = totalLand <= 0 ? 0 : getTotalOccupiedLand() / totalLand;
        double crimeRate = this.getMetricValue(MetricType.CRIME_RATE);
        double stability = this.getMetricValue(MetricType.STABILITY);

        int factoryNumber = this.components.get(ComponentType.FACTORY).getBuildings().size();

        double value = factoryNumber * happiness * landRatio * crimeRate * stability;
        this.landValue = Double.isFinite(value) ? value : 0.0;
    }

    public double getLandValue(){return landValue;}

    @Override
    public void update() {

        for (Metric metric : this.metrics.values()) {
            metric.calculateForRegion(this);
        }
        double birthRate = this.getMetricValue(MetricType.HAPPINESS);
        this.population.updateLifeCycle(birthRate, this.getMetricValue(MetricType.HEALTH_RATE));
        this.getEconomy().calculateBalance(this);


    }
}
