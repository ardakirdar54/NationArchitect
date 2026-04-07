package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.land.Region;
import io.github.NationArchitect.model.metric.Happiness;

/**
 * Represents the rail transport component, which improves {@link Happiness} and supports
 * {@link Tourism} through long-distance passenger movement across rail-based infrastructure.
 * It also enhances industry components such as {@link Factory}, {@link Agriculture}, and
 * {@link Office} by providing reliable high-capacity logistics across regions.
 */
public class RailTransport extends Component {

    /**
     * Creates the rail transport component.
     *
     * @param region region that owns the component
     */
    public RailTransport(Region region) {
        super(ComponentType.RAIL_TRANSPORT, region);
    }
}

