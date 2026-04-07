package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.land.Region;
import io.github.NationArchitect.model.metric.Happiness;

/**
 * Represents the road network component, which improves {@link Happiness} and boosts
 * {@link Factory}, {@link RoadTransport}, {@link RailTransport}, and {@link Tourism}.
 */
public class RoadNetwork extends Component {

    /**
     * Creates the road network component.
     *
     * @param region region that owns the component
     */
    public RoadNetwork(Region region) {
        super(ComponentType.ROAD_NETWORK, region);
    }
}

