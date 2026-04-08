package io.github.NationArchitect.controller.savemanager;

import io.github.NationArchitect.model.component.ComponentType;

public class ComponentSnapshot {
    public ComponentType componentType;
    public double budgetPercentage;
    public BuildingSnapshot[] buildings;

    public ComponentSnapshot() {
    }
}
