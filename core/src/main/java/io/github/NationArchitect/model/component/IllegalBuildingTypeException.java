package io.github.NationArchitect.model.component;

public class IllegalBuildingTypeException extends RuntimeException {
    public IllegalBuildingTypeException(BuildingType buildingType, Component component) {
        super("Illegal building type: Cannot construct " + buildingType.getName() + " for component " + component.getClass().getSimpleName());
    }
    public IllegalBuildingTypeException(BuildingType buildingType, BuildingType requiredBuilding) {
        super("Illegal building type: Cannot construct " + buildingType.getName() + " without constructing " + requiredBuilding.getName());
    }
}
