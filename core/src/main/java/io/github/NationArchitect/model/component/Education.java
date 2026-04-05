package io.github.NationArchitect.model.component;

public class Education extends Component {

    private int primarySchoolCapacity;
    private int highSchoolCapacity;
    private int universityCapacity;

    public Education() {
        super(ComponentType.EDUCATION);
    }


    public int getPrimarySchoolCapacity() {
        return primarySchoolCapacity;
    }

    void increasePrimarySchoolCapacity(int amount) {
        this.primarySchoolCapacity += amount;
    }

    void decreasePrimarySchoolCapacity(int amount) {
        this.primarySchoolCapacity = Math.max(0, this.primarySchoolCapacity - amount);
    }

    public int getHighSchoolCapacity() {
        return highSchoolCapacity;
    }

    void increaseHighSchoolCapacity(int amount) {
        this.highSchoolCapacity += amount;
    }

    void decreaseHighSchoolCapacity(int amount) {
        this.highSchoolCapacity = Math.max(0, this.highSchoolCapacity - amount);
    }

    public int getUniversityCapacity() {
        return universityCapacity;
    }

    void increaseUniversityCapacity(int amount) {
        this.universityCapacity += amount;
    }

    void decreaseUniversityCapacity(int amount) {
        this.universityCapacity = Math.max(0, this.universityCapacity - amount);
    }
}
