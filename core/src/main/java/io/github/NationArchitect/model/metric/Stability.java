package io.github.NationArchitect.model.metric;

import io.github.NationArchitect.model.land.Region;

public class Stability extends Metric {

    public Stability(){
        super(MetricType.STABILITY, 50.0);
    }

    @Override
    public void calculateForRegion(Region region) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateForRegion'");
    }
}

