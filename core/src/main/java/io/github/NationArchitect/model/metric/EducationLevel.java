package io.github.NationArchitect.model.metric;

import java.util.EnumMap;

import io.github.NationArchitect.model.component.EducationBuilding;
import io.github.NationArchitect.model.land.Region;
import io.github.NationArchitect.model.population.Age;

public class EducationLevel extends Metric{

    private EnumMap<EducationBuilding, Double> tierFulfillment;
    public EducationLevel(){
        super(MetricType.EDUCATION_LEVEL, 50.0);

        this.tierFulfillment = new EnumMap<>(EducationBuilding.class);
        for(EducationBuilding tier : EducationBuilding.values()){
            this.tierFulfillment.put(tier, 0.0);
        }
    }

    @Override
    public void calculateForRegion(Region region) {
        if(region == null){
            throw new IllegalArgumentException("Region cannot be null! Metric can't be calculated");
        }
        
        int childPopulation = region.getPopulation().getAgeDistribution(Age.CHILD);
        int teenPopulation = region.getPopulation().getAgeDistribution(Age.TEENAGER);
        int youngPopulation = region.getPopulation().getAgeDistribution(Age.YOUNG_ADULT);
        int adultPopulation = region.getPopulation().getAgeDistribution(Age.ADULT);

        int primaryCapacity = region.getEducationBuildingCapacity(EducationBuilding.PRIMARY_SCHOOL);
        int highCapacity = region.getEducationBuildingCapacity(EducationBuilding.HIGH_SCHOOL);
        int universityCapacity = region.getEducationBuildingCapacity(EducationBuilding.UNIVERSITY);
        int researchInstituteCapacity = region.getEducationBuildingCapacity(EducationBuilding.RESEARCH_INSTITUTE);
        int vocationalTrainingCenterCapacity = region.getEducationBuildingCapacity(EducationBuilding.VOCATIONAL_TRAINING_CENTER);

        int vocationalDemand = (int) (adultPopulation * 0.15); 
        int researchDemand = (int) (adultPopulation * 0.05);

        double primaryFulfillment = calculateFulfillment(childPopulation, primaryCapacity);
        double highFulfillment = calculateFulfillment(teenPopulation, highCapacity);
        double universityFulfillment = calculateFulfillment(youngPopulation, universityCapacity);
        double researchFulfillment = calculateFulfillment(researchDemand, researchInstituteCapacity);
        double vocationalFulfillment = calculateFulfillment(vocationalDemand, vocationalTrainingCenterCapacity);

        tierFulfillment.put(EducationBuilding.PRIMARY_SCHOOL, primaryFulfillment);
        tierFulfillment.put(EducationBuilding.HIGH_SCHOOL, highFulfillment);
        tierFulfillment.put(EducationBuilding.UNIVERSITY, universityFulfillment);
        tierFulfillment.put(EducationBuilding.VOCATIONAL_TRAINING_CENTER, vocationalFulfillment);
        tierFulfillment.put(EducationBuilding.RESEARCH_INSTITUTE, researchFulfillment);

        double overallEducation = 
            (primaryFulfillment * 0.35) +    
            (highFulfillment * 0.25) +       
            (universityFulfillment * 0.20) + 
            (vocationalFulfillment * 0.10) + 
            (researchFulfillment * 0.10);    

        this.setValue(overallEducation);

    }

    private double calculateFulfillment(int demand, int supply){
        if (demand == 0) return 100.0;
        double fulfillment = ((double) supply / demand) * 100;
        return Math.min(100.0, fulfillment);
    }
}

