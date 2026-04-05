package io.github.NationArchitect.model.metric;

import io.github.NationArchitect.model.land.Region;

public class Happiness extends Metric{

    public Happiness(){
        super(MetricType.HAPPINESS, 50.0);
    }

    @Override
    public void calculateForRegion(Region region) {
        if(region == null){
            throw new IllegalArgumentException("Region cannot be null! Metric can't be calculated");
        }
        
        double healthRate = region.getLastMonthMetricValue(MetricType.HEALTH_RATE);
        double crimeRate = region.getLastMonthMetricValue(MetricType.CRIME_RATE);
        double taxBurden = region.getEconomy().getCitizenTaxBurden();
        double unemployment = region.getLastMonthMetricValue(MetricType.UNEMPLOYMENT);
        double education = region.getLastMonthMetricValue(MetricType.EDUCATION_LEVEL);
        double infrastructure = region.getInfrastructurePerformance();

        double positiveFactors = (healthRate * 0.35) + (education * 0.25) + (infrastructure * 0.1);

        double taxPenalty = taxBurden * 1.2; 
        double crimePenalty = crimeRate * 0.5;
        double unemploymentPenalty = unemployment * 0.8;

        double negativeFactors = taxPenalty + crimePenalty + unemploymentPenalty;

        double rawHappiness = 50.0 + positiveFactors - negativeFactors;

        double finalHappiness = Math.max(0.0, Math.min(100.0, rawHappiness));

        this.setValue(finalHappiness);
    }


}

