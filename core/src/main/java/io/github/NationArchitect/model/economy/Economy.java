package io.github.NationArchitect.model.economy;



import io.github.NationArchitect.model.component.ComponentType;
import io.github.NationArchitect.model.land.Land;

import java.util.EnumMap;

/**
 * Base type for regional and country-level economic calculations.
 */
public abstract class Economy {

    /** Total calculated income. */
    private double income;

    /** Total calculated expenses. */
    private double expanse;

    /** Current balance derived from income and expenses. */
    private double balance;

    /** Active tax configuration used by the economy. */
    private final Tax tax;

    /** Calculated tax revenues by tax type. */
    private final EnumMap<TaxType, Double> taxRevenues;

    /** Allocated budgets by component type. */
    private final EnumMap<ComponentType, Double> componentBudgets;

    /**
     * Creates an economy with empty revenue and budget maps.
     *
     * @param tax tax configuration used for calculations
     */
    public Economy(Tax tax) {
        this.tax = tax;
        taxRevenues = new EnumMap<>(TaxType.class);
        componentBudgets = new EnumMap<>(ComponentType.class);
    }

    /**
     * Calculates total tax income for the given land.
     *
     * @param land target land
     * @return total tax income
     */
    public abstract double calculateTaxIncome(Land land);

    /**
     * Calculates and stores total income for the given land.
     *
     * @param land target land
     */
    public abstract void calculateTotalIncome(Land land);

    /**
     * Calculates and stores component budgets for the given land.
     *
     * @param land target land
     */
    public abstract void getComponentBudgets(Land land);

    /**
     * Calculates and stores total expenses for the given land.
     *
     * @param land target land
     */
    public abstract void calculateTotalExpanses(Land land);

    /**
     * Recalculates the balance from current income and expenses.
     */
    public void calculateBalance(Land land) {
        calculateTotalIncome(land);
        calculateTotalExpanses(land);
        balance = getIncome() - getExpanse();
    }

    /**
     * Returns total income.
     *
     * @return income
     */
    public double getIncome() {
        return income;
    }

    /**
     * Updates total income.
     *
     * @param income new income value
     */
    void setIncome(double income) {
        this.income = income;
    }

    /**
     * Returns total expenses.
     *
     * @return expenses
     */
    public double getExpanse() {
        return expanse;
    }

    /**
     * Updates total expenses.
     *
     * @param expanse new expense value
     */
    void setExpanse(double expanse) {
        this.expanse = expanse;
    }

    /**
     * Returns current balance.
     *
     * @return balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Returns the tax configuration.
     *
     * @return tax configuration
     */
    public Tax getTax() {
        return tax;
    }

    /**
     * Returns calculated tax revenues.
     *
     * @return copy of tax revenues
     */
    public EnumMap<TaxType, Double> getTaxRevenues() {
        return new EnumMap<>(taxRevenues);
    }

    /**
     * Returns calculated component budgets.
     *
     * @return copy of component budgets
     */
    public EnumMap<ComponentType, Double> getComponentBudgets() {
        return new EnumMap<>(componentBudgets);
    }

    /**
     * Clears all stored tax revenues.
     */
    void clearTaxRevenues() {
        taxRevenues.clear();
    }

    /**
     * Clears all stored component budgets.
     */
    void clearComponentBudgets() {
        componentBudgets.clear();
    }

    /**
     * Sets the budget value for a component type.
     *
     * @param componentType component type to update
     * @param budget budget value
     */
    void setComponentBudgetByType(ComponentType componentType, double budget) {
        componentBudgets.put(componentType, budget);
    }

    /**
     * Stores a component budget only if it is not already present.
     *
     * @param componentType component type to add
     * @param budget budget value
     */
    void putComponentBudget(ComponentType componentType, double budget) {
        if (!componentBudgets.containsKey(componentType)) {
            componentBudgets.put(componentType, budget);
        }
    }

    /**
     * Stores a tax revenue only if it is not already present.
     *
     * @param taxType tax type to add
     * @param revenue revenue value
     */
    void putTaxRevenue(TaxType taxType, double revenue) {
        if (!taxRevenues.containsKey(taxType)) {
            taxRevenues.put(taxType, revenue);
        }
    }

    public void restoreSummary(double income, double expanse, double balance) {
        setIncome(income);
        setExpanse(expanse);
        this.balance = balance;
    }

    public void restoreTaxRevenues(EnumMap<TaxType, Double> revenues) {
        clearTaxRevenues();
        if (revenues != null) {
            taxRevenues.putAll(revenues);
        }
    }

    public void restoreComponentBudgets(EnumMap<ComponentType, Double> budgets) {
        clearComponentBudgets();
        if (budgets != null) {
            componentBudgets.putAll(budgets);
        }
    }
}
