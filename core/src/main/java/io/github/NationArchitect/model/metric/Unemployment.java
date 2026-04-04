package io.github.NationArchitect.model.metric;

import io.github.NationArchitect.model.land.Region;

public class Unemployment extends Metric{

    public Unemployment(){
        super(MetricType.UNEMPLOYMENT, 50.0);
    }

    @Override
    public void calculateForRegion(Region region) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateForRegion'");
    }
}

