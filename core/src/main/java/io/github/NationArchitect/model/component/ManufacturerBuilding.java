package io.github.NationArchitect.model.component;

public class ManufacturerBuilding extends Building {

    private final double production;

    ManufacturerBuilding(String name, BuildingType type) {
        super(name, type);
    }

    public double getProduction() {
        return production;
    }
}
