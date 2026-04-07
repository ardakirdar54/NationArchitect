package com.nationarchitect.game.modules;

public class Building {

    public enum Category { EDUCATION, HEALTH, SECURITY, INDUSTRY, INFRASTRUCTURE, TRANSPORTATION }

    // Kıyı bölgeleri
    public static final java.util.Set<String> COASTAL_REGIONS = new java.util.HashSet<>(
        java.util.Arrays.asList("Marmara", "Aegean", "Mediterranean", "Black Sea")
    );

    private String name;
    private String imagePath;
    private int constructionCost;
    private int monthlyBudget;
    private Category category;
    private boolean coastalOnly;

    private float educationEffect;
    private float healthEffect;
    private float happinessEffect;
    private float securityEffect;
    private float industryEffect;

    public Building(String name, String imagePath, int constructionCost, int monthlyBudget,
                    Category category,
                    float educationEffect, float healthEffect,
                    float happinessEffect, float securityEffect, float industryEffect) {
        this(name, imagePath, constructionCost, monthlyBudget, category,
             educationEffect, healthEffect, happinessEffect, securityEffect, industryEffect, false);
    }

    public Building(String name, String imagePath, int constructionCost, int monthlyBudget,
                    Category category,
                    float educationEffect, float healthEffect,
                    float happinessEffect, float securityEffect, float industryEffect,
                    boolean coastalOnly) {
        this.name             = name;
        this.imagePath        = imagePath;
        this.constructionCost = constructionCost;
        this.monthlyBudget    = monthlyBudget;
        this.category         = category;
        this.educationEffect  = educationEffect;
        this.healthEffect     = healthEffect;
        this.happinessEffect  = happinessEffect;
        this.securityEffect   = securityEffect;
        this.industryEffect   = industryEffect;
        this.coastalOnly      = coastalOnly;
    }

    public boolean isAvailableFor(Region region) {
        if (!coastalOnly) return true;
        return COASTAL_REGIONS.contains(region.getName());
    }

    public String getName()             { return name; }
    public String getImagePath()        { return imagePath; }
    public int getConstructionCost()    { return constructionCost; }
    public int getMonthlyBudget()       { return monthlyBudget; }
    public Category getCategory()       { return category; }
    public boolean isCoastalOnly()      { return coastalOnly; }
    public float getEducationEffect()   { return educationEffect; }
    public float getHealthEffect()      { return healthEffect; }
    public float getHappinessEffect()   { return happinessEffect; }
    public float getSecurityEffect()    { return securityEffect; }
    public float getIndustryEffect()    { return industryEffect; }
}