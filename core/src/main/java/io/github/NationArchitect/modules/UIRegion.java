package io.github.NationArchitect.modules;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import io.github.NationArchitect.model.component.Agriculture;
import io.github.NationArchitect.model.component.AirTransport;
import io.github.NationArchitect.model.component.Building;
import io.github.NationArchitect.model.component.Component;
import io.github.NationArchitect.model.component.ComponentType;
import io.github.NationArchitect.model.component.Education;
import io.github.NationArchitect.model.component.Electricity;
import io.github.NationArchitect.model.component.Factory;
import io.github.NationArchitect.model.component.HealthServices;
import io.github.NationArchitect.model.component.Internet;
import io.github.NationArchitect.model.component.MarineTransport;
import io.github.NationArchitect.model.component.Office;
import io.github.NationArchitect.model.component.RailTransport;
import io.github.NationArchitect.model.component.RoadNetwork;
import io.github.NationArchitect.model.component.RoadTransport;
import io.github.NationArchitect.model.component.Security;
import io.github.NationArchitect.model.component.Tourism;
import io.github.NationArchitect.model.component.WaterManagement;
import io.github.NationArchitect.model.economy.CountryEconomy;
import io.github.NationArchitect.model.economy.RegionEconomy;
import io.github.NationArchitect.model.economy.Tax;
import io.github.NationArchitect.model.land.Country;
import io.github.NationArchitect.model.land.Region;
import io.github.NationArchitect.model.metric.MetricType;
import io.github.NationArchitect.model.population.Age;
import io.github.NationArchitect.model.population.Gender;
import io.github.NationArchitect.model.population.Population;

import java.util.EnumMap;

public class UIRegion {
    private static final int MAX_UI_POPULATION = 10_000_000;

    private final int id;
    private final String name;
    private final Rectangle bounds;
    private Region region;

    public UIRegion(int id, String name, float x, float y, float width, float height) {
        this.id = id;
        this.name = name;
        this.bounds = new Rectangle(x, y, width, height);
        this.region = createBackingRegion(name, id);
    }

    public boolean contains(float x, float y) {
        return bounds.contains(x, y);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Region getBackingRegion() {
        return region;
    }

    public void setBackingRegion(Region region) {
        if (region != null) {
            this.region = region;
        }
    }

    public float getPopulation() {
        return MathUtils.clamp((region.getPopulation().getTotalPopulation() / (float) MAX_UI_POPULATION) * 100f, 0f, 100f);
    }

    public float getSecurity() {
        return 100f - getCrimeRate();
    }

    public float getIndustry() {
        return 100f - getUnemployment();
    }

    public float getHealth() {
        return getMetric(MetricType.HEALTH_RATE);
    }

    public float getEducation() {
        return getMetric(MetricType.EDUCATION_LEVEL);
    }

    public float getHappiness() {
        return getMetric(MetricType.HAPPINESS);
    }

    public float getCrimeRate() {
        return getMetric(MetricType.CRIME_RATE);
    }

    public float getUnemployment() {
        return getMetric(MetricType.UNEMPLOYMENT);
    }

    public long getTotalPopulation() {
        return region.getPopulation().getTotalPopulation();
    }

    public float getMalePct() {
        return getGenderRatio(Gender.MALE);
    }

    public float getFemalePct() {
        return getGenderRatio(Gender.FEMALE);
    }

    public float getAge0_18() {
        return getAgeRatio(Age.BABY, Age.CHILD, Age.TEENAGER);
    }

    public float getAge18_24() {
        return getAgeRatio(Age.YOUNG_ADULT);
    }

    public float getAge24_65() {
        return getAgeRatio(Age.ADULT);
    }

    public float getAge65plus() {
        return getAgeRatio(Age.ELDERLY);
    }

    public void setPopulation(float value) {
        int targetPopulation = Math.max(0, Math.round((value / 100f) * MAX_UI_POPULATION));
        scalePopulation(targetPopulation);
    }

    public void setSecurity(float value) {
        region.setMetricValue(MetricType.CRIME_RATE, MathUtils.clamp(100f - value, 0f, 100f));
        region.setMetricValue(MetricType.STABILITY, MathUtils.clamp(value, 0f, 100f));
    }

    public void setIndustry(float value) {
        region.setMetricValue(MetricType.UNEMPLOYMENT, MathUtils.clamp(100f - value, 0f, 100f));
    }

    public void setHealth(float value) {
        region.setMetricValue(MetricType.HEALTH_RATE, MathUtils.clamp(value, 0f, 100f));
    }

    public void setEducation(float value) {
        region.setMetricValue(MetricType.EDUCATION_LEVEL, MathUtils.clamp(value, 0f, 100f));
    }

    public void setHappiness(float value) {
        region.setMetricValue(MetricType.HAPPINESS, MathUtils.clamp(value, 0f, 100f));
    }

    public boolean constructBuilding(UIBuilding uiBuilding) {
        return constructBuilding(uiBuilding, null);
    }

    public boolean constructBuilding(UIBuilding uiBuilding, Country country) {
        if (uiBuilding == null) {
            return false;
        }
        Component component = region.getComponents().get(uiBuilding.getTargetComponentType());
        if (component == null) {
            return false;
        }

        double constructionCost = uiBuilding.getConstructionCost();
        CountryEconomy countryEconomy = null;
        if (country != null && country.getEconomy() instanceof CountryEconomy) {
            countryEconomy = (CountryEconomy) country.getEconomy();
            if (!countryEconomy.spend(constructionCost)) {
                return false;
            }
        }

        String buildingName = uiBuilding.getName() + " " + (component.getBuildings().size() + 1);
        Building building = new Building(buildingName, uiBuilding.getBuildingType());
        building.setWorkerAmount(building.getMaxWorkerAmount());
        component.constructBuilding(building);

        if (region.getEconomy() instanceof RegionEconomy) {
            ((RegionEconomy) region.getEconomy()).update(region);
        }
        if (countryEconomy != null) {
            country.calculatePopulation();
            countryEconomy.update(country);
        }
        return true;
    }

    public boolean destroyBuilding(ComponentType componentType, Building building, Country country) {
        if (componentType == null || building == null || region.getComponents() == null) {
            return false;
        }

        Component component = region.getComponents().get(componentType);
        if (component == null) {
            return false;
        }

        component.destroyBuilding(building);

        if (region.getEconomy() instanceof RegionEconomy) {
            ((RegionEconomy) region.getEconomy()).update(region);
        }
        if (country != null && country.getEconomy() instanceof CountryEconomy) {
            country.calculatePopulation();
            ((CountryEconomy) country.getEconomy()).update(country);
        }
        return true;
    }

    private float getMetric(MetricType metricType) {
        return (float) region.getMetricValue(metricType);
    }

    private float getGenderRatio(Gender gender) {
        int totalPopulation = region.getPopulation().getTotalPopulation();
        if (totalPopulation == 0) {
            return 0f;
        }
        return region.getMutablePopulation().getGenderDistribution().get(gender) / (float) totalPopulation;
    }

    private float getAgeRatio(Age... ages) {
        int totalPopulation = region.getPopulation().getTotalPopulation();
        if (totalPopulation == 0) {
            return 0f;
        }

        int groupPopulation = 0;
        for (Age age : ages) {
            groupPopulation += region.getPopulation().getAgeDistribution(age);
        }
        return groupPopulation / (float) totalPopulation;
    }

    private void scalePopulation(int targetPopulation) {
        Population population = region.getMutablePopulation();
        int currentPopulation = population.getTotalPopulation();
        if (currentPopulation <= 0) {
            population.setAgeDistribution(createAgeDistribution(targetPopulation));
            population.setGenderDistribution(createGenderDistribution(targetPopulation));
            return;
        }

        double ratio = targetPopulation / (double) currentPopulation;
        EnumMap<Age, Integer> scaledAges = new EnumMap<>(Age.class);
        for (Age age : Age.values()) {
            scaledAges.put(age, Math.max(0, (int) Math.round(population.getAgeDistribution().get(age) * ratio)));
        }
        population.setAgeDistribution(scaledAges);
        population.setGenderDistribution(createGenderDistribution(targetPopulation));
    }

    private static Region createBackingRegion(String regionName, int regionId) {
        Population population = new Population()
            .setAgeDistribution(createAgeDistribution(1_500_000 + (regionId * 250_000)))
            .setGenderDistribution(createGenderDistribution(1_500_000 + (regionId * 250_000)));
        Region region = new Region(regionName, new RegionEconomy(new Tax()), population);

        region.setMetricValue(MetricType.HAPPINESS, 50);
        region.setMetricValue(MetricType.UNEMPLOYMENT, 35);
        region.setMetricValue(MetricType.CRIME_RATE, 40);
        region.setMetricValue(MetricType.EDUCATION_LEVEL, 45);
        region.setMetricValue(MetricType.HEALTH_RATE, 55);
        region.setMetricValue(MetricType.STABILITY, 60);

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
        region.setComponents(components);

        return region;
    }

    private static EnumMap<Age, Integer> createAgeDistribution(int totalPopulation) {
        EnumMap<Age, Integer> ageDistribution = new EnumMap<>(Age.class);
        ageDistribution.put(Age.BABY, Math.round(totalPopulation * 0.05f));
        ageDistribution.put(Age.CHILD, Math.round(totalPopulation * 0.125f));
        ageDistribution.put(Age.TEENAGER, Math.round(totalPopulation * 0.125f));
        ageDistribution.put(Age.YOUNG_ADULT, Math.round(totalPopulation * 0.25f));
        ageDistribution.put(Age.ADULT, Math.round(totalPopulation * 0.33f));
        ageDistribution.put(Age.ELDERLY, Math.max(0, totalPopulation
            - ageDistribution.get(Age.BABY)
            - ageDistribution.get(Age.CHILD)
            - ageDistribution.get(Age.TEENAGER)
            - ageDistribution.get(Age.YOUNG_ADULT)
            - ageDistribution.get(Age.ADULT)));
        return ageDistribution;
    }

    private static EnumMap<Gender, Integer> createGenderDistribution(int totalPopulation) {
        EnumMap<Gender, Integer> genderDistribution = new EnumMap<>(Gender.class);
        int malePopulation = totalPopulation / 2;
        genderDistribution.put(Gender.MALE, malePopulation);
        genderDistribution.put(Gender.FEMALE, totalPopulation - malePopulation);
        return genderDistribution;
    }
}
