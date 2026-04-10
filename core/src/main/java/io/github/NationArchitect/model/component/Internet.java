package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.land.Region;
import io.github.NationArchitect.model.metric.Happiness;

/**
 * Represents the internet component, which improves {@link Happiness} and boosts the performance of
 * {@link Office}, {@link Education}, and {@link Security}.
 */
public class Internet extends Component {

    /**
     * Creates the internet component.
     *
     * @param region region that owns the component
     */
    public Internet(Region region) {
        super(ComponentType.INTERNET, region);
    }
}

