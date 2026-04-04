package io.github.NationArchitect.model.economy;

public class Tax {
    private final static double INITIAL_TAX_RATE = 7.0;
    private final static double MIN_TAX_RATE = 2.0;
    private final static double MAX_TAX_RATE = 20.0;

    private double incomeTaxRate;
    private double propertyTaxRate;
    private double vatRate;
    private double exciseTaxRate;
    private double corporateTaxRate;
    private double productionTaxRate;

    public Tax() {
        setIncomeTaxRate(INITIAL_TAX_RATE);
        setPropertyTaxRate(INITIAL_TAX_RATE);
        setVatRate(INITIAL_TAX_RATE);
        setExciseTaxRate(INITIAL_TAX_RATE);
        setCorporateTaxRate(INITIAL_TAX_RATE);
        setProductionTaxRate(INITIAL_TAX_RATE);
    }


    public double getIncomeTaxRate() {
        return incomeTaxRate;
    }

    public void setIncomeTaxRate(double incomeTaxRate) {
        if (incomeTaxRate <= MAX_TAX_RATE && incomeTaxRate >= MIN_TAX_RATE) {
            this.incomeTaxRate = incomeTaxRate;
        }
    }

    public double getPropertyTaxRate() {
        return propertyTaxRate;
    }

    public void setPropertyTaxRate(double propertyTaxRate) {
        if (propertyTaxRate <= MAX_TAX_RATE && propertyTaxRate >= MIN_TAX_RATE) {
            this.propertyTaxRate = propertyTaxRate;
        }
    }

    public double getVatRate() {
        return vatRate;
    }

    public void setVatRate(double vatRate) {
        if (vatRate <= MAX_TAX_RATE && vatRate >= MIN_TAX_RATE) {
            this.vatRate = vatRate;
        }
    }

    public double getExciseTaxRate() {
        return exciseTaxRate;
    }

    public void setExciseTaxRate(double exciseTaxRate) {
        if (exciseTaxRate <= 20 && exciseTaxRate >= MIN_TAX_RATE) {
            this.exciseTaxRate = exciseTaxRate;
        }
    }

    public double getCorporateTaxRate() {
        return corporateTaxRate;
    }

    public void setCorporateTaxRate(double corporateTaxRate) {
        if (corporateTaxRate <= MAX_TAX_RATE && corporateTaxRate >= MIN_TAX_RATE) {
            this.corporateTaxRate = corporateTaxRate;
        }
    }

    public double getProductionTaxRate() {
        return productionTaxRate;
    }

    public void setProductionTaxRate(double productionTaxRate) {
        if (productionTaxRate <= MAX_TAX_RATE && productionTaxRate >= MIN_TAX_RATE) {
            this.productionTaxRate = productionTaxRate;
        }
    }
}
