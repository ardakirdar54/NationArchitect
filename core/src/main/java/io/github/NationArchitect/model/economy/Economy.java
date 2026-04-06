package io.github.NationArchitect.model.economy;



import io.github.NationArchitect.model.component.ComponentType;
import io.github.NationArchitect.model.land.Land;

import java.util.EnumMap;

public abstract class Economy {

    private double income;

    private double expanse;

    private double balance;

    private final Tax tax;

    private final EnumMap<TaxType, Double> taxRevenues;

    private final EnumMap<ComponentType, Double> componentBudgets;

    public Economy(Tax tax) {
        this.tax = tax;
        taxRevenues = new EnumMap<>(TaxType.class);
        componentBudgets = new EnumMap<>(ComponentType.class);
    }

    public abstract void calculateTaxIncome(Land land);

    public abstract void calculateTotalIncome(Land land);

    public abstract void calculateComponentBudgets(Land land);

    public abstract void calculateTotalExpanses(Land land);

    public void calculateBalance() {
        balance = getIncome() - getExpanse();
    }

    public double getIncome() {
        return income;
    }

    void setIncome(double income) {
        this.income = income;
    }

    public double getExpanse() {
        return expanse;
    }

    void setExpanse(double expanse) {
        this.expanse = expanse;
    }

    public double getBalance() {
        return balance;
    }

    public Tax getTax() {
        return tax;
    }

    public EnumMap<TaxType, Double> getTaxRevenues() {
        return new EnumMap<>(taxRevenues);
    }

    public EnumMap<ComponentType, Double> getComponentBudgets() {
        return new EnumMap<>(componentBudgets);
    }

    void clearTaxRevenues() {
        taxRevenues.clear();
    }

    void setTaxRevenueByType(TaxType taxType, double revenue) {
        taxRevenues.put(taxType, revenue);
    }

    void clearComponentBudgets() {
        componentBudgets.clear();
    }

    void setComponentBudgetByType(ComponentType componentType, double budget) {
        componentBudgets.put(componentType, budget);
    }
}
