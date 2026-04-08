package io.github.NationArchitect.model.economy;

import io.github.NationArchitect.model.component.ComponentType;
import io.github.NationArchitect.model.land.Country;
import io.github.NationArchitect.model.land.Land;
import io.github.NationArchitect.model.land.Region;
import io.github.NationArchitect.model.product.ProductType;

import java.util.EnumMap;

/**
 * Economy implementation that aggregates regional values at country level.
 */
public class CountryEconomy extends Economy {

    /** Current treasury balance of the country. */
    private double treasury;
    /** Total import spending accumulated by the country. */
    private double import_;
    /** Total export income accumulated by the country. */
    private double export;

    /**
     * Creates a country economy.
     *
     * @param tax tax configuration used for calculations
     */
    CountryEconomy(Tax tax) {
        super(tax);
    }

    /** {@inheritDoc} */
    public double calculateTaxIncome(Land land) {
        calculateTaxRevenues(land);

        double revenue = 0;

        EnumMap<TaxType, Double> taxRevenues = getTaxRevenues();

        for (TaxType taxType : taxRevenues.keySet()) {
            revenue += taxRevenues.get(taxType);
        }

        return revenue;
    }

    /** {@inheritDoc} */
    public void calculateTotalIncome(Land land) {
        calculateTaxIncome(land);

        double totalIncome = export + calculateTaxIncome(land);

        setIncome(totalIncome);
    }

    /** {@inheritDoc} */
    public void getComponentBudgets(Land land) {
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

    /** {@inheritDoc} */
    public void calculateTotalExpanses(Land land) {
        getComponentBudgets(land);

        double totalExpanse = import_;
        for (double budget : getComponentBudgets().values()) {
            totalExpanse += budget;
        }
        setExpanse(totalExpanse);
    }

    /**
     * Updates treasury by recalculating income, expenses, and balance.
     *
     * @param land country land
     */
    public void update(Land land) {
        Region[] regions = getRegions(land);
        for (Region region : regions) {
            if (region == null) {
                continue;
            }
            region.getEconomy().calculateBalance(region);
        }
        calculateBalance(land);
        treasury += getBalance();
    }

    /**
     * Calculates total import cost from regional product deficits.
     *
     * @param land country land
     */
    public void calculateImport(Land land) {
        double import_ = 0;

        for (ProductType productType : ProductType.values()) {

            for (Region region : getRegions(land)) {
                if (region == null) {
                    continue;
                }

                import_ += ((RegionEconomy)region.getEconomy()).getProductDeficit(productType) * productType.getPurchasePrice();
            }
        }

        this.import_ += import_;
    }

    /**
     * Calculates total export income from regional product surpluses.
     *
     * @param land country land
     */
    public void calculateExport(Land land) {
        double export = 0;

        for (ProductType productType : ProductType.values()) {

            for (Region region : getRegions(land)) {
                if (region == null) {
                    continue;
                }

                export += ((RegionEconomy)region.getEconomy()).getProductSurplus(productType) * productType.getSalePrice();
            }
        }

        this.export += export;
    }

    /**
     * Returns treasury balance.
     *
     * @return treasury balance
     */
    public double getTreasury() {
        return treasury;
    }

    /**
     * Returns total import cost.
     *
     * @return import cost
     */
    public double getImport() {
        return import_;
    }

    /**
     * Returns total export income.
     *
     * @return export income
     */
    public double getExport() {
        return export;
    }

    /**
     * Returns the regions of the given country land.
     *
     * @param land country land
     * @return country regions
     */
    private Region[] getRegions(Land land) {
        return ((Country) land).getRegions();
    }

    /**
     * Calculates tax revenues for all tax types by summing regional values.
     *
     * @param land country land
     */
    public void calculateTaxRevenues(Land land) {
        clearTaxRevenues();
        for (TaxType taxType : TaxType.values()) {
            double totalRevenue = 0;
            for (Region region : getRegions(land)) {
                if (region == null) {
                    continue;
                }
                region.getEconomy().calculateTaxIncome(region);
                totalRevenue += region.getEconomy().getTaxRevenues().getOrDefault(taxType, 0.0);
            }
            putTaxRevenue(taxType, totalRevenue);
        }
    }
}
