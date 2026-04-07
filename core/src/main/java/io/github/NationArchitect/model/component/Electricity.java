package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.land.Region;
import io.github.NationArchitect.model.product.ProductType;

/**
 * Represents the electricity component, which produces energy for the region.
 */
public class Electricity extends ManufacturerComponent {

    /**
     * Creates the electricity component configured to produce {@link ProductType#ENERGY}.
     *
     * @param region region that owns the component
     */
    public Electricity(Region region) {
        super(ComponentType.ELECTRICITY, ProductType.ENERGY, region);
    }

}

