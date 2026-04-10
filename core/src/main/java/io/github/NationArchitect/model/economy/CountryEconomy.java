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

    /** Starting treasury for a newly created country economy. */
    private static final double INITIAL_TREASURY = 100_000.0;

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
    public CountryEconomy(Tax tax) {
        super(tax);
        this.treasury = INITIAL_TREASURY;
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
        double totalIncome = export + calculateTaxIncome(land);

        setIncome(totalIncome);
    }

    /** {@inheritDoc} */
    public void getComponentBudgets(Land land) {
        clearComponentBudgets();

        for (ComponentType componentType : ComponentType.values()) {
            double totalBudget = 0;
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
        calculateImport(land);
        calculateExport(land);
        calculateBalance(land);
    }

    /**
     * Applies a fraction of the current monthly cashflow to the treasury.
     *
     * @param monthFraction fraction of one in-game month to apply
     */
    public void applyMonthlyCashflow(double monthFraction) {
        treasury += (getIncome() - getExpanse()) * monthFraction;
    }

    /**
     * Applies a direct treasury delta.
     *
     * @param amount positive income or negative expense to apply
     */
    public void applyTreasuryDelta(double amount) {
        treasury += amount;
    }

    /**
     * Returns whether the country can afford the given amount.
     *
     * @param amount amount to check
     * @return true if treasury is sufficient
     */
    public boolean canAfford(double amount) {
        return treasury >= Math.max(0.0, amount);
    }

    /**
     * Spends treasury if enough funds are available.
     *
     * @param amount amount to spend
     * @return true when the spend succeeded
     */
    public boolean spend(double amount) {
        double normalizedAmount = Math.max(0.0, amount);
        if (!canAfford(normalizedAmount)) {
            return false;
        }
        treasury -= normalizedAmount;
        return true;
    }

    /**
     * Calculates total import cost from regional product deficits.
     *
     * @param land country land
     */
    public void calculateImport(Land land) {
        double totalImport = 0;

        for (Region region : getRegions(land)) {
            if (region == null) {
                continue;
            }
            totalImport += ((RegionEconomy) region.getEconomy()).getImport();
        }

        this.import_ = totalImport;
    }

    /**
     * Calculates total export income from regional product surpluses.
     *
     * @param land country land
     */
    public void calculateExport(Land land) {
        double totalExport = 0;

        for (Region region : getRegions(land)) {
            if (region == null) {
                continue;
            }
            totalExport += ((RegionEconomy) region.getEconomy()).getExport();
        }

        this.export = totalExport;
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

    public void restoreTrade(double treasury, double importValue, double exportValue) {
        this.treasury = treasury;
        this.import_ = importValue;
        this.export = exportValue;
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
