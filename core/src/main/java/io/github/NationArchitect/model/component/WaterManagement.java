package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.land.Region;
import io.github.NationArchitect.model.product.ProductType;
import io.github.NationArchitect.model.product.Water;

/**
 * Represents the water management component, which produces {@link Water} and supports sanitation services.
 */
public class WaterManagement extends ManufacturerComponent {

    /**
     * Creates the water management component configured to produce {@link ProductType#WATER}.
     *
     * @param region region that owns the component
     */
    public WaterManagement(Region region) {
        super(ComponentType.WATER_MANAGEMENT, ProductType.WATER, region);
    }

}

