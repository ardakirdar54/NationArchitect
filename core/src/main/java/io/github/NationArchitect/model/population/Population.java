package io.github.NationArchitect.model.population;

import java.util.EnumMap;

public class Population implements ReadOnlyPopulation{

    private EnumMap<Age, Integer> ageDistribution;
    private EnumMap<Gender, Integer> genderDistribution;
    private int deaths;
    private int births;
    private int netMigration;

    private static double baseElderlyMortality = 0.08;
    private static double baseBabyMortality = 0.03;
    private static double baseOthersMortality = 0.01;

    public Population(){
        this.ageDistribution = new EnumMap<>(Age.class);
        this.genderDistribution = new EnumMap<>(Gender.class);

        for(Age age : Age.values()){
            this.ageDistribution.put(age, 0);
        }

        for(Gender gender : Gender.values()){
            this.genderDistribution.put(gender, 0);
        }

        this.deaths = 0;
        this.births = 0;
    }

    public void updateLifeCycle(double baseBirthRate, double healthRate){
        int totalAdults = ageDistribution.get(Age.YOUNG_ADULT) + ageDistribution.get(Age.ADULT);
        int newBirths = (int) (totalAdults * baseBirthRate);
        ageDistribution.put(Age.BABY, ageDistribution.get(Age.BABY) + newBirths);

        int maleBirths = newBirths / 2;
        int femaleBirths = newBirths - maleBirths;
        genderDistribution.put(Gender.MALE, genderDistribution.get(Gender.MALE) + maleBirths);
        genderDistribution.put(Gender.FEMALE, genderDistribution.get(Gender.FEMALE) + femaleBirths);

        this.births += newBirths;

        int totalDeathsThisTurn = 0;

        double elderlyMortality = baseElderlyMortality / healthRate;
        int elderDeaths = (int) (ageDistribution.get(Age.ELDERLY) * elderlyMortality);
        ageDistribution.put(Age.ELDERLY, ageDistribution.get(Age.ELDERLY) - elderDeaths);
        totalDeathsThisTurn += elderDeaths;

        double adultMortality = baseOthersMortality / healthRate;
        int adultDeaths = (int) (ageDistribution.get(Age.ADULT) * adultMortality);
        ageDistribution.put(Age.ADULT, ageDistribution.get(Age.ADULT) - adultDeaths);
        totalDeathsThisTurn += adultDeaths;

        double youngAdultMortality = baseOthersMortality / healthRate;
        int youngAdultDeaths = (int) (ageDistribution.get(Age.YOUNG_ADULT) * youngAdultMortality);
        ageDistribution.put(Age.YOUNG_ADULT, ageDistribution.get(Age.YOUNG_ADULT) - youngAdultDeaths);
        totalDeathsThisTurn += youngAdultDeaths;

        double childMortality = baseOthersMortality / healthRate;
        int childDeaths = (int) (ageDistribution.get(Age.CHILD) * childMortality);
        ageDistribution.put(Age.CHILD, ageDistribution.get(Age.CHILD) - childDeaths);
        totalDeathsThisTurn += childDeaths;

        double babyMortality = baseBabyMortality / healthRate;
        int babyDeaths = (int) (ageDistribution.get(Age.BABY) * babyMortality);
        ageDistribution.put(Age.BABY, ageDistribution.get(Age.BABY) - babyDeaths);
        totalDeathsThisTurn += babyDeaths;

        int maleDeaths = Math.min(totalDeathsThisTurn / 2, genderDistribution.get(Gender.MALE));
        int femaleDeaths = Math.min(totalDeathsThisTurn - maleDeaths, genderDistribution.get(Gender.FEMALE));
        genderDistribution.put(Gender.MALE, genderDistribution.get(Gender.MALE) - maleDeaths);
        genderDistribution.put(Gender.FEMALE, genderDistribution.get(Gender.FEMALE) - femaleDeaths);

        this.deaths = totalDeathsThisTurn;
    }

    public Population setAgeDistribution(EnumMap<Age, Integer> ageDistribution){
        this.ageDistribution = ageDistribution;
        return this;
    }

    public Population setGenderDistribution(EnumMap<Gender, Integer> genderDistribution){
        this.genderDistribution = genderDistribution;
        return this;
    }

    public Population setBirths(int births) {
        this.births = births;
        return this;
    }

    public Population setDeaths(int deaths) {
        this.deaths = deaths;
        return this;
    }

    public int getTotalPopulation(){
        int total = 0;
        for(int amount : ageDistribution.values()){
            total += amount;
        }
        return total;
    }

    public int getWorkingAgePopulation(){
        return this.ageDistribution.get(Age.YOUNG_ADULT) + this.ageDistribution.get(Age.ADULT);
    }

    @Override
    public int getAgeDistribution(Age type) {
        return this.ageDistribution.get(type);
    }

    public int getBirths(){
        return this.births;
    }
    
    public int getDeaths(){
        return this.deaths;
    }
    
    public EnumMap<Age, Integer> getAgeDistribution(){
        return ageDistribution;
    }

    public EnumMap<Gender, Integer> getGenderDistribution() {
        return genderDistribution;
    }

    public int getNetMigration() { return netMigration; }

    public void processMigration(int migrationVolume) {
        this.netMigration = migrationVolume;
        if (migrationVolume == 0) return;

        boolean isImmigration = migrationVolume > 0;
        int totalPeopleToMove = Math.abs(migrationVolume);

        double youngAdultRatio = 0.50; 
        double adultRatio = 0.30;      
        double childRatio = 0.10;      
        double teenRatio = 0.05;       
        double babyRatio = 0.03;       
        double elderlyRatio = 0.02;    

        moveDemographic(Age.YOUNG_ADULT, (int)(totalPeopleToMove * youngAdultRatio), isImmigration);
        moveDemographic(Age.ADULT, (int)(totalPeopleToMove * adultRatio), isImmigration);
        moveDemographic(Age.CHILD, (int)(totalPeopleToMove * childRatio), isImmigration);
        moveDemographic(Age.TEENAGER, (int)(totalPeopleToMove * teenRatio), isImmigration);
        moveDemographic(Age.BABY, (int)(totalPeopleToMove * babyRatio), isImmigration);
        moveDemographic(Age.ELDERLY, (int)(totalPeopleToMove * elderlyRatio), isImmigration);

        
        int maleMove = totalPeopleToMove / 2;
        int femaleMove = totalPeopleToMove - maleMove;

        if (isImmigration) {
            genderDistribution.put(Gender.MALE, genderDistribution.get(Gender.MALE) + maleMove);
            genderDistribution.put(Gender.FEMALE, genderDistribution.get(Gender.FEMALE) + femaleMove);
        } else {
            genderDistribution.put(Gender.MALE, Math.max(0, genderDistribution.get(Gender.MALE) - maleMove));
            genderDistribution.put(Gender.FEMALE, Math.max(0, genderDistribution.get(Gender.FEMALE) - femaleMove));
        }
    }

    private void moveDemographic(Age ageGroup, int amount, boolean isImmigration) {
        int currentAmount = ageDistribution.get(ageGroup);
        if (isImmigration) {
            ageDistribution.put(ageGroup, currentAmount + amount);
        } else {
            ageDistribution.put(ageGroup, Math.max(0, currentAmount - amount));
        }
    }
}
