package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.land.Region;
import io.github.NationArchitect.model.product.ProductType;
import io.github.NationArchitect.model.product.Technology;

/**
 * Represents the office component, which produces {@link Technology} and supports administrative and service activity.
 */
public class Office extends ManufacturerComponent {

    /**
     * Creates the office component configured to produce {@link ProductType#TECHNOLOGY}.
     *
     * @param region region that owns the component
     */
    public Office(Region region) {
        super(ComponentType.OFFICE, ProductType.TECHNOLOGY, region);
    }
}

