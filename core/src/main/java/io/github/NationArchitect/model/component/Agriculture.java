package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.land.Region;
import io.github.NationArchitect.model.product.Food;
import io.github.NationArchitect.model.product.ProductType;

/**
 * Represents the agriculture component, which produces {@link Food} and supports the food supply of a region.
 */
public class Agriculture extends ManufacturerComponent{

    /**
     * Creates the agriculture component configured to produce {@link ProductType#FOOD}.
     *
     * @param region region that owns the component
     */
    public Agriculture(Region region) {
        super(ComponentType.AGRICULTURE, ProductType.FOOD, region);
    }
}

