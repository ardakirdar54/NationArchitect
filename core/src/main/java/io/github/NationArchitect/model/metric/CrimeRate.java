package io.github.NationArchitect.model.metric;

import io.github.NationArchitect.model.land.Region;

public class CrimeRate extends Metric{

    public CrimeRate(){
        super(MetricType.CRIME_RATE, 50.0);
    }

    @Override
    public void calculateForRegion(Region region) {
        double crimeRate = 1;
        this.setValue(region.getSecurityPerformance());
    }

    
}

