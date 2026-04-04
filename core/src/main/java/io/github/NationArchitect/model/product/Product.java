package io.github.NationArchitect.model.product;

public class Product {

    private final ProductType type;

    private int amount;

    private final double salePrice;

    private final double purchasePrice;

    Product(ProductType type) {
        this.type = type;
        this.amount = 0;
        this.salePrice = ProductType.getSalePrice();
        this.purchasePrice = ProductType.getPurchasePrice();
    }
}
