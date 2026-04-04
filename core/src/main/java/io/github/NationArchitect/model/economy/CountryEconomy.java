package io.github.NationArchitect.model.economy;

import main.java.com.NationArchitect.game.model.land.Land;

public class CountryEconomy extends Economy {

    private double treasury;
    private double import_;
    private double export;

    CountryEconomy(Tax tax) {
        super(tax);
    }

    public void calculateIncomeTaxRevenue(Land land) {

    }

    public void calculatePropertyTaxRevenue(Land land) {

    }

    public void calculateVATRevenue(Land land) {

    }

    public void calculateExciseTaxRevenue(Land land) {

    }

    public void calculateCorporateTaxRevenue(Land land) {

    }

    public void calculateProductionTaxRevenue(Land land) {

    }

    public void calculateTaxIncome(Land land) {

    }

    public void calculateTotalIncome(Land land) {

    }

    public void calculateComponentBudgets(Land land) {

    }

    public void calculateTotalExpanses(Land land) {

    }

    public void updateTreasury(Land land) {

    }

    public void calculateImport(Land land) {
    }

    public void calculateExport(Land land) {
    }

    public double getTreasury() {
        return treasury;
    }

    public double getImport(Land land) {
        return import_;
    }

    public double getExport() {
        return export;
    }
}
