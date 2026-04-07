package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.land.Region;
import io.github.NationArchitect.model.product.IndustrialGood;
import io.github.NationArchitect.model.product.ProductType;

/**
 * Represents the factory component, which produces {@link IndustrialGood} for the region.
 */
public class Factory extends ManufacturerComponent {

    /**
     * Creates the factory component configured to produce {@link ProductType#INDUSTRIAL_GOOD}.
     *
     * @param region region that owns the component
     */
    public Factory(Region region) {
        super(ComponentType.FACTORY, ProductType.INDUSTRIAL_GOOD, region);
    }
}

