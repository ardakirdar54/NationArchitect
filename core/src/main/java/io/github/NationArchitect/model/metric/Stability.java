package io.github.NationArchitect.model.metric;

import io.github.NationArchitect.model.land.Region;

public class Stability extends Metric {

    public Stability(){
        super(MetricType.STABILITY, 50.0);
    }

    @Override
    public void calculateForRegion(Region region) {
        if(region == null){
            throw new IllegalArgumentException("Region cannot be null! Metric can't be calculated");
        }
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateForRegion'");
    }
}

