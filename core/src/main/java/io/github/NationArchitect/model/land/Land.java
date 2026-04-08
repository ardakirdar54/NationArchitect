package io.github.NationArchitect.model.land;

import java.util.EnumMap;

import io.github.NationArchitect.model.Effect.Policy;
import io.github.NationArchitect.model.economy.Economy;
import io.github.NationArchitect.model.metric.*;
import io.github.NationArchitect.model.population.Population;
import io.github.NationArchitect.model.population.ReadOnlyPopulation;

public abstract class Land {
    private String name;
    private Economy economy;
    protected Population population;
    protected EnumMap<MetricType, Metric> metrics;

    Land() {
        
        this.metrics = new EnumMap<>(MetricType.class);
        this.metrics.put(MetricType.HAPPINESS, new Happiness());
        this.metrics.put(MetricType.UNEMPLOYMENT, new Unemployment());
        this.metrics.put(MetricType.CRIME_RATE, new CrimeRate());
        this.metrics.put(MetricType.EDUCATION_LEVEL, new EducationLevel());
        this.metrics.put(MetricType.HEALTH_RATE, new HealthRate());
        this.metrics.put(MetricType.STABILITY, new Stability());
    }

    Land(String name, Economy economy, Population population){
        this.name = name;
        this.economy = economy;
        this.population = population;

        //Defining metrics
        metrics = new EnumMap<>(MetricType.class);
        metrics.put(MetricType.HAPPINESS, new Happiness());
        metrics.put(MetricType.UNEMPLOYMENT, new Unemployment());
        metrics.put(MetricType.CRIME_RATE, new CrimeRate());
        metrics.put(MetricType.EDUCATION_LEVEL, new EducationLevel());
        metrics.put(MetricType.HEALTH_RATE, new HealthRate());

    }

    public abstract void implementPolicy(Policy policy);

    public abstract void cancelPolicy(Policy policy);

    public double calculateAttractiveness() {
        double happiness = this.getMetricValue(MetricType.HAPPINESS);
        double stability = this.getMetricValue(MetricType.STABILITY);
        double unemployment = this.getMetricValue(MetricType.UNEMPLOYMENT);
        double crimeRate = this.getMetricValue(MetricType.CRIME_RATE);


        double pullFactors = (happiness * 0.4) + (stability * 0.3);

        double pushFactors = ((100.0 - unemployment) * 0.2) + ((100.0 - crimeRate) * 0.1);

        double attractiveness = pullFactors + pushFactors;

        return Math.max(0.0, Math.min(100.0, attractiveness));
    }

    public void updateLastMonthValues() {
        for (Metric metric : this.metrics.values()) {
            metric.updateLastMonthValue();
        }
    }

    public abstract void update();

    public String getName(){return this.name;}

    public Economy getEconomy(){return this.economy;}

    public ReadOnlyPopulation getPopulation(){return this.population;}

    public double getMetricValue(MetricType type) {
        if (this.metrics.containsKey(type)) {
            return this.metrics.get(type).getValue();
        }
        return 0.0;
    }

    public double getLastMonthMetricValue(MetricType type){
        if (this.metrics.containsKey(type)) {
            return this.metrics.get(type).getLastMonthValue();
        }
        return 0.0;
    }



}
