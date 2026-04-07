package io.github.NationArchitect.model.component;

/**
 * Thrown when a building type is used with an incompatible component or without a required prerequisite.
 */
public class IllegalBuildingTypeException extends RuntimeException {
    /**
     * Creates an exception for a building that does not belong to the given component.
     *
     * @param buildingType attempted building type
     * @param component target component
     */
    public IllegalBuildingTypeException(BuildingType buildingType, Component component) {
        super("Illegal building type: Cannot construct " + buildingType.getName() + " for component " + component.getClass().getSimpleName());
    }

    /**
     * Creates an exception for a building that requires another building to exist first.
     *
     * @param buildingType attempted building type
     * @param requiredBuilding prerequisite building type
     */
    public IllegalBuildingTypeException(BuildingType buildingType, BuildingType requiredBuilding) {
        super("Illegal building type: Cannot construct " + buildingType.getName() + " without constructing " + requiredBuilding.getName());
    }
}

