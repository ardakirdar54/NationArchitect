package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.land.Region;
import io.github.NationArchitect.model.metric.Happiness;

/**
 * Represents the air transport component, which improves {@link Happiness} and supports
 * {@link Education} and {@link Tourism} through aviation-focused infrastructure. It also
 * enhances industry components such as {@link Office}, {@link Agriculture}, and {@link Factory}
 * by making logistics more efficient.
 */
public class AirTransport extends Component {

    /**
     * Creates the air transport component.
     *
     * @param region region that owns the component
     */
    public AirTransport(Region region) {
        super(ComponentType.AIR_TRANSPORT, region);
    }
}
