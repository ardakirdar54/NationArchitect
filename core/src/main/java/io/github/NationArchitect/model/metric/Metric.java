package io.github.NationArchitect.model.metric;

import io.github.NationArchitect.model.land.Country;
import io.github.NationArchitect.model.land.Region;

public abstract class Metric {

    //The percentage of this metric's sufficiency.
    private double value;

    //The percentage of this metric's sufficiency in last month.
    private double lastMonthValue;

    //The type of this metric.
    private MetricType type;

    public Metric(MetricType type, double initialValue){
        this.type = type;
        this.value = initialValue;
        this.lastMonthValue = initialValue;
    }

    /**
     * Compares last month value and current value. Returns the percentage of the change
     * @return the change in value
     */
    public double getTrend(){
        return this.value - this.lastMonthValue;
    }

    public void setValue(double value){this.value = value;}

    public void setLastMonthValue(double lastMonthValue){this.lastMonthValue = lastMonthValue;}

    public abstract void calculateForRegion(Region region);

    /**
     * Standard calculation for population-weighted metrics
     * @param country 
     */
    public void calculateForCountry(Country country) {
        if(country == null){
            throw new IllegalArgumentException("Country cannot be null! Metric can't be calculated");
        }
        int totalPopulation = country.getPopulation().getTotalPopulation();

        if (totalPopulation == 0) {
            this.setValue(0.0);
            return; 
        }

        double sumOfWeightedValue = 0.0;

        for (Region region : country.getRegions()) {
            if (region != null && region.getPopulation().getTotalPopulation() > 0) {
                int regionPop = region.getPopulation().getTotalPopulation();

                double regionMetricValue = region.getMetricValue(this.type);
                
                sumOfWeightedValue += (regionMetricValue * regionPop);
            }
        }

        this.setValue(sumOfWeightedValue / totalPopulation);
    }

    public double getValue(){return this.value;}

    public double getLastMonthValue(){return this.lastMonthValue;}

    public MetricType getType(){return this.type;}


}

