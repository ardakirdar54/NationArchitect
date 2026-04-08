package io.github.NationArchitect.model.metric;

import io.github.NationArchitect.model.component.ComponentType;
import io.github.NationArchitect.model.land.Region;

public class Stability extends Metric {

    private double targetStability;
    public Stability(){
        super(MetricType.STABILITY, 50.0);
    }

    @Override
    public void calculateForRegion(Region region) {
        if(region == null){
            throw new IllegalArgumentException("Region cannot be null! Metric can't be calculated");
        }
        
        double happiness = region.getLastMonthMetricValue(MetricType.HAPPINESS);
        double crimeRate = region.getLastMonthMetricValue(MetricType.CRIME_RATE);
        double unemployment = region.getLastMonthMetricValue(MetricType.UNEMPLOYMENT);

        double securityPerformance = region.getComponentPerformance(ComponentType.SECURITY);

        double happinessImpact = (happiness - 50.0) * 0.6;

        double crimePenalty = crimeRate * 0.4;
        double unemploymentPenalty = unemployment * 0.3;

        double securityBonus = securityPerformance * 0.5;

        double policyBonus = region.getTotalPolicyModifierForMetric(MetricType.STABILITY);

        double targetStability = 50.0 + happinessImpact + securityBonus + policyBonus - crimePenalty - unemploymentPenalty;
        targetStability = Math.max(0.0, Math.min(100.0, targetStability));
        this.targetStability = targetStability;

        double currentStability = this.getValue();
        double difference = targetStability - currentStability;

        double driftSpeed = 0.10;

        if (difference < 0 && crimeRate > 70.0) {
            driftSpeed = 0.25;
        }

        double finalStability = currentStability + (difference * driftSpeed);

        if (Math.abs(targetStability - finalStability) < 0.5) {
            finalStability = targetStability;
        }
        
        this.setValue(finalStability);
    }

    public double getTargetStability(){return this.targetStability;}
}

