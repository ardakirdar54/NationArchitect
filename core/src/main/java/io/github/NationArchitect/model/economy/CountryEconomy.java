package io.github.NationArchitect.model.economy;

import io.github.NationArchitect.model.component.ComponentType;
import io.github.NationArchitect.model.land.Country;
import io.github.NationArchitect.model.land.Land;
import io.github.NationArchitect.model.land.Region;

public class CountryEconomy extends Economy {

    private double treasury;
    private double import_;
    private double export;

    CountryEconomy(Tax tax) {
        super(tax);
    }

    public void calculateTaxIncome(Land land) {
        calculateTaxRevenues(land);
    }

    public void calculateTotalIncome(Land land) {
        calculateTaxIncome(land);

        double totalIncome = 0;
        for (double revenue : getTaxRevenues().values()) {
            totalIncome += revenue;
        }
        setIncome(totalIncome);
    }

    public void calculateComponentBudgets(Land land) {
        clearComponentBudgets();

        for (ComponentType componentType : ComponentType.values()) {
            double totalBudget = export;
            for (Region region : getRegions(land)) {
                if (region == null) {
                    continue;
                }
                totalBudget += region.getEconomy().getComponentBudgets().getOrDefault(componentType, 0.0);
            }
            setComponentBudgetByType(componentType, totalBudget);
        }
    }

    public void calculateTotalExpanses(Land land) {
        calculateComponentBudgets(land);

        double totalExpanse = import_;
        for (double budget : getComponentBudgets().values()) {
            totalExpanse += budget;
        }
        setExpanse(totalExpanse);
    }

    public void updateTreasury(Land land) {
        calculateTotalIncome(land);
        calculateTotalExpanses(land);
        calculateBalance();
        treasury += getBalance();
    }

    public void calculateImport(Land land) {
    }

    public void calculateExport(Land land) {
    }

    public double getTreasury() {
        return treasury;
    }

    public double getImport() {
        return import_;
    }

    public double getExport() {
        return export;
    }

    private Region[] getRegions(Land land) {
        return ((Country) land).getRegions();
    }

    public void calculateTaxRevenues(Land land) {
        clearTaxRevenues();

        for (TaxType taxType : TaxType.values()) {
            double totalRevenue = 0;
            for (Region region : getRegions(land)) {
                if (region == null) {
                    continue;
                }
                totalRevenue += region.getEconomy().getTaxRevenues().getOrDefault(taxType, 0.0);
            }
            setTaxRevenueByType(taxType, totalRevenue);
        }
    }
}
