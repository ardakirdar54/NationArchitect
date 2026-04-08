package io.github.NationArchitect.model.product;

/**
 * Lists tradable product categories and their default prices.
 */
public enum ProductType {

    WATER(10, 8),
    ENERGY(15, 12),
    TECHNOLOGY(110, 90),
    INDUSTRIAL_GOOD(70, 50),
    FOOD(20, 15),
    TOURISM_SERVICE(0, 50);

    /** Unit sale price of the product type. */
    private final double salePrice;

    /** Unit purchase price of the product type. */
    private final double purchasePrice;

    /**
     * Creates the enum value definition for {@link ProductType}.
     *
     * @param purchasePrice default purchase price
     * @param salePrice default sale price
     */
    ProductType(double purchasePrice, double salePrice) {
        this.salePrice = salePrice;
        this.purchasePrice = purchasePrice;
    }

    /**
     * Returns the default sale price.
     *
     * @return sale price
     */
    public double getSalePrice() {
        return salePrice;
    }

    /**
     * Returns the default purchase price.
     *
     * @return purchase price
     */
    public double getPurchasePrice() {
        return purchasePrice;
    }
}
