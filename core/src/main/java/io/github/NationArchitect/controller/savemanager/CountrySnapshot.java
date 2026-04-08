package io.github.NationArchitect.controller.savemanager;

public class CountrySnapshot {
    public String name;
    public PopulationSnapshot population;
    public MetricSnapshot[] metrics;
    public TaxSnapshot tax;
    public RegionSnapshot[] regions;

    public CountrySnapshot() {
    }
}
