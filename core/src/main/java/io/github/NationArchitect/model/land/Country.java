package io.github.NationArchitect.model.land;

import java.util.EnumMap;

import io.github.NationArchitect.model.Effect.Policy;
import io.github.NationArchitect.model.economy.Economy;
import io.github.NationArchitect.model.metric.*;
import io.github.NationArchitect.model.population.Age;
import io.github.NationArchitect.model.population.Gender;
import io.github.NationArchitect.model.population.Population;

public class Country extends Land {

    private Region[] regions;

    public Country(String name, Economy economy, Population population) {
        super(name, economy, population);
        this.regions = new Region[10];
        
    }

    /**
     * Adds the policy to activePolicies list for all of the regions.
     *
     * @param policy the policy will be implemented for all of the regions
     */
    @Override
    public void implementPolicy(Policy policy) {
        for (Region region : regions) {
            region.implementPolicy(policy);
        }
    }

    @Override
    public void cancelPolicy(Policy policy) {
        for (Region region : regions) {
            region.cancelPolicy(policy);
        }
    }

    @Override
    public void update() {
        for (Region region : regions) {
            if (region != null) {
                region.update(); 
            }
        }

        processExternalMigration();
        
        processInternalMigration();

        for (Metric metric : this.metrics.values()) {
            metric.calculateForCountry(this);
        }
    }

    @Override
    public void updateLastMonthValues() {
        super.updateLastMonthValues();

        if (regions != null) {
            for (Region region : regions) {
                if (region != null) {
                    region.updateLastMonthValues();
                }
            }
        }
    }

    public void processInternalMigration() {
        double totalAttractiveness = 0;
        int validRegionsCount = 0;

        
        for (Region region : regions) {
            if (region != null) {
                totalAttractiveness += region.calculateAttractiveness();
                validRegionsCount++;
            }
        }

        if (validRegionsCount == 0) return;
        double averageAttractiveness = totalAttractiveness / validRegionsCount;

        int internalMigrantsPool = 0;

        for (Region region : regions) {
            if (region != null) {
                double attractiveness = region.calculateAttractiveness();
                if (attractiveness < averageAttractiveness) {
                    
                    double difference = averageAttractiveness - attractiveness;
                    
                    double migrationRate = (difference / 100.0) * 0.02; 
                    int leavingMigrants = (int) (region.getPopulation().getTotalPopulation() * migrationRate);

                    region.population.processMigration(-leavingMigrants);
                    internalMigrantsPool += leavingMigrants;
                }
            }
        }

        double totalPositiveDifference = 0;
        for (Region region : regions) {
            if (region != null) {
                double attractiveness = region.calculateAttractiveness();
                if (attractiveness > averageAttractiveness) {
                    totalPositiveDifference += (attractiveness - averageAttractiveness);
                }
            }
        }

        if (totalPositiveDifference > 0 && internalMigrantsPool > 0) {
            for (Region region : regions) {
                if (region != null) {
                    double attractiveness = region.calculateAttractiveness();
                    if (attractiveness > averageAttractiveness) {
                        
                        double share = (attractiveness - averageAttractiveness) / totalPositiveDifference;
                        int arrivingMigrants = (int) (internalMigrantsPool * share);
                        
                        region.population.processMigration(arrivingMigrants);
                    }
                }
            }
        }
    }

    public void processExternalMigration() {
        double countryAttractiveness = this.calculateAttractiveness();
        
        double migrationMultiplier = (countryAttractiveness - 50.0) / 100.0;
        
        int totalCountryPop = this.getPopulation().getTotalPopulation();
        
        int externalMigrationPool = (int) (totalCountryPop * migrationMultiplier * 0.01);

        if (externalMigrationPool == 0) return;

        if (externalMigrationPool > 0) {
            
            double totalAttractivenessSum = 0;
            for (Region region : regions) {
                if (region != null) totalAttractivenessSum += region.calculateAttractiveness();
            }

            for (Region region : regions) {
                if (region != null) {
                    
                    double share = region.calculateAttractiveness() / totalAttractivenessSum;
                    int arrivingMigrants = (int) (externalMigrationPool * share);
                    region.population.processMigration(arrivingMigrants);
                }
            }

        } else {
            
            int peopleLeaving = Math.abs(externalMigrationPool);
            double totalUnattractivenessSum = 0;
            
            
            for (Region region : regions) {
                if (region != null) totalUnattractivenessSum += (100.0 - region.calculateAttractiveness());
            }

            for (Region region : regions) {
                if (region != null) {
                    double unattractiveness = 100.0 - region.calculateAttractiveness();
                    
                    double share = unattractiveness / totalUnattractivenessSum;
                    int leavingMigrants = (int) (peopleLeaving * share);
                    
                    region.population.processMigration(-leavingMigrants);
                }
            }
        }
    }

    public void calculatePopulation(){
        EnumMap<Age, Integer> ageDistribution = new EnumMap<>(Age.class);
        EnumMap<Gender, Integer> genderDistribution = new EnumMap<>(Gender.class);

        for(Age age : Age.values()){
            int total = 0;
            for(Region region : this.regions){
                total += region.population.getAgeDistribution().get(age);
            }
            ageDistribution.put(age, total);
        }
        for(Gender gender : Gender.values()){
            int total = 0;
            for(Region region : this.regions){
                total += region.population.getGenderDistribution().get(gender);
            }
            genderDistribution.put(gender, total);
        }
        this.population.setAgeDistribution(ageDistribution);
        this.population.setGenderDistribution(genderDistribution);

    }

    public Region[] getRegions() {
        return this.regions;
    }
}
