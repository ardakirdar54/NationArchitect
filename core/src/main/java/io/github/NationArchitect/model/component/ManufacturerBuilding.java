package io.github.NationArchitect.model.component;

/**
 * Defines the building types available for the Manufacturer component.
 */
public class ManufacturerBuilding extends Building {

    /** Product output provided by the building. */
    private double production;

    /**
     * Creates a manufacturer building from a building type definition.
     *
     * @param name runtime name of the building
     * @param type type definition used to initialize the building
     */
    public ManufacturerBuilding(String name, BuildingType type) {
        super(name, type);
        this.production = type.getProduction() * getEfficiency();
    }

    /**
     * Returns the production output of the building.
     *
     * @return production amount
     */
    public double getProduction() {
        return production;
    }

    @Override
    public void calculateEfficiency() {
        super.calculateEfficiency();
        production = getType().getProduction() * getEfficiency();
    }
}

