package io.github.NationArchitect.model.component;

import java.util.ArrayList;
import java.util.EnumMap;
import io.github.NationArchitect.model.metric.*;

public abstract class Component {

    private final static double INITIAL_MONTHLY_BUDGET = 0;
    private final static double BASE_BUDGET_MULTIPLIER = 1;
    private final static double BASE_PERFORMANCE = 1;
    private final static double INITIAL_PERFORMANCE_MULTIPLIER = 0;
    private final static double INITIAL_TOTAL_OCCUPIED_LAND = 0;

    private final ComponentType ComponentType;

    private final String description;

    private double baseMonthlyBudget;

    private double finalMonthlyBudget;

    private double budgetMultiplier;

    private double performance;

    private double performanceMultiplier;

    private final ArrayList<Building> buildings;

    private final ArrayList<Effect> activeEffects;

    private double totalOccupiedLand;

    private final EnumMap<MetricType, Double> relatedMetrics;

    private final EnumMap<ComponentType, Double> relatedComponents;

    Component(ComponentType componentType){
        this.description = null;
        this.buildings = new ArrayList<>();
        this.activeEffects =  new ArrayList<>();
        this.ComponentType = componentType;
        this.relatedComponents = componentType.getRelatedComponents();
        this.relatedMetrics = componentType.getRelatedMetrics();
        setBaseMonthlyBudget(INITIAL_MONTHLY_BUDGET);
        setFinalMonthlyBudget(INITIAL_MONTHLY_BUDGET);
        setBudgetMultiplier(BASE_BUDGET_MULTIPLIER);
        setPerformance(BASE_PERFORMANCE);
        setPerformanceMultiplier(INITIAL_PERFORMANCE_MULTIPLIER);
        setTotalOccupiedLand(INITIAL_TOTAL_OCCUPIED_LAND);
    }

    public void constructBuilding(Building building) {
        if (building == null) {
            return;
        }
        if (!building.getClass().getSimpleName().equals(this.getClass().getSimpleName() + "Building")) {
            throw new IllegalBuildingTypeException(building.getType(), this);
        }

        buildings.add(building);
        setBaseMonthlyBudget(getBaseMonthlyBudget() + building.getMaintenanceCost());
        setTotalOccupiedLand(getTotalOccupiedLand() + building.getOccupiedLand());
        setPerformanceMultiplier(getPerformanceMultiplier() + building.getPerformanceMultiplier());
    }

    public void destroyBuilding(Building building) {
        getBuildings().remove(building);
        setBaseMonthlyBudget(getBaseMonthlyBudget() + building.getMaintenanceCost());
        setTotalOccupiedLand(getTotalOccupiedLand() - building.getOccupiedLand());
        setPerformance(getPerformance() / (1 + building.getPerformanceMultiplier()));
    }

    public void calculateBudgetMultiplier(double budgetPercentage) {

        setBudgetMultiplier(budgetPercentage);
    }

    public void calculateFinalMonthlyBudget(double budgetPercentage) {

    }

    public void calculatePerformance(double budgetPercentage) {
        setPerformance(getPerformance() * budgetPercentage);
    }

    public void affectMetrics() {

    }

    public void affectComponents() {
    }

    //public void addEffect(Effect effect)

    //public void removeEffect(Effect effect)

    public double calculateEffectMultiplier() {
        return 0;
    }

    public ComponentType getComponentType() {
        return ComponentType;
    }

    public String getDescription(){
        return description;
    }

    public double getBaseMonthlyBudget(){
        return baseMonthlyBudget;
    }

    public void setBaseMonthlyBudget(double baseMonthlyBudget){
        this.baseMonthlyBudget = baseMonthlyBudget;
    }

    public double getFinalMonthlyBudget(){
        return finalMonthlyBudget;
    }

    public void setFinalMonthlyBudget(double finalMonthlyBudget){
        this.finalMonthlyBudget = finalMonthlyBudget;
    }

    public double getBudgetMultiplier(){
        return budgetMultiplier;
    }

    public void setBudgetMultiplier(double budgetMultiplier){
        this.budgetMultiplier = budgetMultiplier;
    }

    public double getPerformance(){
        return performance;
    }

    public void setPerformance(double performance){
        this.performance = performance;
    }

    public double getTotalOccupiedLand(){
        return totalOccupiedLand;
    }

    public void setTotalOccupiedLand(double totalOccupiedLand){
        this.totalOccupiedLand = totalOccupiedLand;
    }

    public ArrayList<Building> getBuildings() {
        return new ArrayList<>(buildings);
    }

    public EnumMap<MetricType, Double> getRelatedMetrics() {
        return new EnumMap<>(relatedMetrics);
    }

    public EnumMap<ComponentType, Double> getRelatedComponents() {
        return new EnumMap<>(relatedComponents);
    }

    public double getPerformanceMultiplier() {
        return performanceMultiplier;
    }

    public void setPerformanceMultiplier(double performanceMultiplier) {
        this.performanceMultiplier = performanceMultiplier;
    }
}
