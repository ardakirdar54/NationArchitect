package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.product.ProductType;

public class Electricity extends ManufacturerComponent {

    public Electricity() {
        super(ComponentType.ELECTRICITY, ProductType.ENERGY);
    }

}
