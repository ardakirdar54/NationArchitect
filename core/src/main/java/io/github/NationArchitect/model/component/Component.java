package io.github.NationArchitect.model.component;

import java.util.ArrayList;
import java.util.EnumMap;
import io.github.NationArchitect.model.Effect.Effect;
import io.github.NationArchitect.model.land.Region;
import io.github.NationArchitect.model.metric.*;
import io.github.NationArchitect.model.product.ProductType;

/**
 * Base class for all city components such as factories, offices, and infrastructure services.
 * It tracks buildings, budgets, performance, land usage, and active effects for the component.
 */
public abstract class Component {

    /** Initial monthly budget value assigned to a newly created component. */
    private final static double INITIAL_MONTHLY_BUDGET = 0;
    /** Default budget multiplier used before any budget adjustment is applied. */
    private final static double BASE_BUDGET_MULTIPLIER = 1;
    /** Base performance value used before buildings and effects modify the component. */
    private final static double BASE_PERFORMANCE = 1;
    /** Initial additive performance multiplier of the component. */
    private final static double INITIAL_PERFORMANCE_MULTIPLIER = 0;
    /** Initial occupied land value for a component with no buildings. */
    private final static double INITIAL_TOTAL_OCCUPIED_LAND = 0;

    /** Type definition of the component. */
    private final ComponentType componentType;

    /** The region component belongs to */
    private final Region region;

    /** Human-readable description of the component. */
    private final String description;

    /** Monthly maintenance cost produced by the component's current buildings. */
    private double baseMonthlyBudget;

    /** Final monthly budget after budget multipliers are applied. */
    private double finalMonthlyBudget;

    /** Multiplier applied to the base budget. */
    private double budgetMultiplier;

    /** The budget percentage of component */
    private double budgetPercentage = 1.0;

    /** Current effective performance of the component. */
    private double performance;

    /** Buildings currently constructed for this component. */
    private final ArrayList<Building> buildings;

    /** Temporary or permanent effects currently affecting this component. */
    private final ArrayList<Effect> activeEffects;

    /** Total land occupied by all buildings of this component. */
    private double totalOccupiedLand;

    /** Metric effects directly produced by this component type. */
    private final EnumMap<MetricType, Double> relatedMetrics;

    /** Effects this component has on other component types. */
    private final EnumMap<ComponentType, Double> relatedComponents;

    /** The demand map of the component. */
    private final EnumMap<ProductType, Integer> demand;

    /**
     * Creates a component with default budget, performance, and land values.
     *
     * @param componentType type of the component to initialize
     */
    public Component(ComponentType componentType, Region region){
        this.region = region;
        this.description = null;
        this.buildings = new ArrayList<>();
        this.activeEffects =  new ArrayList<>();
        this.componentType = componentType;
        this.relatedComponents = componentType.getRelatedComponents();
        this.relatedMetrics = componentType.getRelatedMetrics();
        this.demand = new EnumMap<>(ProductType.class);

        setBaseMonthlyBudget(INITIAL_MONTHLY_BUDGET);
        setFinalMonthlyBudget(INITIAL_MONTHLY_BUDGET);
        setBudgetMultiplier(BASE_BUDGET_MULTIPLIER);
        setPerformance(BASE_PERFORMANCE);
        setTotalOccupiedLand(INITIAL_TOTAL_OCCUPIED_LAND);
    }

    /**
     * Updates the component statistics.
     */
    public void update() {
        calculateBudgetMultiplier();
        calculatePerformance();
        calculateFinalMonthlyBudget();
    }

    /**
     * Adds a building to the component and updates maintenance, land usage, and performance state.
     *
     * @param building building to be constructed
     */
    public void constructBuilding(Building building) {
        if (building == null) {
            return;
        }

        buildings.add(building);
        if (building.getRelatedComponents() != null) {
            for (ComponentType componentType : building.getRelatedComponents().keySet()) {
                relatedComponents.put(componentType,
                    relatedComponents.getOrDefault(componentType, 0.0) + building.getRelatedComponents().get(componentType));
            }
        }
        if (building.getRelatedMetrics() != null) {
            for (MetricType metricType : building.getRelatedMetrics().keySet()) {
                relatedMetrics.put(metricType,
                    relatedMetrics.getOrDefault(metricType, 0.0) + building.getRelatedMetrics().get(metricType));
            }
        }
        if (building.getDemand() != null) {
            for (ProductType productType : building.getDemand().keySet()) {
                demand.put(productType,
                    (int) (demand.getOrDefault(productType, 0) + building.getDemand().get(productType)));
            }
        }
        setBaseMonthlyBudget(getBaseMonthlyBudget() + building.getMaintenanceCost());
        setTotalOccupiedLand(getTotalOccupiedLand() + building.getOccupiedLand());
        update();
    }

    public void restoreBuilding(Building building) {
        constructBuilding(building);
    }

    /**
     * Removes a building from the component and reverses its maintenance, land, and performance impact.
     *
     * @param building building to be destroyed
     */
    public void destroyBuilding(Building building) {
        buildings.remove(building);

        if (building.getRelatedComponents() != null) {
            for (ComponentType componentType : building.getRelatedComponents().keySet()) {
                relatedComponents.put(componentType,
                    Math.max(relatedComponents.get(componentType) - building.getRelatedComponents().get(componentType), 0));
            }
        }
        if (building.getRelatedMetrics() != null) {
            for (MetricType metricType : building.getRelatedMetrics().keySet()) {
                relatedMetrics.put(metricType,
                    Math.max(relatedMetrics.get(metricType) - building.getRelatedMetrics().get(metricType), 0));
            }
        }
        if (building.getDemand() != null) {
            for (ProductType productType : building.getDemand().keySet()) {
                demand.put(productType,
                    (int) Math.max(demand.get(productType) - building.getDemand().get(productType), 0));
            }
        }
        setBaseMonthlyBudget(getBaseMonthlyBudget() - building.getMaintenanceCost());
        setTotalOccupiedLand(getTotalOccupiedLand() - building.getOccupiedLand());
        update();
    }

    /**
     * Sets the current budget multiplier from an external budget percentage value.
     */
    public void calculateBudgetMultiplier() {
        double totalBudgetMultiplier = budgetPercentage - 1;

        for (Effect effect : activeEffects) {
            totalBudgetMultiplier += effect.getRelatedComponents().getOrDefault(componentType, 0.0);
        }

        setBudgetMultiplier(totalBudgetMultiplier);
    }

    /**
     * Calculates the final monthly budget after budget adjustments are applied.
     */
    public void calculateFinalMonthlyBudget() {
        calculateBudgetMultiplier();
        setFinalMonthlyBudget(budgetMultiplier * baseMonthlyBudget);
    }

    /**
     * Recalculates component performance using base performance, building bonuses, budget,
     * and active effect modifiers.
     */
    public void calculatePerformance() {

        double performanceMultiplier = 1;

        for (ComponentType type : ComponentType.values()) {
            performanceMultiplier += region.getComponentEffect(componentType, type);
        }

        for (Effect effect : activeEffects) {
            performanceMultiplier += effect.getRelatedComponents().getOrDefault(componentType, 0.0);
        }

        for (Building b : buildings) {
            performanceMultiplier += b.getRelatedComponents().getOrDefault(componentType, 0.0);
        }

        performanceMultiplier *= budgetPercentage / 100;

        setPerformance(BASE_PERFORMANCE * Math.log(performanceMultiplier + 1));
    }

    /**
     * Adds an effect to the component if it is valid and not already active.
     *
     * @param effect effect to attach
     */
    public void addEffect(Effect effect) {
        if (effect == null || activeEffects.contains(effect)) {
            return;
        }
        activeEffects.add(effect);
        update();
    }

    /**
     * Removes an active effect from the component.
     *
     * @param effect effect to remove
     */
    public void removeEffect(Effect effect) {
        if (effect == null) {
            return;
        }
        activeEffects.remove(effect);
        update();
    }

    /**
     * Returns the real effect this component has on a specific metric.
     *
     * @param metricType metric whose total contribution is requested
     * @return the real contribution value for the given metric
     */
    public double getPerformanceWithType(MetricType metricType) {
        return relatedMetrics.getOrDefault(metricType, 0.0) * performance;
    }

    /**
     * Returns the real effect this component has on a specific component.
     *
     * @param componentType metric whose total contribution is requested
     * @return the real contribution value for the given metric
     */
    public double getPerformanceWithType(ComponentType componentType) {
        return relatedComponents.getOrDefault(componentType, 0.0) * performance;
    }

    /**
     * Returns the component type.
     *
     * @return component type
     */
    public ComponentType getComponentType() {
        return componentType;
    }

    /**
     * Returns the component description.
     *
     * @return description text
     */
    public String getDescription(){
        return description;
    }

    /**
     * Returns the base monthly budget before multipliers.
     *
     * @return base monthly budget
     */
    public double getBaseMonthlyBudget(){
        return baseMonthlyBudget;
    }

    /**
     * Updates the base monthly budget.
     *
     * @param baseMonthlyBudget new base monthly budget value
     */
    void setBaseMonthlyBudget(double baseMonthlyBudget){
        this.baseMonthlyBudget = baseMonthlyBudget;
    }

    /**
     * Returns the final monthly budget after adjustments.
     *
     * @return final monthly budget
     */
    public double getFinalMonthlyBudget(){
        return finalMonthlyBudget;
    }

    /**
     * Updates the final monthly budget value.
     *
     * @param finalMonthlyBudget new final monthly budget
     */
    void setFinalMonthlyBudget(double finalMonthlyBudget){
        this.finalMonthlyBudget = finalMonthlyBudget;
    }

    /**
     * Returns the current budget multiplier.
     *
     * @return budget multiplier
     */
    public double getBudgetMultiplier(){
        return budgetMultiplier;
    }

    /**
     * Updates the budget multiplier.
     *
     * @param budgetMultiplier new budget multiplier
     */
    void setBudgetMultiplier(double budgetMultiplier){
        this.budgetMultiplier = budgetMultiplier;
    }

    /**
     * Returns the current effective performance.
     *
     * @return performance value
     */
    public double getPerformance(){
        return performance;
    }

    /**
     * Updates the current effective performance.
     *
     * @param performance new performance value
     */
    void setPerformance(double performance){
        this.performance = performance;
    }

    /**
     * Returns the total land occupied by this component's buildings.
     *
     * @return total occupied land
     */
    public double getTotalOccupiedLand(){
        return totalOccupiedLand;
    }

    /**
     * Updates the total occupied land value.
     *
     * @param totalOccupiedLand new occupied land total
     */
    void setTotalOccupiedLand(double totalOccupiedLand){
        this.totalOccupiedLand = totalOccupiedLand;
    }

    /**
     * Returns a copy of the building list.
     *
     * @return buildings belonging to this component
     */
    public ArrayList<Building> getBuildings() {
        return new ArrayList<>(buildings);
    }

    /**
     * Returns metric effects related to this component.
     *
     * @return copy of related metric effects
     */
    public EnumMap<MetricType, Double> getRelatedMetrics() {
        return new EnumMap<>(relatedMetrics);
    }

    /**
     * Returns component-to-component effects related to this component.
     *
     * @return copy of related component effects
     */
    public EnumMap<ComponentType, Double> getRelatedComponents() {
        return new EnumMap<>(relatedComponents);
    }

    /**
     * Returns a copy of all currently active effects.
     *
     * @return list of active effects
     */
    public ArrayList<Effect> getActiveEffects() {
        return new ArrayList<>(activeEffects);
    }

    /**
     * Returns the demand for the specified product type.
     *
     * @param productType the product type whose demand is calculated
     * @return the demand for that product type
     */
    public int getProductDemand(ProductType productType) {
        if (demand.containsKey(productType)) {
            return demand.get(productType);
        }
        return 0;
    }


    /**
     * Returns the budget percentage allocated.
     *
     * @return the budget percentage value
     */
    public double getBudgetPercentage() {
        return budgetPercentage;
    }

    /**
     * Sets the budget percentage.
     *
     * @param budgetPercentage the new budget percentage value to set
     */
    public void setBudgetPercentage(double budgetPercentage) {
        this.budgetPercentage = budgetPercentage;
    }
}
