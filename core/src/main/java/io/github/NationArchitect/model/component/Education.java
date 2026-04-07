package io.github.NationArchitect.model.component;

import io.github.NationArchitect.model.land.Region;
import io.github.NationArchitect.model.metric.EducationLevel;

/**
 * Represents the education component, which primarily improves the
 * {@link EducationLevel} metric and tracks education capacity across school tiers.
 */
public class Education extends Component {

    /**
     * Creates the education component that affects education level through its buildings.
     *
     * @param region region that owns the component
     */
    public Education(Region region) {
        super(ComponentType.EDUCATION, region);
    }
}
