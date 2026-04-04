package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.product.ProductType;

public class Factory extends ManufacturerComponent {

    public Factory() {
        super(ComponentType.FACTORY, ProductType.INDUSTRIAL_GOOD);
    }
}
