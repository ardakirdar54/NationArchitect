package io.github.NationArchitect.controller.savemanager;

import java.util.HashMap;
import java.util.Map;

public class PopulationSnapshot {
    public Map<String, Integer> ageDistribution;
    public Map<String, Integer> genderDistribution;

    public PopulationSnapshot() {
    }

    public PopulationSnapshot(Map<String, Integer> ageDistribution, Map<String, Integer> genderDistribution) {
        this.ageDistribution = ageDistribution;
        this.genderDistribution = genderDistribution;
    }
}
