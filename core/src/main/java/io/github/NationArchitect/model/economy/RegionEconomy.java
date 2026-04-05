package io.github.NationArchitect.model.economy;

import io.github.NationArchitect.model.land.Land;
import io.github.NationArchitect.model.product.Product;
import io.github.NationArchitect.model.product.ProductType;

import java.util.EnumMap;

public class RegionEconomy extends Economy {

    EnumMap<ProductType, Product> production;

    EnumMap<ProductType, Product> demand;

    public RegionEconomy(Tax tax) {
        super(tax);
        production = new EnumMap<>(ProductType.class);
        demand = new EnumMap<>(ProductType.class);
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

    public void calculateProduction(Land land) {
    }

    public void calculateDemand(Land land) {
    }
}
