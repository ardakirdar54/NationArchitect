package io.github.NationArchitect.model.social;

import java.util.ArrayList;
import java.util.List;

import io.github.NationArchitect.model.Effect.TriggerCondition;
import io.github.NationArchitect.model.land.Country;

public class SocialMediaComment {

    private String author;
    private String content;
    private List<TriggerCondition> triggers;


    public SocialMediaComment() {
        this.triggers = new ArrayList<>();
    }

    /**
     * Checks if the comment is eligible to be posted based on current country conditions.
     */
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

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}

