package io.github.NationArchitect.model.component;

public class Education extends Component {

    private static int INITIAL_CAPACITY = 0;

    private int primarySchoolCapacity;
    private int highSchoolCapacity;
    private int universityCapacity;

    public Education() {
        super(ComponentType.EDUCATION);

    }
}
