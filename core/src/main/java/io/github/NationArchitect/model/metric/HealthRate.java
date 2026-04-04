package io.github.NationArchitect.model.metric;

import io.github.NationArchitect.model.land.Region;

public class HealthRate extends Metric{

    public HealthRate(){
        super(MetricType.HEALTH_RATE, 50.0);
    }

    @Override
    public void calculateForRegion(Region region) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateForRegion'");
    }

}

