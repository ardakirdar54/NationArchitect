package io.github.NationArchitect.model.land;


import java.util.EnumMap;
import java.util.Map;

import io.github.NationArchitect.model.economy.Economy;
import io.github.NationArchitect.model.metric.*;
import io.github.NationArchitect.model.population.Population;

public class Country extends Land {

    private Region[] regions;

    // private Date date;

    Country(String name, Economy economy, Population population) {
        super(name, economy, population);
        this.regions = new Region[10];
        // this.date = new Date();
    }

    /**
     * Adds the policy to activePolicies list for all of the regions.
    *
    * @param policy the policy will be implemented for all of the regions
    */
    @Override
    public void implementPolicy(Policy policy) {
        for (Region region : regions) {
            region.implementPolicy(policy);
        }
    }

    @Override
    public void cancelPolicy(Policy policy) {
        for (Region region : regions) {
            region.cancelPolicy(policy);
        }
    }

    @Override
    public void update() {
        for (Region region : regions) {
            region.update();
        }

        for (Metric metric : this.metrics.values()) {
            metric.calculateForCountry(this);
        }
    }

    public Region[] getRegions(){return this.regions;}
}

