package io.github.NationArchitect.model.population;

public interface ReadOnlyPopulation {
    int getTotalPopulation();
    int getWorkingAgePopulation();
    int getAgeDistribution(Age type);
    int getBirths();
    int getDeaths();
}
