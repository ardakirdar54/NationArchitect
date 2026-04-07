package com.nationarchitect.game.modules;

import com.badlogic.gdx.math.Rectangle;

public class Region {
    private int id;
    private String name;
    private Rectangle bounds;

    private float population;
    private float security;
    private float industry;
    private float health;
    private float education;
    private float happiness;

    // Yaş dağılımı (0-1 arası oran)
    private float ageGroup0_18  = 0.25f;
    private float ageGroup18_24 = 0.15f;
    private float ageGroup24_65 = 0.45f;
    private float ageGroup65plus= 0.15f;

    // Cinsiyet dağılımı
    private float malePct   = 0.50f;
    private float femalePct = 0.50f;

    // Toplam nüfus sayısı
    private long totalPopulation = 5_000_000;

    public Region(int id, String name, float x, float y, float width, float height) {
        this.id = id;
        this.name = name;
        this.bounds = new Rectangle(x, y, width, height);
        this.population = 50f;
        this.security   = 50f;
        this.industry   = 50f;
        this.health     = 50f;
        this.education  = 50f;
        this.happiness  = 50f;
    }

    public boolean contains(float x, float y) { return bounds.contains(x, y); }

    public int getId()              { return id; }
    public String getName()         { return name; }
    public Rectangle getBounds()    { return bounds; }
    public float getPopulation()    { return population; }
    public float getSecurity()      { return security; }
    public float getIndustry()      { return industry; }
    public float getHealth()        { return health; }
    public float getEducation()     { return education; }
    public float getHappiness()     { return happiness; }
    public long getTotalPopulation(){ return totalPopulation; }
    public float getMalePct()       { return malePct; }
    public float getFemalePct()     { return femalePct; }
    public float getAge0_18()       { return ageGroup0_18; }
    public float getAge18_24()      { return ageGroup18_24; }
    public float getAge24_65()      { return ageGroup24_65; }
    public float getAge65plus()     { return ageGroup65plus; }

    public void setPopulation(float v)      { population = v; }
    public void setSecurity(float v)        { security = v; }
    public void setIndustry(float v)        { industry = v; }
    public void setHealth(float v)          { health = v; }
    public void setEducation(float v)       { education = v; }
    public void setHappiness(float v)       { happiness = v; }
    public void setTotalPopulation(long v)  { totalPopulation = v; }
    public void setMalePct(float v)         { malePct = v; }
    public void setFemalePct(float v)       { femalePct = v; }
}