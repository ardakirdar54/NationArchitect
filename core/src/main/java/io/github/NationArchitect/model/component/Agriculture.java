package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.product.ProductType;

public class Agriculture extends ManufacturerComponent{

    public Agriculture() {
        super(ComponentType.AGRICULTURE, ProductType.FOOD);
    }
}
