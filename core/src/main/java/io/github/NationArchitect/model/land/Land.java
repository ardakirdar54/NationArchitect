package io.github.NationArchitect.model.land;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.atomic.DoubleAccumulator;

import io.github.NationArchitect.model.economy.Economy;
import io.github.NationArchitect.model.metric.*;
import io.github.NationArchitect.model.population.Population;
import io.github.NationArchitect.model.population.ReadOnlyPopulation;

public abstract class Land {
    private String name;
    private Economy economy;
    protected Population population;
    protected EnumMap<MetricType, Metric> metrics;

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

    public double calculateAttractiveness() throws Exception{
        throw new Exception();
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
