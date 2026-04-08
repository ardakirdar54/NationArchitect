package io.github.NationArchitect.model.Effect;

public class ActiveEffect {
    
    private final Effect effect;
    private int remainingMonths;

    public ActiveEffect(Effect effect, int duration) {
        this.effect = effect;
        this.remainingMonths = duration;
    }

    public Effect getEffect() {
        return effect;
    }

    public void decrementDuration() {
        if (remainingMonths > 0) {
            this.remainingMonths--;
        }
    }

    public boolean isExpired() {
        return remainingMonths <= 0;
    }

    public int getRemainingMonths() {
        return remainingMonths;
    }
}
