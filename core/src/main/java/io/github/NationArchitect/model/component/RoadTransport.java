package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.land.Region;
import io.github.NationArchitect.model.metric.Happiness;

/**
 * Represents the road transport component, which improves {@link Happiness} and supports
 * {@link Tourism} through everyday passenger mobility and destination access via road-based
 * infrastructure. It also enhances industry components such as {@link Factory} and
 * {@link Agriculture} by improving local distribution and overland logistics.
 */
public class RoadTransport extends Component {

    /**
     * Creates the road transport component.
     *
     * @param region region that owns the component
     */
    public RoadTransport(Region region) {
        super(ComponentType.ROAD_TRANSPORT, region);
    }
}

