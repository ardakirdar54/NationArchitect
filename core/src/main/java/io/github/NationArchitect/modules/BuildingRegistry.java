package io.github.NationArchitect.modules;

import com.badlogic.gdx.utils.Array;
import io.github.NationArchitect.model.component.AgricultureBuilding;
import io.github.NationArchitect.model.component.AirTransportBuilding;
import io.github.NationArchitect.model.component.BuildingType;
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

public class BuildingRegistry {

    private static Array<UIBuilding> educationBuildings;
    private static Array<UIBuilding> healthBuildings;
    private static Array<UIBuilding> securityBuildings;
    private static Array<UIBuilding> transportationBuildings;
    private static Array<UIBuilding> infrastructureBuildings;
    private static Array<UIBuilding> industryBuildings;

    public static Array<UIBuilding> getEducationBuildings() {
        if (educationBuildings == null) {
            educationBuildings = buildCategory(UIBuilding.Category.EDUCATION, EducationBuilding.values());
        }
        return educationBuildings;
    }

    public static Array<UIBuilding> getHealthBuildings() {
        if (healthBuildings == null) {
            healthBuildings = buildCategory(UIBuilding.Category.HEALTH, HealthServicesBuilding.values());
        }
        return healthBuildings;
    }

    public static Array<UIBuilding> getSecurityBuildings() {
        if (securityBuildings == null) {
            securityBuildings = buildCategory(UIBuilding.Category.SECURITY, SecurityBuilding.values());
        }
        return securityBuildings;
    }

    public static Array<UIBuilding> getTransportationBuildings() {
        if (transportationBuildings == null) {
            transportationBuildings = new Array<>();
            addAll(transportationBuildings, UIBuilding.Category.TRANSPORTATION, RoadTransportBuilding.values());
            addAll(transportationBuildings, UIBuilding.Category.TRANSPORTATION, RailTransportBuilding.values());
            addAll(transportationBuildings, UIBuilding.Category.TRANSPORTATION, MarineTransportBuilding.values());
            addAll(transportationBuildings, UIBuilding.Category.TRANSPORTATION, AirTransportBuilding.values());
        }
        return transportationBuildings;
    }

    public static Array<UIBuilding> getInfrastructureBuildings() {
        if (infrastructureBuildings == null) {
            infrastructureBuildings = new Array<>();
            addAll(infrastructureBuildings, UIBuilding.Category.INFRASTRUCTURE, ElectricityBuilding.values());
            addAll(infrastructureBuildings, UIBuilding.Category.INFRASTRUCTURE, WaterManagementBuilding.values());
            addAll(infrastructureBuildings, UIBuilding.Category.INFRASTRUCTURE, InternetBuilding.values());
            addAll(infrastructureBuildings, UIBuilding.Category.INFRASTRUCTURE, RoadNetworkBuilding.values());
            addAll(infrastructureBuildings, UIBuilding.Category.INFRASTRUCTURE, AgricultureBuilding.values());
        }
        return infrastructureBuildings;
    }

    public static Array<UIBuilding> getIndustryBuildings() {
        if (industryBuildings == null) {
            industryBuildings = new Array<>();
            addAll(industryBuildings, UIBuilding.Category.INDUSTRY, FactoryBuilding.values());
            addAll(industryBuildings, UIBuilding.Category.INDUSTRY, OfficeBuilding.values());
            addAll(industryBuildings, UIBuilding.Category.INDUSTRY, TourismBuilding.values());
        }
        return industryBuildings;
    }

    private static Array<UIBuilding> buildCategory(UIBuilding.Category category, BuildingType[] buildingTypes) {
        Array<UIBuilding> buildings = new Array<>();
        addAll(buildings, category, buildingTypes);
        return buildings;
    }

    private static void addAll(Array<UIBuilding> target, UIBuilding.Category category, BuildingType[] buildingTypes) {
        for (BuildingType buildingType : buildingTypes) {
            target.add(new UIBuilding(buildingType, category));
        }
    }
}
