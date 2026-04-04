package io.github.NationArchitect.model.land;

import java.util.EnumMap;
import java.util.Map;

import io.github.NationArchitect.model.economy.Economy;
import io.github.NationArchitect.model.metric.*;
import io.github.NationArchitect.model.population.Population;

public abstract class Land {
    private String name;
    private Economy economy;
    private Population population;
    private EnumMap<MetricType, Metric> metrics;

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

    public Population getPopulation(){return this.population;}

    public Happiness getHappiness(){return (Happiness) this.metrics.get(MetricType.HAPPINESS);}

    public Unemployment getUnemployment(){return (Unemployment) this.metrics.get(MetricType.UNEMPLOYMENT);}

    public CrimeRate getCrimeRate(){return (CrimeRate) this.metrics.get(MetricType.CRIME_RATE);}

    public EducationLevel getEducationLevel(){return (EducationLevel) this.metrics.get(MetricType.EDUCATION_LEVEL);}

    public HealthRate getHealthRate(){return (HealthRate) this.metrics.get(MetricType.HEALTH_RATE);}

    public EnumMap<MetricType, Metric> getMetrics(){return this.metrics;}

}
