package io.github.NationArchitect.model.economy;

import main.java.com.NationArchitect.game.model.component.ComponentType;
import main.java.com.NationArchitect.game.model.land.Land;

import java.util.EnumMap;
import java.util.HashMap;

public abstract class Economy {

    private double income;

    private double expanse;

    private double balance;

    private final Tax tax;

    private final HashMap<String, Double> taxRevenues;

    private final EnumMap<ComponentType, Double> componentBudgets;

    public Economy(Tax tax) {
        this.tax = tax;
        taxRevenues = new HashMap<>();
        componentBudgets = new EnumMap<>(ComponentType.class);
    }

    public abstract void calculateIncomeTaxRevenue(Land land);

    public abstract void calculatePropertyTaxRevenue(Land land);

    public abstract void calculateVATRevenue(Land land);

    public abstract void calculateExciseTaxRevenue(Land land);

    public abstract void calculateCorporateTaxRevenue(Land land);

    public abstract void calculateProductionTaxRevenue(Land land);

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

    public HashMap<String, Double> getTaxRevenues() {
        return new HashMap<>(taxRevenues);
    }

    void setTaxRevenue(String tax, double revenue) {
        taxRevenues.put(tax, taxRevenues.getOrDefault(tax, 0.0) + revenue);
    }

    public EnumMap<ComponentType, Double> getComponentBudgets() {
        return new EnumMap<>(componentBudgets);
    }
}
