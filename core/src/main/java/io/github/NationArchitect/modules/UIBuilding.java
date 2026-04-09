package io.github.NationArchitect.modules;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import io.github.NationArchitect.model.component.AgricultureBuilding;
import io.github.NationArchitect.model.component.AirTransportBuilding;
import io.github.NationArchitect.model.component.BuildingType;
import io.github.NationArchitect.model.component.ComponentType;
import io.github.NationArchitect.model.component.EducationBuilding;
import io.github.NationArchitect.model.component.ElectricityBuilding;
import io.github.NationArchitect.model.component.FactoryBuilding;
import io.github.NationArchitect.model.component.HealthServicesBuilding;
import io.github.NationArchitect.model.component.InternetBuilding;
import io.github.NationArchitect.model.component.MarineTransportBuilding;
import io.github.NationArchitect.model.component.OfficeBuilding;
import io.github.NationArchitect.model.component.RailTransportBuilding;
import io.github.NationArchitect.model.component.RoadNetworkBuilding;
import io.github.NationArchitect.model.component.RoadTransportBuilding;
import io.github.NationArchitect.model.component.SecurityBuilding;
import io.github.NationArchitect.model.component.TourismBuilding;
import io.github.NationArchitect.model.component.WaterManagementBuilding;
import io.github.NationArchitect.model.metric.MetricType;

public class UIBuilding {

    private static final double MAINTENANCE_COST_MULTIPLIER = 0.30;

    public enum Category { EDUCATION, HEALTH, SECURITY, INDUSTRY, INFRASTRUCTURE, TRANSPORTATION }

    public static final Set<String> COASTAL_REGIONS = new HashSet<>(
        Arrays.asList("Marmara", "Aegean", "Mediterranean", "Black Sea")
    );

    private static final String MISSING_IMAGE_PATH = "__missing__";

    private final BuildingType buildingType;
    private final String imagePath;
    private final Category category;
    private final ComponentType targetComponentType;
    private final boolean coastalOnly;

    public UIBuilding(BuildingType buildingType, Category category) {
        this(buildingType, category, MISSING_IMAGE_PATH, isCoastalBuilding(buildingType));
    }

    public UIBuilding(BuildingType buildingType, Category category, String imagePath, boolean coastalOnly) {
        this.buildingType = buildingType;
        this.category = category;
        this.imagePath = imagePath == null || imagePath.isBlank() ? MISSING_IMAGE_PATH : imagePath;
        this.targetComponentType = resolveTargetComponentType(buildingType);
        this.coastalOnly = coastalOnly;
    }

    public boolean isAvailableFor(UIRegion region) {
        if (!coastalOnly) {
            return true;
        }
        return COASTAL_REGIONS.contains(region.getName());
    }

    public String getName() {
        return buildingType.getName();
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getConstructionCost() {
        return (int) Math.round(buildingType.getConstructionCost());
    }

    public int getMonthlyBudget() {
        return (int) Math.round(buildingType.getMaintenanceCost() * MAINTENANCE_COST_MULTIPLIER);
    }

    public Category getCategory() {
        return category;
    }

    public boolean isCoastalOnly() {
        return coastalOnly;
    }

    public float getEducationEffect() {
        return getMetricEffect(MetricType.EDUCATION_LEVEL);
    }

    public float getHealthEffect() {
        return getMetricEffect(MetricType.HEALTH_RATE);
    }

    public float getHappinessEffect() {
        return getMetricEffect(MetricType.HAPPINESS);
    }

    public float getSecurityEffect() {
        float stabilityEffect = getMetricEffect(MetricType.STABILITY);
        float componentEffect = getComponentEffect(ComponentType.SECURITY);
        return stabilityEffect + componentEffect;
    }

    public float getIndustryEffect() {
        float productionEffect = (float) buildingType.getProduction();
        float componentEffect = getComponentEffect(ComponentType.FACTORY)
            + getComponentEffect(ComponentType.OFFICE)
            + getComponentEffect(ComponentType.AGRICULTURE)
            + getComponentEffect(ComponentType.TOURISM);
        return productionEffect + componentEffect;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public ComponentType getTargetComponentType() {
        return targetComponentType;
    }

    private float getMetricEffect(MetricType metricType) {
        if (buildingType.getRelatedMetrics() == null) {
            return 0f;
        }
        return buildingType.getRelatedMetrics().getOrDefault(metricType, 0.0).floatValue();
    }

    private float getComponentEffect(ComponentType componentType) {
        if (buildingType.getRelatedComponents() == null) {
            return 0f;
        }
        return buildingType.getRelatedComponents().getOrDefault(componentType, 0.0).floatValue();
    }

    private static boolean isCoastalBuilding(BuildingType buildingType) {
        return buildingType instanceof MarineTransportBuilding;
    }

    private static ComponentType resolveTargetComponentType(BuildingType buildingType) {
        if (buildingType instanceof EducationBuilding) {
            return ComponentType.EDUCATION;
        }
        if (buildingType instanceof HealthServicesBuilding) {
            return ComponentType.HEALTH_SERVICES;
        }
        if (buildingType instanceof SecurityBuilding) {
            return ComponentType.SECURITY;
        }
        if (buildingType instanceof FactoryBuilding) {
            return ComponentType.FACTORY;
        }
        if (buildingType instanceof OfficeBuilding) {
            return ComponentType.OFFICE;
        }
        if (buildingType instanceof TourismBuilding) {
            return ComponentType.TOURISM;
        }
        if (buildingType instanceof AgricultureBuilding) {
            return ComponentType.AGRICULTURE;
        }
        if (buildingType instanceof RoadTransportBuilding) {
            return ComponentType.ROAD_TRANSPORT;
        }
        if (buildingType instanceof RailTransportBuilding) {
            return ComponentType.RAIL_TRANSPORT;
        }
        if (buildingType instanceof MarineTransportBuilding) {
            return ComponentType.MARINE_TRANSPORT;
        }
        if (buildingType instanceof AirTransportBuilding) {
            return ComponentType.AIR_TRANSPORT;
        }
        if (buildingType instanceof RoadNetworkBuilding) {
            return ComponentType.ROAD_NETWORK;
        }
        if (buildingType instanceof ElectricityBuilding) {
            return ComponentType.ELECTRICITY;
        }
        if (buildingType instanceof WaterManagementBuilding) {
            return ComponentType.WATER_MANAGEMENT;
        }
        if (buildingType instanceof InternetBuilding) {
            return ComponentType.INTERNET;
        }
        throw new IllegalArgumentException("Unsupported building type: " + buildingType.getClass().getName());
    }
}
