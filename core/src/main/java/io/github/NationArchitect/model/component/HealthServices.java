package io.github.NationArchitect.model.component;

public class HealthServices extends Component {

    private int capacity;

    HealthServices() {
        super(ComponentType.HEALTH_SERVICES);
    }

    public int getCapacity() {
        return capacity;
    }

    void increaseCapacity(int amount) {
        this.capacity += amount;
    }

    void decreaseCapacity(int amount) {
        this.capacity = Math.max(0, this.capacity - amount);
    }
}
