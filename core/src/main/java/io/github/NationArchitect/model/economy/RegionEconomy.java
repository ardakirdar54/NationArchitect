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

    private static final double IMPORT_COST_MULTIPLIER = 0.35;

    /** Produced products grouped by product type. */
    EnumMap<ProductType, Product> production;

    /** Demanded products grouped by product type. */
    EnumMap<ProductType, Product> demand;

    /** Total import spending for the region. */
    private double import_;

    /** Total export income for the region. */
    private double export;

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
        getProduction(land);
        getDemand(land);
        calculateImport(land);
        calculateExport(land);
        calculateBalance(land);
    }

    /**
     * Calculates income tax revenue for the given region.
     *
     * @param land target region
     */
    public void calculateIncomeTaxRevenue(Land land) {
        ensureTradeData(land);
        double totalProductionValue = 0;

        for (ProductType type : ProductType.values()) {
            totalProductionValue += production.getOrDefault(type, new Product(type)).getAmount() * type.getSalePrice();
        }

        Region region = (Region) land;
        int workingPopulation = land.getPopulation().getWorkingAgePopulation();
        int employedPopulation = Math.min(workingPopulation, Math.max(region.getTotalEmploymentCapacity(), 0));
        double taxableIncomeBase = totalProductionValue + (employedPopulation * 2.5);
        double happinessFactor = Math.max(0.25, land.getMetricValue(MetricType.HAPPINESS) / 100.0);
        double incomeTax = taxableIncomeBase * (getTax().getIncomeTaxRate() / 100.0) * happinessFactor;

        putTaxRevenue(TaxType.INCOME_TAX, incomeTax);
    }

    /**
     * Calculates property tax revenue for the given region.
     *
     * @param land target region
     */
    public void calculatePropertyTaxRevenue(Land land) {
        assert land instanceof Region;
        Region region = (Region) land;
        double propertyBase = Math.max(region.getLandValue(), region.getTotalOccupiedLand() * 10.0);
        double propertyTax = land.getPopulation().getTotalPopulation() * propertyBase * (getTax().getPropertyTaxRate() / 100.0) * 0.000002;
        putTaxRevenue(TaxType.PROPERTY_TAX, propertyTax);
    }

    /**
     * Calculates VAT revenue for the given region.
     *
     * @param land target region
     */
    public void calculateVATRevenue(Land land) {
        ensureTradeData(land);
        int totalDemand = 0;
        for (ProductType type : ProductType.values()) {
            totalDemand += demand.getOrDefault(type, new Product(type)).getAmount();
        }

        putTaxRevenue(TaxType.VAT, totalDemand * (getTax().getVatRate() / 100.0));
    }

    /**
     * Calculates excise tax revenue for the given region.
     *
     * @param land target region
     */
    public void calculateExciseTaxRevenue(Land land) {
        ensureTradeData(land);
        double totalRevenue = 0;
        for (ProductType type : ProductType.values()) {
            totalRevenue += demand.getOrDefault(type, new Product(type)).getAmount() * (getTax().getExciseTaxRate(type) / 100.0);
        }

        putTaxRevenue(TaxType.EXCISE_TAX, totalRevenue);
    }

    /**
     * Calculates corporate tax revenue for the given region.
     *
     * @param land target region
     */
    public void calculateCorporateTaxRevenue(Land land) {
        ensureTradeData(land);
        assert land instanceof Region;
        Region region = (Region) land;
        double totalProfit = 0;

        double profit = production.getOrDefault(ProductType.INDUSTRIAL_GOOD, new Product(ProductType.INDUSTRIAL_GOOD)).getAmount() * ProductType.INDUSTRIAL_GOOD.getSalePrice()
            - region.getComponentBudget(ComponentType.FACTORY);
        totalProfit += profit > 0 ? profit : profit * -0.07;

        profit = production.getOrDefault(ProductType.WATER, new Product(ProductType.WATER)).getAmount() * ProductType.WATER.getSalePrice()
            - region.getComponentBudget(ComponentType.WATER_MANAGEMENT);
        totalProfit += profit > 0 ? profit : profit * -0.07;

        profit = production.getOrDefault(ProductType.FOOD, new Product(ProductType.FOOD)).getAmount() * ProductType.FOOD.getSalePrice()
            - region.getComponentBudget(ComponentType.AGRICULTURE);
        totalProfit += profit > 0 ? profit : profit * -0.07;

        profit = production.getOrDefault(ProductType.TECHNOLOGY, new Product(ProductType.TECHNOLOGY)).getAmount() * ProductType.TECHNOLOGY.getSalePrice()
            - region.getComponentBudget(ComponentType.OFFICE);
        totalProfit += profit > 0 ? profit : profit * -0.07;

        profit = production.getOrDefault(ProductType.TOURISM_SERVICE, new Product(ProductType.TOURISM_SERVICE)).getAmount() * ProductType.TOURISM_SERVICE.getSalePrice()
            - region.getComponentBudget(ComponentType.TOURISM);
        totalProfit += profit > 0 ? profit : profit * -0.07;

        putTaxRevenue(TaxType.CORPORATE_TAX, totalProfit * (getTax().getCorporateTaxRate() / 100.0));
    }

    /**
     * Calculates production tax revenue for the given region.
     *
     * @param land target region
     */
    public void calculateProductionTaxRevenue(Land land) {
        ensureTradeData(land);
        double totalProduction = 0;

        for (ProductType type : ProductType.values()) {
            totalProduction += production.getOrDefault(type, new Product(type)).getAmount() * type.getSalePrice();
        }

        putTaxRevenue(TaxType.PRODUCTION_TAX, totalProduction * (getTax().getProductionTaxRate() / 100.0));

    }

    /** {@inheritDoc} */
    public double calculateTaxIncome(Land land) {
        ensureTradeData(land);
        clearTaxRevenues();
        calculateIncomeTaxRevenue(land);
        calculatePropertyTaxRevenue(land);
        calculateVATRevenue(land);
        calculateExciseTaxRevenue(land);
        calculateCorporateTaxRevenue(land);
        calculateProductionTaxRevenue(land);

        double revenue = 0;
        for (TaxType type : TaxType.values()) {
            revenue += getTaxRevenues().getOrDefault(type, 0.0);
        }

        return revenue;
    }

    /** {@inheritDoc} */
    public void calculateTotalIncome(Land land) {
        setIncome(calculateTaxIncome(land) + export);
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
        double totalExpanses = import_;
        getComponentBudgets(land);
        for (ComponentType type : ComponentType.values()) {
            totalExpanses += getComponentBudgets().getOrDefault(type, 0.0);
        }
        setExpanse(totalExpanses);
    }

    /**
     * Calculates total regional import spending from product deficits.
     *
     * @param land target region
     */
    public void calculateImport(Land land) {
        ensureTradeData(land);
        double totalImport = 0;
        for (ProductType productType : ProductType.values()) {
            totalImport += Math.abs(getProductDeficit(productType)) * productType.getPurchasePrice() * IMPORT_COST_MULTIPLIER;
        }
        this.import_ = totalImport;
    }

    /**
     * Calculates total regional export income from product surpluses.
     *
     * @param land target region
     */
    public void calculateExport(Land land) {
        ensureTradeData(land);
        double totalExport = 0;
        for (ProductType productType : ProductType.values()) {
            totalExport += getProductSurplus(productType) * productType.getSalePrice();
        }
        this.export = totalExport;
    }

    /**
     * Returns the product deficit for a product type.
     *
     * @param productType target product type
     * @return negative deficit value or zero
     */
    public int getProductDeficit(ProductType productType) {
        int difference = production.getOrDefault(productType, new Product(productType)).getAmount()
            - demand.getOrDefault(productType, new Product(productType)).getAmount();
        return Math.min(difference, 0);
    }

    /**
     * Returns the product surplus for a product type.
     *
     * @param productType target product type
     * @return positive surplus value or zero
     */
    public int getProductSurplus(ProductType productType) {
        int difference = production.getOrDefault(productType, new Product(productType)).getAmount()
            - demand.getOrDefault(productType, new Product(productType)).getAmount();
        return Math.max(difference, 0);
    }

    private void ensureTradeData(Land land) {
        if (production.size() != ProductType.values().length) {
            getProduction(land);
        }
        if (demand.size() != ProductType.values().length) {
            getDemand(land);
        }
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
            product.produce(region.getProductDemand(type));
            demand.put(type, product);
        }
    }

    public EnumMap<ProductType, Product> getProductionProducts() {
        return new EnumMap<>(production);
    }

    public EnumMap<ProductType, Product> getDemandProducts() {
        return new EnumMap<>(demand);
    }

    public double getImport() {
        return import_;
    }

    public double getExport() {
        return export;
    }

    public void restoreProduction(EnumMap<ProductType, Product> production) {
        this.production.clear();
        if (production != null) {
            this.production.putAll(production);
        }
    }

    public void restoreDemand(EnumMap<ProductType, Product> demand) {
        this.demand.clear();
        if (demand != null) {
            this.demand.putAll(demand);
        }
    }

    public void restoreTrade(double importValue, double exportValue) {
        this.import_ = importValue;
        this.export = exportValue;
    }
}
