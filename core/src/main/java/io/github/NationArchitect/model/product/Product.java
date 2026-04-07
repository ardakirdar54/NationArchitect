package io.github.NationArchitect.model.product;

/**
 * Base class for tradable products tracked by the economy.
 */
public class Product {

    /** Type of the product. */
    private final ProductType type;

    /** Current stored amount of the product. */
    private int amount;

    /** Unit sale price of the product. */
    private final double salePrice;

    /** Unit purchase price of the product. */
    private final double purchasePrice;

    /**
     * Creates a product with prices derived from its type.
     *
     * @param type type of the product
     */
    public Product(ProductType type) {
        this.type = type;
        this.amount = 0;
        this.salePrice = type.getSalePrice();
        this.purchasePrice = type.getPurchasePrice();
    }

    /**
     * Returns the product type.
     *
     * @return product type
     */
    public ProductType getType() {
        return type;
    }

    /**
     * Returns the stored amount of the product.
     *
     * @return product amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Increases the stored amount of the product.
     *
     * @param amount amount to add
     */
    public void produce(int amount) {
        this.amount += amount;
    }

    /**
     * Returns the unit sale price.
     *
     * @return sale price
     */
    public double getSalePrice() {
        return salePrice;
    }

    /**
     * Returns the unit purchase price.
     *
     * @return purchase price
     */
    public double getPurchasePrice() {
        return purchasePrice;
    }
}
