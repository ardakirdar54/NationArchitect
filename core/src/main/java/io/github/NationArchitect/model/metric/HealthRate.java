package io.github.NationArchitect.model.metric;

import io.github.NationArchitect.model.component.ComponentType;
import io.github.NationArchitect.model.land.Region;

public class HealthRate extends Metric{

    private static final double BASE_HEALTH = 20.0;

    public HealthRate(){
        super(MetricType.HEALTH_RATE, 50.0);
    }

    @Override
    public void calculateForRegion(Region region) {
        if(region == null){
            throw new IllegalArgumentException("Region cannot be null! Metric can't be calculated");
        }

        int totalPopulation = region.getPopulation().getTotalPopulation();

        if (totalPopulation == 0) {
            this.setValue(100.0);
            return;
        }

        double totalMultiplier = region.getComponentPerformance(ComponentType.HEALTH_SERVICES);

        
    }

}

