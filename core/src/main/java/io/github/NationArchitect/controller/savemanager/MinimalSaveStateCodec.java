package io.github.NationArchitect.controller.savemanager;

import io.github.NationArchitect.model.component.Agriculture;
import io.github.NationArchitect.model.component.AgricultureBuilding;
import io.github.NationArchitect.model.component.AirTransport;
import io.github.NationArchitect.model.component.AirTransportBuilding;
import io.github.NationArchitect.model.component.Building;
import io.github.NationArchitect.model.component.Component;
import io.github.NationArchitect.model.component.ComponentType;
import io.github.NationArchitect.model.component.Education;
import io.github.NationArchitect.model.component.EducationBuilding;
import io.github.NationArchitect.model.component.Electricity;
import io.github.NationArchitect.model.component.ElectricityBuilding;
import io.github.NationArchitect.model.component.Factory;
import io.github.NationArchitect.model.component.FactoryBuilding;
import io.github.NationArchitect.model.component.HealthServices;
import io.github.NationArchitect.model.component.HealthServicesBuilding;
import io.github.NationArchitect.model.component.Internet;
import io.github.NationArchitect.model.component.InternetBuilding;
import io.github.NationArchitect.model.component.MarineTransport;
import io.github.NationArchitect.model.component.MarineTransportBuilding;
import io.github.NationArchitect.model.component.Office;
import io.github.NationArchitect.model.component.OfficeBuilding;
import io.github.NationArchitect.model.component.RailTransport;
import io.github.NationArchitect.model.component.RailTransportBuilding;
import io.github.NationArchitect.model.component.RoadNetwork;
import io.github.NationArchitect.model.component.RoadNetworkBuilding;
import io.github.NationArchitect.model.component.RoadTransport;
import io.github.NationArchitect.model.component.RoadTransportBuilding;
import io.github.NationArchitect.model.component.Security;
import io.github.NationArchitect.model.component.SecurityBuilding;
import io.github.NationArchitect.model.component.Tourism;
import io.github.NationArchitect.model.component.TourismBuilding;
import io.github.NationArchitect.model.component.WaterManagement;
import io.github.NationArchitect.model.component.WaterManagementBuilding;
import io.github.NationArchitect.model.economy.CountryEconomy;
import io.github.NationArchitect.model.economy.Economy;
import io.github.NationArchitect.model.economy.RegionEconomy;
import io.github.NationArchitect.model.economy.Tax;
import io.github.NationArchitect.model.economy.TaxType;
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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.EnumMap;

final class MinimalSaveStateCodec {

    private MinimalSaveStateCodec() {
    }

    static SaveData.CountryState serializeCountryState(Country country) {
        if (country == null) {
            return null;
        }
        SaveData.CountryState state = new SaveData.CountryState();
        state.name = country.getName();
        state.population = serializePopulation(readPopulation(country));
        state.metrics = serializeMetrics(country);
        state.economy = serializeEconomy(country.getEconomy(), country);
        Region[] regions = country.getRegions();
        state.regions = new SaveData.RegionState[regions == null ? 0 : regions.length];
        if (regions != null) {
            for (int index = 0; index < regions.length; index++) {
                state.regions[index] = serializeRegionState(regions[index]);
            }
        }
        return state;
    }

    static void hydrateCountryState(SaveData.CountryState state) {
        if (state == null || state.regions == null) {
            return;
        }
        for (SaveData.RegionState regionState : state.regions) {
            hydrateRegionState(regionState);
        }
    }

    static Country deserializeCountryState(SaveData.CountryState state) {
        if (state == null) {
            return null;
        }
        try {
            Constructor<CountryEconomy> economyConstructor = CountryEconomy.class.getDeclaredConstructor(Tax.class);
            economyConstructor.setAccessible(true);
            CountryEconomy economy = economyConstructor.newInstance(deserializeTax(state.economy == null ? null : state.economy.tax));

            Constructor<Country> countryConstructor = Country.class.getDeclaredConstructor(
                String.class,
                io.github.NationArchitect.model.economy.Economy.class,
                Population.class
            );
            countryConstructor.setAccessible(true);
            Country country = countryConstructor.newInstance(
                state.name == null ? "Saved Country" : state.name,
                economy,
                deserializePopulation(state.population)
            );

            Field regionsField = Country.class.getDeclaredField("regions");
            regionsField.setAccessible(true);
            SaveData.RegionState[] regionStates = state.regions == null ? new SaveData.RegionState[0] : state.regions;
            Region[] regions = new Region[Math.max(regionStates.length, 10)];
            for (int index = 0; index < regionStates.length; index++) {
                regions[index] = deserializeRegionState(regionStates[index]);
            }
            regionsField.set(country, regions);
            restoreEconomy(country.getEconomy(), country, state.economy);
            return country;
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Unable to restore saved country state", exception);
        }
    }

    static SaveData.RegionState serializeRegionState(Region region) {
        if (region == null) {
            return null;
        }
        SaveData.RegionState state = new SaveData.RegionState();
        state.name = region.getName();
        state.terrainType = region.getTerrainType() == null ? "" : region.getTerrainType().name();
        state.undergroundResources = serializeNamedDoubleStates(region.getResources());
        state.components = serializeComponents(region);
        state.activeEffects = serializeActiveEffects(region);
        state.activePolicies = serializePolicies(region);
        state.landValue = region.getLandValue();
        state.population = serializePopulation(readPopulation(region));
        state.metrics = serializeMetrics(region);
        state.economy = serializeEconomy(region.getEconomy(), region);
        state.baseCrimeRate = region.getBaseCrimeRate();
        return state;
    }

    static Region deserializeRegionState(SaveData.RegionState state) {
        if (state == null) {
            return null;
        }
        Region region = new Region(
            state.name == null ? "Saved Region" : state.name,
            new RegionEconomy(deserializeTax(state.economy == null ? null : state.economy.tax)),
            deserializePopulation(state.population)
        );
        region.setComponents(createDefaultComponents(region));
        region.setTerrainType(deserializeTerrain(state.terrainType));
        region.setResources(deserializeResourceStates(state.undergroundResources));
        region.setLandValue(state.landValue);
        region.setBaseCrimeRate(state.baseCrimeRate);
        applyMetrics(region, state.metrics);
        restoreComponentState(region, state.components);
        restoreEconomy(region.getEconomy(), region, state.economy);
        restoreEffects(region, state.activeEffects, state.activePolicies);
        return region;
    }

    private static SaveData.PopulationState serializePopulation(Population population) {
        SaveData.PopulationState state = new SaveData.PopulationState();
        state.ageDistribution = new SaveData.NamedIntState[Age.values().length];
        int ageIndex = 0;
        for (Age age : Age.values()) {
            state.ageDistribution[ageIndex++] = new SaveData.NamedIntState(age.name(), population.getAgeDistribution().getOrDefault(age, 0));
        }
        state.genderDistribution = new SaveData.NamedIntState[Gender.values().length];
        int genderIndex = 0;
        for (Gender gender : Gender.values()) {
            state.genderDistribution[genderIndex++] = new SaveData.NamedIntState(gender.name(), population.getGenderDistribution().getOrDefault(gender, 0));
        }
        state.births = population.getBirths();
        state.deaths = population.getDeaths();
        return state;
    }

    private static Population deserializePopulation(SaveData.PopulationState state) {
        Population population = new Population();
        EnumMap<Age, Integer> ages = new EnumMap<>(Age.class);
        for (Age age : Age.values()) {
            ages.put(age, 0);
        }
        if (state != null && state.ageDistribution != null) {
            for (SaveData.NamedIntState value : state.ageDistribution) {
                if (value != null && value.key != null) {
                    ages.put(Age.valueOf(value.key), value.value);
                }
            }
        }
        population.setAgeDistribution(ages);

        EnumMap<Gender, Integer> genders = new EnumMap<>(Gender.class);
        for (Gender gender : Gender.values()) {
            genders.put(gender, 0);
        }
        if (state != null && state.genderDistribution != null) {
            for (SaveData.NamedIntState value : state.genderDistribution) {
                if (value != null && value.key != null) {
                    genders.put(Gender.valueOf(value.key), value.value);
                }
            }
        }
        population.setGenderDistribution(genders);
        return population;
    }

    private static SaveData.MetricState[] serializeMetrics(io.github.NationArchitect.model.land.Land land) {
        SaveData.MetricState[] states = new SaveData.MetricState[MetricType.values().length];
        int index = 0;
        for (MetricType type : MetricType.values()) {
            SaveData.MetricState state = new SaveData.MetricState();
            state.type = type.name();
            state.value = land.getMetricValue(type);
            state.lastMonthValue = land.getLastMonthMetricValue(type);
            states[index++] = state;
        }
        return states;
    }

    private static void applyMetrics(io.github.NationArchitect.model.land.Land land, SaveData.MetricState[] states) {
        if (states == null) {
            return;
        }
        try {
            Field metricsField = io.github.NationArchitect.model.land.Land.class.getDeclaredField("metrics");
            metricsField.setAccessible(true);
            @SuppressWarnings("unchecked")
            EnumMap<MetricType, io.github.NationArchitect.model.metric.Metric> metrics =
                (EnumMap<MetricType, io.github.NationArchitect.model.metric.Metric>) metricsField.get(land);
            for (SaveData.MetricState state : states) {
                if (state == null || state.type == null) {
                    continue;
                }
                io.github.NationArchitect.model.metric.Metric metric = metrics.get(MetricType.valueOf(state.type));
                if (metric != null) {
                    metric.setValue(state.value);
                    metric.setLastMonthValue(state.lastMonthValue);
                }
            }
        } catch (ReflectiveOperationException ignored) {
        }
    }

    private static SaveData.EconomyState serializeEconomy(Economy economy, io.github.NationArchitect.model.land.Land land) {
        SaveData.EconomyState state = new SaveData.EconomyState();
        if (economy == null) {
            state.kind = "";
            state.tax = serializeTax(null);
            state.taxRevenues = new SaveData.NamedDoubleState[0];
            state.componentBudgets = new SaveData.NamedDoubleState[0];
            state.production = new SaveData.ProductState[0];
            state.demand = new SaveData.ProductState[0];
            return state;
        }

        economy.calculateBalance(land);
        state.kind = economy.getClass().getSimpleName();
        state.income = economy.getIncome();
        state.expanse = economy.getExpanse();
        state.balance = economy.getBalance();
        state.tax = serializeTax(economy.getTax());
        state.taxRevenues = serializeNamedDoubleStates(economy.getTaxRevenues());
        state.componentBudgets = serializeNamedDoubleStates(economy.getComponentBudgets());
        state.production = new SaveData.ProductState[0];
        state.demand = new SaveData.ProductState[0];

        if (economy instanceof CountryEconomy) {
            CountryEconomy countryEconomy = (CountryEconomy) economy;
            state.treasury = countryEconomy.getTreasury();
            state.importValue = countryEconomy.getImport();
            state.exportValue = countryEconomy.getExport();
        } else if (economy instanceof RegionEconomy) {
            RegionEconomy regionEconomy = (RegionEconomy) economy;
            state.production = serializeProducts(regionEconomy.getProductionProducts());
            state.demand = serializeProducts(regionEconomy.getDemandProducts());
            state.treasury = 0.0;
            state.importValue = regionEconomy.getImport();
            state.exportValue = regionEconomy.getExport();
        }
        return state;
    }

    private static SaveData.TaxState serializeTax(Tax tax) {
        SaveData.TaxState state = new SaveData.TaxState();
        if (tax == null) {
            return state;
        }
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

    private static Population readPopulation(io.github.NationArchitect.model.land.Land land) {
        try {
            Field populationField = io.github.NationArchitect.model.land.Land.class.getDeclaredField("population");
            populationField.setAccessible(true);
            Population population = (Population) populationField.get(land);
            return population == null ? new Population() : population;
        } catch (ReflectiveOperationException exception) {
            return new Population();
        }
    }

    private static EnumMap<ComponentType, Component> createDefaultComponents(Region region) {
        EnumMap<ComponentType, Component> components = new EnumMap<>(ComponentType.class);
        components.put(ComponentType.FACTORY, new Factory(region));
        components.put(ComponentType.OFFICE, new Office(region));
        components.put(ComponentType.TOURISM, new Tourism(region));
        components.put(ComponentType.AGRICULTURE, new Agriculture(region));
        components.put(ComponentType.HEALTH_SERVICES, new HealthServices(region));
        components.put(ComponentType.EDUCATION, new Education(region));
        components.put(ComponentType.SECURITY, new Security(region));
        components.put(ComponentType.ROAD_TRANSPORT, new RoadTransport(region));
        components.put(ComponentType.RAIL_TRANSPORT, new RailTransport(region));
        components.put(ComponentType.MARINE_TRANSPORT, new MarineTransport(region));
        components.put(ComponentType.AIR_TRANSPORT, new AirTransport(region));
        components.put(ComponentType.ROAD_NETWORK, new RoadNetwork(region));
        components.put(ComponentType.ELECTRICITY, new Electricity(region));
        components.put(ComponentType.WATER_MANAGEMENT, new WaterManagement(region));
        components.put(ComponentType.INTERNET, new Internet(region));
        return components;
    }

    private static SaveData.ComponentState[] serializeComponents(Region region) {
        if (region.getComponents() == null || region.getComponents().isEmpty()) {
            return new SaveData.ComponentState[0];
        }
        SaveData.ComponentState[] states = new SaveData.ComponentState[region.getComponents().size()];
        int index = 0;
        for (Component component : region.getComponents().values()) {
            SaveData.ComponentState state = new SaveData.ComponentState();
            state.componentType = component.getComponentType().name();
            state.description = component.getDescription() == null
                ? component.getComponentType().getDescription()
                : component.getDescription();
            state.baseMonthlyBudget = component.getBaseMonthlyBudget();
            state.finalMonthlyBudget = component.getFinalMonthlyBudget();
            state.budgetMultiplier = component.getBudgetMultiplier();
            state.budgetPercentage = component.getBudgetPercentage();
            state.performance = component.getPerformance();
            state.totalOccupiedLand = component.getTotalOccupiedLand();
            state.relatedMetrics = serializeNamedDoubleStates(component.getRelatedMetrics());
            state.relatedComponents = serializeNamedDoubleStates(component.getRelatedComponents());
            state.demand = serializeComponentDemand(component);
            state.buildings = serializeBuildings(component);
            state.activeEffects = serializeEffects(component.getActiveEffects(), false);
            states[index++] = state;
        }
        return states;
    }

    private static SaveData.BuildingState[] serializeBuildings(Component component) {
        ArrayList<Building> buildings = component.getBuildings();
        if (buildings == null || buildings.isEmpty()) {
            return new SaveData.BuildingState[0];
        }
        SaveData.BuildingState[] states = new SaveData.BuildingState[buildings.size()];
        for (int index = 0; index < buildings.size(); index++) {
            Building building = buildings.get(index);
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
            state.relatedMetrics = serializeNamedDoubleStates(building.getRelatedMetrics());
            state.relatedComponents = serializeNamedDoubleStates(building.getRelatedComponents());
            state.demand = serializeNamedDoubleStates(building.getDemand());
            states[index] = state;
        }
        return states;
    }

    private static SaveData.EffectState[] serializeActiveEffects(Region region) {
        if (region.getActiveEffects() == null || region.getActiveEffects().isEmpty()) {
            return new SaveData.EffectState[0];
        }
        SaveData.EffectState[] states = new SaveData.EffectState[region.getActiveEffects().size()];
        for (int index = 0; index < region.getActiveEffects().size(); index++) {
            io.github.NationArchitect.model.Effect.ActiveEffect activeEffect = region.getActiveEffects().get(index);
            SaveData.EffectState state = serializeEffect(activeEffect.getEffect(), false);
            state.active = !activeEffect.isExpired();
            state.remainingMonths = activeEffect.getRemainingMonths();
            states[index] = state;
        }
        return states;
    }

    private static SaveData.EffectState[] serializePolicies(Region region) {
        if (region.getActivePolicies() == null || region.getActivePolicies().isEmpty()) {
            return new SaveData.EffectState[0];
        }
        SaveData.EffectState[] states = new SaveData.EffectState[region.getActivePolicies().size()];
        for (int index = 0; index < region.getActivePolicies().size(); index++) {
            io.github.NationArchitect.model.Effect.Policy policy = region.getActivePolicies().get(index);
            SaveData.EffectState state = serializeEffect(policy, true);
            state.active = policy.getIsActive();
            states[index] = state;
        }
        return states;
    }

    private static SaveData.EffectState[] serializeEffects(ArrayList<io.github.NationArchitect.model.Effect.Effect> effects, boolean policy) {
        if (effects == null || effects.isEmpty()) {
            return new SaveData.EffectState[0];
        }
        SaveData.EffectState[] states = new SaveData.EffectState[effects.size()];
        for (int index = 0; index < effects.size(); index++) {
            states[index] = serializeEffect(effects.get(index), policy);
        }
        return states;
    }

    private static SaveData.EffectState serializeEffect(io.github.NationArchitect.model.Effect.Effect effect, boolean policy) {
        SaveData.EffectState state = new SaveData.EffectState();
        if (effect == null) {
            state.name = "";
            state.description = "";
            state.policy = policy;
            state.active = false;
            state.remainingMonths = 0;
            state.relatedComponents = new SaveData.NamedDoubleState[0];
            state.relatedMetrics = new SaveData.NamedDoubleState[0];
            return state;
        }
        state.name = effect.getName() == null ? "" : effect.getName();
        state.description = effect.getDescription() == null ? "" : effect.getDescription();
        state.policy = policy;
        state.active = true;
        state.remainingMonths = 0;
        state.relatedComponents = serializeNamedDoubleStates(effect.getRelatedComponents());
        state.relatedMetrics = serializeNamedDoubleStates(effect.getRelatedMetrics());
        return state;
    }

    private static SaveData.NamedDoubleState[] serializeNamedDoubleStates(EnumMap<?, Double> values) {
        if (values == null || values.isEmpty()) {
            return new SaveData.NamedDoubleState[0];
        }
        SaveData.NamedDoubleState[] states = new SaveData.NamedDoubleState[values.size()];
        int index = 0;
        for (Object key : values.keySet()) {
            states[index++] = new SaveData.NamedDoubleState(String.valueOf(key), values.getOrDefault(key, 0.0));
        }
        return states;
    }

    private static SaveData.NamedDoubleState[] serializeComponentDemand(Component component) {
        SaveData.NamedDoubleState[] states = new SaveData.NamedDoubleState[ProductType.values().length];
        for (int index = 0; index < ProductType.values().length; index++) {
            ProductType type = ProductType.values()[index];
            states[index] = new SaveData.NamedDoubleState(type.name(), component.getProductDemand(type));
        }
        return states;
    }

    private static SaveData.ProductState[] serializeProducts(EnumMap<ProductType, Product> products) {
        if (products == null || products.isEmpty()) {
            return new SaveData.ProductState[0];
        }
        SaveData.ProductState[] states = new SaveData.ProductState[products.size()];
        int index = 0;
        for (ProductType type : products.keySet()) {
            Product product = products.get(type);
            SaveData.ProductState state = new SaveData.ProductState();
            state.type = type.name();
            state.amount = product == null ? 0 : product.getAmount();
            state.salePrice = product == null ? type.getSalePrice() : product.getSalePrice();
            state.purchasePrice = product == null ? type.getPurchasePrice() : product.getPurchasePrice();
            states[index++] = state;
        }
        return states;
    }

    private static TerrainType deserializeTerrain(String terrainType) {
        if (terrainType == null || terrainType.isBlank()) {
            return null;
        }
        try {
            return TerrainType.valueOf(terrainType);
        } catch (IllegalArgumentException exception) {
            return null;
        }
    }

    private static EnumMap<ResourceType, Double> deserializeResourceStates(SaveData.NamedDoubleState[] states) {
        EnumMap<ResourceType, Double> resources = new EnumMap<>(ResourceType.class);
        if (states == null) {
            return resources;
        }
        for (SaveData.NamedDoubleState state : states) {
            if (state == null || state.key == null || state.key.isBlank()) {
                continue;
            }
            try {
                resources.put(ResourceType.valueOf(state.key), state.value);
            } catch (IllegalArgumentException ignored) {
            }
        }
        return resources;
    }

    private static void restoreEconomy(Economy economy, io.github.NationArchitect.model.land.Land land, SaveData.EconomyState state) {
        if (economy == null || state == null) {
            return;
        }

        economy.restoreSummary(state.income, state.expanse, state.balance);
        economy.restoreTaxRevenues(deserializeTaxRevenueStates(state.taxRevenues));
        economy.restoreComponentBudgets(deserializeComponentBudgetStates(state.componentBudgets));

        if (economy instanceof CountryEconomy) {
            ((CountryEconomy) economy).restoreTrade(state.treasury, state.importValue, state.exportValue);
        } else if (economy instanceof RegionEconomy) {
            RegionEconomy regionEconomy = (RegionEconomy) economy;
            regionEconomy.restoreProduction(deserializeProducts(state.production));
            regionEconomy.restoreDemand(deserializeProducts(state.demand));
            regionEconomy.restoreTrade(state.importValue, state.exportValue);
        }
    }

    private static EnumMap<TaxType, Double> deserializeTaxRevenueStates(SaveData.NamedDoubleState[] states) {
        EnumMap<TaxType, Double> revenues = new EnumMap<>(TaxType.class);
        if (states == null) {
            return revenues;
        }
        for (SaveData.NamedDoubleState state : states) {
            if (state == null || state.key == null || state.key.isBlank()) {
                continue;
            }
            try {
                revenues.put(TaxType.valueOf(state.key), state.value);
            } catch (IllegalArgumentException ignored) {
            }
        }
        return revenues;
    }

    private static EnumMap<ComponentType, Double> deserializeComponentBudgetStates(SaveData.NamedDoubleState[] states) {
        EnumMap<ComponentType, Double> budgets = new EnumMap<>(ComponentType.class);
        if (states == null) {
            return budgets;
        }
        for (SaveData.NamedDoubleState state : states) {
            if (state == null || state.key == null || state.key.isBlank()) {
                continue;
            }
            try {
                budgets.put(ComponentType.valueOf(state.key), state.value);
            } catch (IllegalArgumentException ignored) {
            }
        }
        return budgets;
    }

    private static EnumMap<ProductType, Product> deserializeProducts(SaveData.ProductState[] states) {
        EnumMap<ProductType, Product> products = new EnumMap<>(ProductType.class);
        if (states == null) {
            return products;
        }
        for (SaveData.ProductState state : states) {
            if (state == null || state.type == null || state.type.isBlank()) {
                continue;
            }
            try {
                ProductType type = ProductType.valueOf(state.type);
                Product product = new Product(type);
                product.produce(state.amount);
                products.put(type, product);
            } catch (IllegalArgumentException ignored) {
            }
        }
        return products;
    }

    private static void restoreComponentState(Region region, SaveData.ComponentState[] states) {
        if (states == null) {
            return;
        }
        for (SaveData.ComponentState state : states) {
            if (state == null || state.componentType == null) {
                continue;
            }
            Component component = region.getComponents().get(ComponentType.valueOf(state.componentType));
            if (component == null) {
                continue;
            }
            component.setBudgetPercentage(state.budgetPercentage <= 0 ? 100.0 : state.budgetPercentage);
            if (state.buildings != null) {
                for (SaveData.BuildingState buildingState : state.buildings) {
                    if (buildingState == null || buildingState.typeName == null) {
                        continue;
                    }
                    Building building = new Building(
                        buildingState.name == null ? buildingState.typeName : buildingState.name,
                        resolveBuildingType(ComponentType.valueOf(state.componentType), buildingState.typeName)
                    );
                    building.setWorkerAmount(buildingState.workerAmount <= 0 ? building.getMaxWorkerAmount() : buildingState.workerAmount);
                    component.restoreBuilding(building);
                }
            }
        }
    }

    private static void restoreEffects(Region region, SaveData.EffectState[] activeEffects, SaveData.EffectState[] activePolicies) {
        if (activeEffects != null) {
            for (SaveData.EffectState state : activeEffects) {
                if (state == null) {
                    continue;
                }
                region.addTemporaryEffect(new io.github.NationArchitect.model.Effect.Effect(), Math.max(1, state.remainingMonths));
            }
        }
        if (activePolicies != null) {
            try {
                Field policiesField = Region.class.getDeclaredField("activePolicies");
                policiesField.setAccessible(true);
                @SuppressWarnings("unchecked")
                ArrayList<io.github.NationArchitect.model.Effect.Policy> policies =
                    (ArrayList<io.github.NationArchitect.model.Effect.Policy>) policiesField.get(region);
                for (SaveData.EffectState ignored : activePolicies) {
                    policies.add(new io.github.NationArchitect.model.Effect.Policy());
                }
            } catch (ReflectiveOperationException ignored) {
            }
        }
    }

    private static void hydrateRegionState(SaveData.RegionState state) {
        if (state == null || state.components == null) {
            return;
        }
        for (SaveData.ComponentState componentState : state.components) {
            hydrateComponentState(componentState);
        }
    }

    private static void hydrateComponentState(SaveData.ComponentState state) {
        if (state == null || state.componentType == null || state.buildings == null) {
            return;
        }
        ComponentType componentType;
        try {
            componentType = ComponentType.valueOf(state.componentType);
        } catch (IllegalArgumentException exception) {
            return;
        }

        for (SaveData.BuildingState buildingState : state.buildings) {
            hydrateBuildingState(componentType, buildingState);
        }
    }

    private static void hydrateBuildingState(ComponentType componentType, SaveData.BuildingState state) {
        if (state == null || state.typeName == null || state.typeName.isBlank()) {
            return;
        }

        io.github.NationArchitect.model.component.BuildingType buildingType;
        try {
            buildingType = resolveBuildingType(componentType, state.typeName);
        } catch (IllegalArgumentException exception) {
            return;
        }

        if (state.name == null || state.name.isBlank()) {
            state.name = buildingType.getName();
        }
        state.maxWorkerAmount = state.maxWorkerAmount > 0 ? state.maxWorkerAmount : buildingType.getMaxWorkerAmount();
        state.workerAmount = state.workerAmount > 0 ? state.workerAmount : state.maxWorkerAmount;
        state.efficiency = state.maxWorkerAmount <= 0 ? 0.0 : (double) state.workerAmount / state.maxWorkerAmount;
        state.performanceMultiplier = state.performanceMultiplier != 0.0 ? state.performanceMultiplier : buildingType.getPerformanceMultiplier();
        state.constructionCost = state.constructionCost > 0.0 ? state.constructionCost : buildingType.getConstructionCost();
        state.maintenanceCost = state.maintenanceCost > 0.0 ? state.maintenanceCost : buildingType.getMaintenanceCost();
        state.occupiedLand = state.occupiedLand > 0.0 ? state.occupiedLand : buildingType.getOccupiedLand();
        state.production = state.production > 0.0 ? state.production : buildingType.getProduction();
        if (state.relatedMetrics == null || state.relatedMetrics.length == 0) {
            state.relatedMetrics = serializeNamedDoubleStates(buildingType.getRelatedMetrics());
        }
        if (state.relatedComponents == null || state.relatedComponents.length == 0) {
            state.relatedComponents = serializeNamedDoubleStates(buildingType.getRelatedComponents());
        }
        if (state.demand == null || state.demand.length == 0) {
            state.demand = serializeNamedDoubleStates(buildingType.getDemand());
        }
    }

    private static io.github.NationArchitect.model.component.BuildingType resolveBuildingType(ComponentType componentType, String typeName) {
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
