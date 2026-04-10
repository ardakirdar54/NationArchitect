package io.github.NationArchitect.controller.savemanager;

public class BuildingSnapshot {
    public String name;
    public String typeName;
    public int workerAmount;

    public BuildingSnapshot() {
    }

    public BuildingSnapshot(String name, String typeName, int workerAmount) {
        this.name = name;
        this.typeName = typeName;
        this.workerAmount = workerAmount;
    }
}
