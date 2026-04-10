package io.github.NationArchitect.model.metric;

import io.github.NationArchitect.model.land.Region;

public class Unemployment extends Metric{

    public Unemployment(){
        super(MetricType.UNEMPLOYMENT, 0.0);
    }

    @Override
    public void calculateForRegion(Region region) {
        if(region == null){
            throw new IllegalArgumentException("Region cannot be null! Metric can't be calculated");
        }
        int totalWorkers = region.getTotalEmploymentCapacity();
        int workForce = region.getPopulation().getWorkingAgePopulation();

        if(workForce == 0){
            this.setValue(0);
            return;
        }

        int unemployedPeople = Math.max(0, workForce - totalWorkers);
        double unemploymentRate = (((double) unemployedPeople) / workForce) * 100.0;

        double policyBonus = region.getTotalPolicyModifierForMetric(MetricType.UNEMPLOYMENT);
        double activeEffectBonus = region.getTotalActiveEffectModifierForMetric(MetricType.UNEMPLOYMENT);

        double finalUnemployment = Math.min(100, Math.max(0, unemploymentRate + policyBonus + activeEffectBonus));

        this.setValue(finalUnemployment);
    }
}
