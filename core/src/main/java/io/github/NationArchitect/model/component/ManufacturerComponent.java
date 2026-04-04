package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.product.ProductType;

public abstract class ManufacturerComponent extends Component {

    private double productionAmount;

    private final ProductType productType;

    public ManufacturerComponent(ComponentType componentType, ProductType productType) {
        super(componentType);
        this.productType = productType;
    }



    public double getProductionAmount() {
        return productionAmount;
    }

}
