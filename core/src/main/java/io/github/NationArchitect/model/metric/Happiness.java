package io.github.NationArchitect.model.metric;

import io.github.NationArchitect.model.land.Region;
import io.github.NationArchitect.model.product.ProductType;

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
        double taxBurden = taxAverage(region);
        double unemployment = region.getLastMonthMetricValue(MetricType.UNEMPLOYMENT);
        double education = region.getLastMonthMetricValue(MetricType.EDUCATION_LEVEL);
        double infrastructure = region.getInfrastructurePerformance();

        double positiveFactors = (healthRate * 0.35) + (education * 0.25) + (infrastructure * 0.1);

       // double taxPenalty = taxBurden * 1.2;
        double crimePenalty = crimeRate * 0.5;
        double unemploymentPenalty = unemployment * 0.8;

        double negativeFactors = /*taxPenalty*/ + crimePenalty + unemploymentPenalty;

        double rawHappiness = 50.0 + positiveFactors - negativeFactors;

        double policyBonus = region.getTotalPolicyModifierForMetric(MetricType.HAPPINESS);
        double activeEffectBonus = region.getTotalActiveEffectModifierForMetric(MetricType.HAPPINESS);

        double finalHappiness = Math.max(0.0, Math.min(100.0, rawHappiness + policyBonus + activeEffectBonus));

        this.setValue(finalHappiness);
    }

    private double taxAverage(Region region){
        double taxBurden = region.getEconomy().getTax().getIncomeTaxRate();
        taxBurden += region.getEconomy().getTax().getPropertyTaxRate();
        double excise = region.getEconomy().getTax().getExciseTaxRate(ProductType.FOOD) * 5;
        excise += region.getEconomy().getTax().getExciseTaxRate(ProductType.WATER) * 5;
        excise += region.getEconomy().getTax().getExciseTaxRate(ProductType.ENERGY);
        excise += region.getEconomy().getTax().getExciseTaxRate(ProductType.TECHNOLOGY);
        excise += region.getEconomy().getTax().getExciseTaxRate(ProductType.INDUSTRIAL_GOOD);
        excise += region.getEconomy().getTax().getExciseTaxRate(ProductType.TOURISM_SERVICE);
        excise /= 14;
        taxBurden += excise;
        taxBurden += region.getEconomy().getTax().getVatRate();

        taxBurden /= 4;

        return taxBurden;
    }

}

