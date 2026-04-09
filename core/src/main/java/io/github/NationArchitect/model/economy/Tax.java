package io.github.NationArchitect.model.economy;

import io.github.NationArchitect.model.product.ProductType;

/**
 * Stores configurable tax rates used by the economic model.
 */
public class Tax {
    /** Default starting tax rate for all categories. */
    private final static double INITIAL_TAX_RATE = 7.0;
    /** Minimum allowed tax rate. */
    private final static double MIN_TAX_RATE = 2.0;
    /** Maximum allowed tax rate. */
    private final static double MAX_TAX_RATE = 20.0;

    /** Income tax rate. */
    private double incomeTaxRate;
    /** Property tax rate. */
    private double propertyTaxRate;
    /** VAT rate. */
    private double vatRate;
    /** Base excise tax rate. */
    private double exciseTaxRate;
    /** Corporate tax rate. */
    private double corporateTaxRate;
    /** Production tax rate. */
    private double productionTaxRate;

    /**
     * Creates a tax configuration with default rates.
     */
    public Tax() {
        setIncomeTaxRate(INITIAL_TAX_RATE);
        setPropertyTaxRate(INITIAL_TAX_RATE);
        setVatRate(INITIAL_TAX_RATE);
        setExciseTaxRate(INITIAL_TAX_RATE);
        setCorporateTaxRate(INITIAL_TAX_RATE);
        setProductionTaxRate(INITIAL_TAX_RATE);
    }


    /**
     * Returns income tax rate.
     *
     * @return income tax rate
     */
    public double getIncomeTaxRate() {
        return incomeTaxRate;
    }

    /**
     * Updates income tax rate if it is within bounds.
     *
     * @param incomeTaxRate new income tax rate
     */
    public void setIncomeTaxRate(double incomeTaxRate) {
        if (incomeTaxRate <= MAX_TAX_RATE && incomeTaxRate >= MIN_TAX_RATE) {
            this.incomeTaxRate = incomeTaxRate;
        }
    }

    /**
     * Returns property tax rate.
     *
     * @return property tax rate
     */
    public double getPropertyTaxRate() {
        return propertyTaxRate;
    }

    /**
     * Updates property tax rate if it is within bounds.
     *
     * @param propertyTaxRate new property tax rate
     */
    public void setPropertyTaxRate(double propertyTaxRate) {
        if (propertyTaxRate <= MAX_TAX_RATE && propertyTaxRate >= MIN_TAX_RATE) {
            this.propertyTaxRate = propertyTaxRate;
        }
    }

    /**
     * Returns VAT rate.
     *
     * @return VAT rate
     */
    public double getVatRate() {
        return vatRate;
    }

    /**
     * Updates VAT rate if it is within bounds.
     *
     * @param vatRate new VAT rate
     */
    public void setVatRate(double vatRate) {
        if (vatRate <= MAX_TAX_RATE && vatRate >= MIN_TAX_RATE) {
            this.vatRate = vatRate;
        }
    }

    /**
     * Returns excise tax rate adjusted for the given product type.
     *
     * @param productType product type to evaluate
     * @return effective excise tax rate
     */
    public double getExciseTaxRate(ProductType productType) {
        switch (productType) {
            case FOOD: return exciseTaxRate * 0.1;
            case INDUSTRIAL_GOOD: return exciseTaxRate * 0.2;
            case TECHNOLOGY: return exciseTaxRate * 0.5;
            case WATER: return exciseTaxRate * 0.01;
            case TOURISM_SERVICE: return exciseTaxRate;
        }
        return 0;
    }

    /**
     * Updates excise tax rate if it is within bounds.
     *
     * @param exciseTaxRate new excise tax rate
     */
    public void setExciseTaxRate(double exciseTaxRate) {
        if (exciseTaxRate <= 20 && exciseTaxRate >= MIN_TAX_RATE) {
            this.exciseTaxRate = exciseTaxRate;
        }
    }

    /**
     * Returns corporate tax rate.
     *
     * @return corporate tax rate
     */
    public double getCorporateTaxRate() {
        return corporateTaxRate;
    }

    /**
     * Updates corporate tax rate if it is within bounds.
     *
     * @param corporateTaxRate new corporate tax rate
     */
    public void setCorporateTaxRate(double corporateTaxRate) {
        if (corporateTaxRate <= MAX_TAX_RATE && corporateTaxRate >= MIN_TAX_RATE) {
            this.corporateTaxRate = corporateTaxRate;
        }
    }

    /**
     * Returns production tax rate.
     *
     * @return production tax rate
     */
    public double getProductionTaxRate() {
        return productionTaxRate;
    }

    /**
     * Updates production tax rate if it is within bounds.
     *
     * @param productionTaxRate new production tax rate
     */
    public void setProductionTaxRate(double productionTaxRate) {
        if (productionTaxRate <= MAX_TAX_RATE && productionTaxRate >= MIN_TAX_RATE) {
            this.productionTaxRate = productionTaxRate;
        }
    }
}
