package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.land.Region;
import io.github.NationArchitect.model.metric.CrimeRate;

/**
 * Represents the security component, which primarily affects the
 * {@link CrimeRate} metric.
 */
public class Security extends Component {

    /**
     * Creates the security component that improves public safety through its buildings.
     *
     * @param region region that owns the component
     */
    public Security(Region region) {
        super(ComponentType.SECURITY, region);
    }
}

