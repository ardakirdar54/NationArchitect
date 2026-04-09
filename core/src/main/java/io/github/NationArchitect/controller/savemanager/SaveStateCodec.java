package io.github.NationArchitect.controller.savemanager;

import io.github.NationArchitect.model.Effect.ActiveEffect;
import io.github.NationArchitect.model.Effect.Effect;
import io.github.NationArchitect.model.Effect.Policy;
import io.github.NationArchitect.model.component.*;
import io.github.NationArchitect.model.economy.*;
import io.github.NationArchitect.model.land.Country;
import io.github.NationArchitect.model.land.Region;
import io.github.NationArchitect.model.land.ResourceType;
import io.github.NationArchitect.model.land.TerrainType;
import io.github.NationArchitect.model.metric.MetricType;
import io.github.NationArchitect.model.population.Age;
import io.github.NationArchitect.model.population.Gender;
import io.github.NationArchitect.model.population.Population;
import io.github.NationArchitect.model.product.Product;
import io.github.NationArchitect.model.product.ProductType;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 * Maps runtime model objects to save DTOs and reconstructs runtime state from them.
 */
public final class SaveStateCodec {

    private SaveStateCodec() {
    }

    public static SaveData.CountryState serializeCountryState(Country country) {
        if (country == null) {
            return null;
        }
        SaveData.CountryState state = new SaveData.CountryState();
        state.name = country.getName();
        state.population = serializePopulation(country.getMutablePopulation());
        state.metrics = serializeMetrics(country);
        state.economy = serializeEconomy(country.getEconomy());
        Region[] regions = country.getRegions();
        state.regions = new SaveData.RegionState[regions.length];
        for (int index = 0; index < regions.length; index++) {
            state.regions[index] = regions[index] == null ? null : serializeRegionState(regions[index]);
        }
        return state;
    }

    public static Country deserializeCountryState(SaveData.CountryState state) {
        if (state == null) {
            return null;
        }
        CountryEconomy economy = new CountryEconomy(deserializeTax(state.economy == null ? null : state.economy.tax));
        Population population = deserializePopulation(state.population);
        Country country = new Country(state.name, economy, population);
        applyMetrics(country, state.metrics);
        restoreEconomy(economy, state.economy);

        SaveData.RegionState[] regionStates = state.regions == null ? new SaveData.RegionState[0] : state.regions;
        Region[] regions = new Region[Math.max(regionStates.length, 10)];
        for (int index = 0; index < regionStates.length; index++) {
            regions[index] = regionStates[index] == null ? null : deserializeRegionState(regionStates[index]);
        }
        country.setRegions(regions);
        return country;
    }

    public static SaveData.RegionState serializeRegionState(Region region) {
        SaveData.RegionState state = new SaveData.RegionState();
        state.name = region.getName();
        state.terrainType = region.getTerrainType() == null ? null : region.getTerrainType().name();
        state.landValue = region.getLandValue();
        state.baseCrimeRate = region.getBaseCrimeRate();
        state.population = serializePopulation(region.getMutablePopulation());
        state.metrics = serializeMetrics(region);
        state.economy = serializeEconomy(region.getEconomy());
        state.undergroundResources = serializeNamedDoubles(region.getResources());
        state.components = serializeComponents(region);
        state.activeEffects = serializeRegionEffects(region.getActiveEffects());
        state.activePolicies = serializePolicies(region.getActivePolicies());
        return state;
    }

    public static Region deserializeRegionState(SaveData.RegionState state) {
        RegionEconomy economy = new RegionEconomy(deserializeTax(state.economy == null ? null : state.economy.tax));
        Population population = deserializePopulation(state.population);
        Region region = new Region(state.name, economy, population);
        region.setTerrainType(state.terrainType == null ? null : TerrainType.valueOf(state.terrainType));
        region.setLandValue(state.landValue);
        region.setBaseCrimeRate(state.baseCrimeRate);
        region.setResources(deserializeResourceMap(state.undergroundResources));
        applyMetrics(region, state.metrics);

        EnumMap<ComponentType, Component> components = new EnumMap<ComponentType, Component>(ComponentType.class);
        if (state.components != null) {
            for (SaveData.ComponentState componentState : state.components) {
                ComponentType componentType = ComponentType.valueOf(componentState.componentType);
                components.put(componentType, createComponent(componentType, region));
            }
        }
        region.setComponents(components);

        if (state.components != null) {
            for (SaveData.ComponentState componentState : state.components) {
                restoreComponent(components.get(ComponentType.valueOf(componentState.componentType)), componentState);
            }
        }
        restoreRegionEffects(region, state.activeEffects);
        restorePolicies(region, state.activePolicies);
        restoreEconomy(economy, state.economy);
        return region;
    }

    private static SaveData.ComponentState[] serializeComponents(Region region) {
        List<SaveData.ComponentState> states = new ArrayList<SaveData.ComponentState>();
        for (Component component : region.getComponents().values()) {
            states.add(serializeComponent(component));
        }
        return states.toArray(new SaveData.ComponentState[0]);
    }

    private static SaveData.ComponentState serializeComponent(Component component) {
        SaveData.ComponentState state = new SaveData.ComponentState();
        state.componentType = component.getComponentType().name();
        state.description = component.getDescription();
        state.baseMonthlyBudget = component.getBaseMonthlyBudget();
        state.finalMonthlyBudget = component.getFinalMonthlyBudget();
        state.budgetMultiplier = component.getBudgetMultiplier();
        state.budgetPercentage = component.getBudgetPercentage();
        state.performance = component.getPerformance();
        state.totalOccupiedLand = component.getTotalOccupiedLand();
        state.relatedMetrics = serializeNamedDoubles(component.getRelatedMetrics());
        state.relatedComponents = serializeNamedDoubles(component.getRelatedComponents());
        state.demand = serializeDemand(component);
        state.activeEffects = serializeComponentEffects(component.getActiveEffects());

        List<SaveData.BuildingState> buildingStates = new ArrayList<SaveData.BuildingState>();
        for (Building building : component.getBuildings()) {
            buildingStates.add(serializeBuilding(building));
        }
        state.buildings = buildingStates.toArray(new SaveData.BuildingState[0]);
        return state;
    }

    private static SaveData.BuildingState serializeBuilding(Building building) {
        SaveData.BuildingState state = new SaveData.BuildingState();
        state.name = building.getName();
        state.typeName = building.getType().getName();
        state.workerAmount = building.getWorkerAmount();
        state.maxWorkerAmount = building.getMaxWorkerAmount();
        state.efficiency = building.getEfficiency();
        state.performanceMultiplier = building.getPerformanceMultiplier();
        state.constructionCost = building.getConstructionCost();
        state.maintenanceCost = building.getMaintenanceCost();
        state.occupiedLand = building.getOccupiedLand();
        state.production = building.getType().getProduction();
        state.relatedMetrics = serializeNamedDoubles(building.getRelatedMetrics());
        state.relatedComponents = serializeNamedDoubles(building.getRelatedComponents());
        state.demand = serializeDemand(building);
        return state;
    }

    private static SaveData.EffectState[] serializeComponentEffects(List<Effect> effects) {
        List<SaveData.EffectState> states = new ArrayList<SaveData.EffectState>();
        for (Effect effect : effects) {
            states.add(serializeEffect(effect, false, true, 0));
        }
        return states.toArray(new SaveData.EffectState[0]);
    }

    private static SaveData.EffectState[] serializeRegionEffects(List<ActiveEffect> effects) {
        List<SaveData.EffectState> states = new ArrayList<SaveData.EffectState>();
        for (ActiveEffect activeEffect : effects) {
            states.add(serializeEffect(activeEffect.getEffect(), false, true, activeEffect.getRemainingMonths()));
        }
        return states.toArray(new SaveData.EffectState[0]);
    }

    private static SaveData.EffectState[] serializePolicies(List<Policy> policies) {
        List<SaveData.EffectState> states = new ArrayList<SaveData.EffectState>();
        for (Policy policy : policies) {
            states.add(serializeEffect(policy, true, policy.getIsActive(), 0));
        }
        return states.toArray(new SaveData.EffectState[0]);
    }

    private static SaveData.EffectState serializeEffect(Effect effect, boolean policy, boolean active, int remainingMonths) {
        SaveData.EffectState state = new SaveData.EffectState();
        state.name = effect.getName();
        state.description = effect.getDescription();
        state.policy = policy;
        state.active = active;
        state.remainingMonths = remainingMonths;
        state.relatedComponents = serializeNamedDoubles(effect.getRelatedComponents());
        state.relatedMetrics = serializeNamedDoubles(effect.getRelatedMetrics());
        return state;
    }

    private static SaveData.PopulationState serializePopulation(Population population) {
        SaveData.PopulationState state = new SaveData.PopulationState();
        List<SaveData.NamedIntState> ages = new ArrayList<SaveData.NamedIntState>();
        for (Age age : Age.values()) {
            ages.add(new SaveData.NamedIntState(age.name(), population.getAgeDistribution().getOrDefault(age, 0)));
        }
        state.ageDistribution = ages.toArray(new SaveData.NamedIntState[0]);

        List<SaveData.NamedIntState> genders = new ArrayList<SaveData.NamedIntState>();
        for (Gender gender : Gender.values()) {
            genders.add(new SaveData.NamedIntState(gender.name(), population.getGenderDistribution().getOrDefault(gender, 0)));
        }
        state.genderDistribution = genders.toArray(new SaveData.NamedIntState[0]);
        state.births = population.getBirths();
        state.deaths = population.getDeaths();
        return state;
    }

    private static Population deserializePopulation(SaveData.PopulationState state) {
        Population population = new Population();
        EnumMap<Age, Integer> ages = new EnumMap<Age, Integer>(Age.class);
        for (Age age : Age.values()) {
            ages.put(age, 0);
        }
        if (state != null && state.ageDistribution != null) {
            for (SaveData.NamedIntState value : state.ageDistribution) {
                ages.put(Age.valueOf(value.key), value.value);
            }
        }
        population.setAgeDistribution(ages);

        EnumMap<Gender, Integer> genders = new EnumMap<Gender, Integer>(Gender.class);
        for (Gender gender : Gender.values()) {
            genders.put(gender, 0);
        }
        if (state != null && state.genderDistribution != null) {
            for (SaveData.NamedIntState value : state.genderDistribution) {
                genders.put(Gender.valueOf(value.key), value.value);
            }
        }
        population.setGenderDistribution(genders);
        if (state != null) {
            population.setBirths(state.births).setDeaths(state.deaths);
        }
        return population;
    }

    private static SaveData.MetricState[] serializeMetrics(io.github.NationArchitect.model.land.Land land) {
        List<SaveData.MetricState> states = new ArrayList<SaveData.MetricState>();
        for (MetricType type : MetricType.values()) {
            SaveData.MetricState state = new SaveData.MetricState();
            state.type = type.name();
            state.value = land.getMetricValue(type);
            state.lastMonthValue = land.getLastMonthMetricValue(type);
            states.add(state);
        }
        return states.toArray(new SaveData.MetricState[0]);
    }

    private static void applyMetrics(io.github.NationArchitect.model.land.Land land, SaveData.MetricState[] states) {
        if (states == null) {
            return;
        }
        for (SaveData.MetricState state : states) {
            MetricType type = MetricType.valueOf(state.type);
            land.setMetricValue(type, state.value);
            land.setLastMonthMetricValue(type, state.lastMonthValue);
        }
    }

    private static SaveData.EconomyState serializeEconomy(Economy economy) {
        SaveData.EconomyState state = new SaveData.EconomyState();
        state.kind = economy.getClass().getSimpleName();
        state.income = economy.getIncome();
        state.expanse = economy.getExpanse();
        state.balance = economy.getBalance();
        state.tax = serializeTax(economy.getTax());
        state.taxRevenues = serializeNamedDoubles(economy.getTaxRevenues());
        state.componentBudgets = serializeNamedDoubles(economy.getComponentBudgets());
        if (economy instanceof RegionEconomy) {
            RegionEconomy regionEconomy = (RegionEconomy) economy;
            state.production = serializeProducts(regionEconomy.getProductionProducts());
            state.demand = serializeProducts(regionEconomy.getDemandProducts());
        }
        if (economy instanceof CountryEconomy) {
            CountryEconomy countryEconomy = (CountryEconomy) economy;
            state.treasury = countryEconomy.getTreasury();
            state.importValue = countryEconomy.getImport();
            state.exportValue = countryEconomy.getExport();
        }
        return state;
    }

    private static void restoreEconomy(Economy economy, SaveData.EconomyState state) {
        if (state == null) {
            return;
        }
        economy.restoreSummary(state.income, state.expanse, state.balance);
        economy.restoreTaxRevenues(deserializeDoubleMapTax(state.taxRevenues));
        economy.restoreComponentBudgets(deserializeDoubleMapComponents(state.componentBudgets));
        if (economy instanceof RegionEconomy) {
            RegionEconomy regionEconomy = (RegionEconomy) economy;
            regionEconomy.restoreProduction(deserializeProducts(state.production));
            regionEconomy.restoreDemand(deserializeProducts(state.demand));
        }
        if (economy instanceof CountryEconomy) {
            ((CountryEconomy) economy).restoreTrade(state.treasury, state.importValue, state.exportValue);
        }
    }

    private static SaveData.TaxState serializeTax(Tax tax) {
        SaveData.TaxState state = new SaveData.TaxState();
        state.incomeTaxRate = tax.getIncomeTaxRate();
        state.propertyTaxRate = tax.getPropertyTaxRate();
        state.vatRate = tax.getVatRate();
        state.exciseTaxRate = tax.getExciseTaxRate(ProductType.TOURISM_SERVICE);
        state.corporateTaxRate = tax.getCorporateTaxRate();
        state.productionTaxRate = tax.getProductionTaxRate();
        return state;
    }

    private static Tax deserializeTax(SaveData.TaxState state) {
        Tax tax = new Tax();
        if (state == null) {
            return tax;
        }
        tax.setIncomeTaxRate(state.incomeTaxRate);
        tax.setPropertyTaxRate(state.propertyTaxRate);
        tax.setVatRate(state.vatRate);
        tax.setExciseTaxRate(state.exciseTaxRate);
        tax.setCorporateTaxRate(state.corporateTaxRate);
        tax.setProductionTaxRate(state.productionTaxRate);
        return tax;
    }

    private static SaveData.ProductState[] serializeProducts(EnumMap<ProductType, Product> products) {
        List<SaveData.ProductState> states = new ArrayList<SaveData.ProductState>();
        if (products == null) {
            return new SaveData.ProductState[0];
        }
        for (Product product : products.values()) {
            SaveData.ProductState state = new SaveData.ProductState();
            state.type = product.getType().name();
            state.amount = product.getAmount();
            state.salePrice = product.getSalePrice();
            state.purchasePrice = product.getPurchasePrice();
            states.add(state);
        }
        return states.toArray(new SaveData.ProductState[0]);
    }

    private static EnumMap<ProductType, Product> deserializeProducts(SaveData.ProductState[] states) {
        EnumMap<ProductType, Product> products = new EnumMap<ProductType, Product>(ProductType.class);
        if (states == null) {
            return products;
        }
        for (SaveData.ProductState state : states) {
            Product product = new Product(ProductType.valueOf(state.type));
            product.produce(state.amount);
            products.put(product.getType(), product);
        }
        return products;
    }

    private static SaveData.NamedDoubleState[] serializeDemand(Component component) {
        List<SaveData.NamedDoubleState> values = new ArrayList<SaveData.NamedDoubleState>();
        for (ProductType type : ProductType.values()) {
            values.add(new SaveData.NamedDoubleState(type.name(), component.getProductDemand(type)));
        }
        return values.toArray(new SaveData.NamedDoubleState[0]);
    }

    private static SaveData.NamedDoubleState[] serializeDemand(Building building) {
        List<SaveData.NamedDoubleState> values = new ArrayList<SaveData.NamedDoubleState>();
        EnumMap<ProductType, Double> demand = building.getDemand();
        if (demand == null) {
            return new SaveData.NamedDoubleState[0];
        }
        for (ProductType type : ProductType.values()) {
            values.add(new SaveData.NamedDoubleState(type.name(), demand.getOrDefault(type, 0.0)));
        }
        return values.toArray(new SaveData.NamedDoubleState[0]);
    }

    private static SaveData.NamedDoubleState[] serializeNamedDoubles(EnumMap<?, Double> map) {
        List<SaveData.NamedDoubleState> values = new ArrayList<SaveData.NamedDoubleState>();
        if (map == null) {
            return new SaveData.NamedDoubleState[0];
        }
        for (Object key : map.keySet()) {
            values.add(new SaveData.NamedDoubleState(String.valueOf(key), map.get(key)));
        }
        return values.toArray(new SaveData.NamedDoubleState[0]);
    }

    private static EnumMap<ResourceType, Double> deserializeResourceMap(SaveData.NamedDoubleState[] states) {
        EnumMap<ResourceType, Double> map = new EnumMap<ResourceType, Double>(ResourceType.class);
        if (states == null) {
            return map;
        }
        for (SaveData.NamedDoubleState state : states) {
            map.put(ResourceType.valueOf(state.key), state.value);
        }
        return map;
    }

    private static EnumMap<TaxType, Double> deserializeDoubleMapTax(SaveData.NamedDoubleState[] states) {
        EnumMap<TaxType, Double> map = new EnumMap<TaxType, Double>(TaxType.class);
        if (states == null) {
            return map;
        }
        for (SaveData.NamedDoubleState state : states) {
            map.put(TaxType.valueOf(state.key), state.value);
        }
        return map;
    }

    private static EnumMap<ComponentType, Double> deserializeDoubleMapComponents(SaveData.NamedDoubleState[] states) {
        EnumMap<ComponentType, Double> map = new EnumMap<ComponentType, Double>(ComponentType.class);
        if (states == null) {
            return map;
        }
        for (SaveData.NamedDoubleState state : states) {
            map.put(ComponentType.valueOf(state.key), state.value);
        }
        return map;
    }

    private static EnumMap<MetricType, Double> deserializeMetricModifiers(SaveData.NamedDoubleState[] states) {
        EnumMap<MetricType, Double> map = new EnumMap<MetricType, Double>(MetricType.class);
        if (states == null) {
            return map;
        }
        for (SaveData.NamedDoubleState state : states) {
            map.put(MetricType.valueOf(state.key), state.value);
        }
        return map;
    }

    private static void restoreRegionEffects(Region region, SaveData.EffectState[] states) {
        if (states == null) {
            return;
        }
        for (SaveData.EffectState state : states) {
            region.addTemporaryEffect(deserializeEffect(state), state.remainingMonths);
        }
    }

    private static void restorePolicies(Region region, SaveData.EffectState[] states) {
        if (states == null) {
            return;
        }
        for (SaveData.EffectState state : states) {
            Policy policy = deserializePolicy(state);
            policy.setActive(state.active);
            region.implementPolicy(policy);
        }
    }

    private static void restoreComponent(Component component, SaveData.ComponentState state) {
        if (component == null || state == null) {
            return;
        }
        component.setBudgetPercentage(state.budgetPercentage);
        if (state.buildings != null) {
            for (SaveData.BuildingState buildingState : state.buildings) {
                Building building = createBuilding(ComponentType.valueOf(state.componentType), buildingState);
                component.restoreBuilding(building);
            }
        }
        if (state.activeEffects != null) {
            for (SaveData.EffectState effectState : state.activeEffects) {
                component.addEffect(deserializeEffect(effectState));
            }
        }
        component.update();
    }

    private static Effect deserializeEffect(SaveData.EffectState state) {
        return new Effect(
            state.name,
            state.description,
            deserializeDoubleMapComponents(state.relatedComponents),
            deserializeMetricModifiers(state.relatedMetrics)
        );
    }

    private static Policy deserializePolicy(SaveData.EffectState state) {
        return new Policy(
            state.name,
            state.description,
            deserializeDoubleMapComponents(state.relatedComponents),
            deserializeMetricModifiers(state.relatedMetrics)
        );
    }

    private static Component createComponent(ComponentType componentType, Region region) {
        switch (componentType) {
            case FACTORY: return new Factory(region);
            case OFFICE: return new Office(region);
            case TOURISM: return new Tourism(region);
            case AGRICULTURE: return new Agriculture(region);
            case HEALTH_SERVICES: return new HealthServices(region);
            case EDUCATION: return new Education(region);
            case SECURITY: return new Security(region);
            case ROAD_TRANSPORT: return new RoadTransport(region);
            case RAIL_TRANSPORT: return new RailTransport(region);
            case MARINE_TRANSPORT: return new MarineTransport(region);
            case AIR_TRANSPORT: return new AirTransport(region);
            case ROAD_NETWORK: return new RoadNetwork(region);
            case ELECTRICITY: return new Electricity(region);
            case WATER_MANAGEMENT: return new WaterManagement(region);
            case INTERNET: return new Internet(region);
            default: throw new IllegalArgumentException("Unsupported component type: " + componentType);
        }
    }

    private static Building createBuilding(ComponentType componentType, SaveData.BuildingState state) {
        BuildingType type = resolveBuildingType(componentType, state.typeName);
        Building building = isManufacturer(componentType)
            ? new ManufacturerBuilding(state.name, type)
            : new Building(state.name, type);
        building.setWorkerAmount(state.workerAmount);
        return building;
    }

    private static boolean isManufacturer(ComponentType componentType) {
        return componentType == ComponentType.AGRICULTURE
            || componentType == ComponentType.ELECTRICITY
            || componentType == ComponentType.FACTORY
            || componentType == ComponentType.OFFICE
            || componentType == ComponentType.WATER_MANAGEMENT;
    }

    private static BuildingType resolveBuildingType(ComponentType componentType, String typeName) {
        switch (componentType) {
            case FACTORY: return FactoryBuilding.valueOf(typeName);
            case OFFICE: return OfficeBuilding.valueOf(typeName);
            case TOURISM: return TourismBuilding.valueOf(typeName);
            case AGRICULTURE: return AgricultureBuilding.valueOf(typeName);
            case HEALTH_SERVICES: return HealthServicesBuilding.valueOf(typeName);
            case EDUCATION: return EducationBuilding.valueOf(typeName);
            case SECURITY: return SecurityBuilding.valueOf(typeName);
            case ROAD_TRANSPORT: return RoadTransportBuilding.valueOf(typeName);
            case RAIL_TRANSPORT: return RailTransportBuilding.valueOf(typeName);
            case MARINE_TRANSPORT: return MarineTransportBuilding.valueOf(typeName);
            case AIR_TRANSPORT: return AirTransportBuilding.valueOf(typeName);
            case ROAD_NETWORK: return RoadNetworkBuilding.valueOf(typeName);
            case ELECTRICITY: return ElectricityBuilding.valueOf(typeName);
            case WATER_MANAGEMENT: return WaterManagementBuilding.valueOf(typeName);
            case INTERNET: return InternetBuilding.valueOf(typeName);
            default: throw new IllegalArgumentException("Unsupported component type: " + componentType);
        }
    }
}
