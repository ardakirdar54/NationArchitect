package io.github.NationArchitect.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.github.NationArchitect.model.land.Country;
import io.github.NationArchitect.model.social.SocialMediaComment;

public class SocialMediaGenerator {

    private List<SocialMediaComment> commentPool;
    private final Random random;

    public SocialMediaGenerator(DataLoader dataLoader) {
        this.random = new Random();
        
        this.commentPool = dataLoader.loadCommentsFromJson("data/comments.json");
    }

    /**
     * Generates a single social media comment that fits the current state of the country.
     * 
     */
    public SocialMediaComment generateComment(Country country) {
        List<SocialMediaComment> possibleComments = new ArrayList<>();

        for (SocialMediaComment comment : commentPool) {
            if (comment.canTrigger(country)) {
                possibleComments.add(comment);
            }
        }

        if (possibleComments.isEmpty()) {
            return null;
        }

        int randomIndex = random.nextInt(possibleComments.size());
        return possibleComments.get(randomIndex);
    }
}
