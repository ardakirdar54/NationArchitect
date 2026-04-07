package io.github.NationArchitect.model.economy;

import io.github.NationArchitect.model.component.ComponentType;
import io.github.NationArchitect.model.land.Land;
import io.github.NationArchitect.model.land.Region;
import io.github.NationArchitect.model.metric.MetricType;
import io.github.NationArchitect.model.product.Product;
import io.github.NationArchitect.model.product.ProductType;

import java.util.EnumMap;

/**
 * Economy implementation responsible for regional production, demand, and taxation.
 */
public class RegionEconomy extends Economy {

    /** Produced products grouped by product type. */
    EnumMap<ProductType, Product> production;

    /** Demanded products grouped by product type. */
    EnumMap<ProductType, Product> demand;

    /**
     * Creates a region economy with empty production and demand maps.
     *
     * @param tax tax configuration used for calculations
     */
    public RegionEconomy(Tax tax) {
        super(tax);
        production = new EnumMap<>(ProductType.class);
        demand = new EnumMap<>(ProductType.class);
    }

    /**
     * Updates the economy statistics
     *
     * @param land the land that economy belongs to
     */
    public void update(Land land) {
        calculateTotalIncome(land);
    }

    /**
     * Calculates income tax revenue for the given region.
     *
     * @param land target region
     */
    public void calculateIncomeTaxRevenue(Land land) {
        int totalProduction = 0;

        for (ProductType type : ProductType.values()) {
            totalProduction += production.get(type).getAmount();
        }

        int population = land.getPopulation().getTotalPopulation();

        double popFactor = population / (population + 100000.0);

        double incomeTax = popFactor
            * getTax().getIncomeTaxRate()
            * (land.getMetricValue(MetricType.HAPPINESS))
            * (1 - Math.exp(-0.02 * totalProduction))
            * totalProduction;

        putTaxRevenue(TaxType.INCOME_TAX, incomeTax);
    }

    /**
     * Calculates property tax revenue for the given region.
     *
     * @param land target region
     */
    public void calculatePropertyTaxRevenue(Land land) {
        assert land instanceof Region;
        //putTaxRevenue(TaxType.PROPERTY_TAX, land.getPopulation().getTotalPopulation() * ((Region)land).getLandValue() * getTax().getPropertyTaxRate());
    }

    /**
     * Calculates VAT revenue for the given region.
     *
     * @param land target region
     */
    public void calculateVATRevenue(Land land) {
        int totalDemand = 0;
        for (ProductType type : ProductType.values()) {
            totalDemand += demand.get(type).getAmount();
        }

        putTaxRevenue(TaxType.VAT, totalDemand * getTax().getVatRate());
    }

    /**
     * Calculates excise tax revenue for the given region.
     *
     * @param land target region
     */
    public void calculateExciseTaxRevenue(Land land) {
        double totalRevenue = 0;
        for (ProductType type : ProductType.values()) {
            totalRevenue += demand.get(type).getAmount() * getTax().getExciseTaxRate(type);
        }

        putTaxRevenue(TaxType.EXCISE_TAX, totalRevenue);
    }

    /**
     * Calculates corporate tax revenue for the given region.
     *
     * @param land target region
     */
    public void calculateCorporateTaxRevenue(Land land) {
        double totalProfit = 0;
        assert land instanceof Region;
        Region region = (Region) land;
        double profit = production.get(ProductType.INDUSTRIAL_GOOD).getAmount() * ProductType.INDUSTRIAL_GOOD.getSalePrice()
            - region.getComponentBudget(ComponentType.FACTORY);
        totalProfit += profit > 0 ? profit : profit * -0.07;

        profit += production.get(ProductType.WATER).getAmount() * ProductType.WATER.getSalePrice()
            - region.getComponentBudget(ComponentType.WATER_MANAGEMENT);
        totalProfit += profit > 0 ? profit : profit * -0.07;

        profit += production.get(ProductType.FOOD).getAmount() * ProductType.FOOD.getSalePrice()
            - region.getComponentBudget(ComponentType.AGRICULTURE);
        totalProfit += profit > 0 ? profit : profit * -0.07;

        profit += production.get(ProductType.TECHNOLOGY).getAmount() * ProductType.TECHNOLOGY.getSalePrice()
            - region.getComponentBudget(ComponentType.OFFICE);
        totalProfit += profit > 0 ? profit : profit * -0.07;

        profit += production.get(ProductType.TOURISM_SERVICE).getAmount() * ProductType.TOURISM_SERVICE.getSalePrice()
            - region.getComponentBudget(ComponentType.TOURISM);
        totalProfit += profit > 0 ? profit : profit * -0.07;

        putTaxRevenue(TaxType.CORPORATE_TAX, totalProfit * getTax().getCorporateTaxRate());
    }

    /**
     * Calculates production tax revenue for the given region.
     *
     * @param land target region
     */
    public void calculateProductionTaxRevenue(Land land) {
        double totalProduction = 0;

        for (ProductType type : ProductType.values()) {
            totalProduction += production.get(type).getAmount() * type.getSalePrice();
        }

        putTaxRevenue(TaxType.PRODUCTION_TAX, totalProduction * getTax().getProductionTaxRate() * 0.02);

    }

    /** {@inheritDoc} */
    public double calculateTaxIncome(Land land) {
        calculateIncomeTaxRevenue(land);
        calculatePropertyTaxRevenue(land);
        calculateVATRevenue(land);
        calculateExciseTaxRevenue(land);
        calculateCorporateTaxRevenue(land);
        calculateProductionTaxRevenue(land);

        double revenue = 0;
        for (TaxType type : TaxType.values()) {
            revenue += getTaxRevenues().get(type);
        }

        return revenue;
    }

    /** {@inheritDoc} */
    public void calculateTotalIncome(Land land) {
        setIncome(calculateTaxIncome(land));
    }

    /** {@inheritDoc} */
    public void getComponentBudgets(Land land) {
        clearComponentBudgets();

        for (ComponentType type : ComponentType.values()) {
            putComponentBudget(type, ((Region)land).getComponentBudget(type));
        }
    }

    /** {@inheritDoc} */
    public void calculateTotalExpanses(Land land) {
        double totalExpanses = 0;
        getComponentBudgets(land);
        for (ComponentType type : ComponentType.values()) {
            totalExpanses += getComponentBudgets().get(type);
        }
        setExpanse(totalExpanses);
    }

    /**
     * Returns the product deficit for a product type.
     *
     * @param productType target product type
     * @return negative deficit value or zero
     */
    public int getProductDeficit(ProductType productType) {
        int difference = production.get(productType).getAmount() - demand.get(productType).getAmount();
        return Math.min(difference, 0);
    }

    /**
     * Returns the product surplus for a product type.
     *
     * @param productType target product type
     * @return positive surplus value or zero
     */
    public int getProductSurplus(ProductType productType) {
        int difference = production.get(productType).getAmount() - demand.get(productType).getAmount();
        return Math.max(difference, 0);
    }

    /**
     * Fetches regional production totals.
     *
     * @param land target region
     */
    public void getProduction(Land land) {
        production.clear();
        assert land instanceof Region;
        Region region = (Region)land;
        for (ProductType type : ProductType.values()) {
            Product product = new Product(type);
            product.produce(region.getManufacturerComponentProduction(type));
            production.put(type, product);
        }
    }

    /**
     * Fetches regional product demand totals.
     *
     * @param land target region
     */
    public void getDemand(Land land) {
        demand.clear();
        assert land instanceof Region;
        Region region = (Region)land;
        for (ProductType type : ProductType.values()) {
            Product product = new Product(type);
            for (ComponentType componentType : ComponentType.values()) {
                product.produce(region.getProductDemand(type));
            }
            demand.put(type, product);
        }
    }
}
