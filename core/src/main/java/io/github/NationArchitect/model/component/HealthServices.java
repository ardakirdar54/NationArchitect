package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.land.Region;
import io.github.NationArchitect.model.metric.HealthRate;

/**
 * Represents the health services component, which primarily improves the
 * {@link HealthRate} metric.
 */
public class HealthServices extends Component {

    /**
     * Creates the health services component that affects population health through its buildings.
     *
     * @param region region that owns the component
     */
    public HealthServices(Region region) {
        super(ComponentType.HEALTH_SERVICES, region);
    }
}
