package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.land.Region;
import io.github.NationArchitect.model.metric.Happiness;
import io.github.NationArchitect.model.product.ProductType;

/**
 * Represents the tourism component, which improves {@link Happiness} and works together with
 * {@link AirTransport}, {@link RoadTransport}, {@link Education}, {@link Office}, and {@link Internet}.
 */
public class Tourism extends ManufacturerComponent {

    /**
     * Creates the tourism component.
     *
     * @param region region that owns the component
     */
    public Tourism(Region region) {
        super(ComponentType.TOURISM, ProductType.TOURISM_SERVICE, region);
    }
}

