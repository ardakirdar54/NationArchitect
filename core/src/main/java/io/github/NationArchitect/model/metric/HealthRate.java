package io.github.NationArchitect.model.metric;

import io.github.NationArchitect.model.land.Region;
import io.github.NationArchitect.model.population.Age;

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

        int totalBurden = region.getPopulation().getTotalPopulation();
        totalBurden += region.getPopulation().getAgeDistribution(Age.ELDERLY);

        if (totalBurden == 0) {
            this.setValue(100.0);
            return;
        }

        int totalHealthServiceCapacity = region.getTotalHealthServiceCapacity();

        double policyBonus = region.getTotalPolicyModifierForMetric(MetricType.HEALTH_RATE);

        double finalHealthRate = Math.min(100, 
            Math.max(0, calculateFulfillment(totalBurden, totalHealthServiceCapacity) + policyBonus));
        this.setValue(finalHealthRate);
    }

    private double calculateFulfillment(int demand, int supply){
        if (demand == 0) return 100.0;
        double fulfillment = ((double) supply / demand) * 100;
        return Math.min(100.0, fulfillment);
    }

}
