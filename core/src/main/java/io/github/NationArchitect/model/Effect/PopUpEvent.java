package io.github.NationArchitect.model.Effect;

import java.util.ArrayList;
import java.util.List;

import io.github.NationArchitect.model.land.Country;

public class PopUpEvent {
    private String title;
    private String description;
    private List<PopUpChoice> choices;
    private List<TriggerCondition> triggers;

    private double baseWeight;

    public PopUpEvent() {
        this.choices = new ArrayList<>();
        this.triggers = new ArrayList<>();
    }

    public PopUpEvent(String title, String description, double baseWeight) {
        this.title = title;
        this.description = description;
        this.baseWeight = baseWeight;
        this.choices = new ArrayList<>();
    }

    public boolean canTrigger(Country country) {
        
        if (triggers == null || triggers.isEmpty()) {
            return true; 
        }

        for (TriggerCondition condition : triggers) {
            if (!condition.evaluate(country)) {
                return false; 
            }
        }

        return true; 
    }

    public double getBaseWeight(){
        return this.baseWeight;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<PopUpChoice> getChoices() {
        return choices;
    }
}
