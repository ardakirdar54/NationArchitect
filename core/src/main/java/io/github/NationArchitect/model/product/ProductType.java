package io.github.NationArchitect.model.product;

public enum ProductType {

    WATER(10, 8),
    ENERGY(15, 12),
    TECHNOLOGY(110, 90),
    INDUSTRIAL_GOOD(70, 50),
    FOOD(20, 15);

    private final double salePrice;

    private final double purchasePrice;

    ProductType(double purchasePrice, double salePrice) {
        this.salePrice = salePrice;
        this.purchasePrice = purchasePrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }
}
