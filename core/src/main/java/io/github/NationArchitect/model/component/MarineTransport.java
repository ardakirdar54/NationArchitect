package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.land.Region;
import io.github.NationArchitect.model.metric.Happiness;

/**
 * Represents the marine transport component, which improves {@link Happiness} and supports
 * {@link Tourism} through maritime travel and port access. It also enhances industry
 * components such as {@link Factory}, {@link Agriculture}, and {@link Office} by enabling
 * efficient sea-based logistics, trade, and coastal connectivity.
 */
public class MarineTransport extends Component {

    /**
     * Creates the marine transport component.
     *
     * @param region region that owns the component
     */
    public MarineTransport(Region region) {
        super(ComponentType.MARINE_TRANSPORT, region);
    }
}

