package io.github.NationArchitect.model.metric;

import io.github.NationArchitect.model.component.ComponentType;
import io.github.NationArchitect.model.land.Region;

public class CrimeRate extends Metric{

    public CrimeRate(){
        super(MetricType.CRIME_RATE, 50.0);
    }

    @Override
    public void calculateForRegion(Region region) {
        if(region == null){
            throw new IllegalArgumentException("Region cannot be null! Metric can't be calculated");
        }
        
        double happiness = region.getLastMonthMetricValue(MetricType.HAPPINESS);
        double unemployment = region.getLastMonthMetricValue(MetricType.UNEMPLOYMENT);
        double securityPerformance = region.getComponentPerformance(ComponentType.SECURITY);
        double baseCrimeRate = region.getBaseCrimeRate();

        double unemploymentPenalty = unemployment * 0.5;
        double unhappinessPenalty = (100.0 - happiness) * 0.2;
        double rawCrime = baseCrimeRate + unemploymentPenalty + unhappinessPenalty;

        double securityMultiplier = 1.0 - (securityPerformance * 0.007);
        securityMultiplier = Math.max(0.1, securityMultiplier);

        double policyBonus = region.getTotalPolicyModifierForMetric(MetricType.CRIME_RATE);

        double finalCrimeRate = rawCrime * securityMultiplier;

        finalCrimeRate = Math.max(0, Math.min(100, finalCrimeRate + policyBonus));

        this.setValue(finalCrimeRate);
    }

    
}

