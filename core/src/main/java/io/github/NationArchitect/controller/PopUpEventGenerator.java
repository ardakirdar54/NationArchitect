package io.github.NationArchitect.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.github.NationArchitect.model.Effect.PopUpEvent;
import io.github.NationArchitect.model.land.Country;

public class PopUpEventGenerator {

    private List<PopUpEvent> eventPool;
    private final Random random;

    public PopUpEventGenerator(DataLoader dataLoader) {
        this.random = new Random();
        this.eventPool = dataLoader.loadEventsFromJson("data/events.json");
    }

    /**
     * Called every month in the game loop to determine if an event should happen.
     */
    public PopUpEvent generateEvent(Country country) {
        List<PopUpEvent> possibleEvents = new ArrayList<>();

        for (PopUpEvent event : eventPool) {
            if (event.canTrigger(country)) {
                possibleEvents.add(event);
            }
        }

        if (possibleEvents.isEmpty()) {
            return null;
        }

        return getRandomEventByWeight(possibleEvents);
    }

    /**
     * Selects a random event from the list, favoring events with higher base weight.
     */
    private PopUpEvent getRandomEventByWeight(List<PopUpEvent> possibleEvents) {
        double totalWeight = 0.0;
        
        for (PopUpEvent event : possibleEvents) {
            totalWeight += event.getBaseWeight(); 
        }

        double randomValue = random.nextDouble() * totalWeight;
        double currentWeightSum = 0.0;

        for (PopUpEvent event : possibleEvents) {
            currentWeightSum += event.getBaseWeight();
            if (randomValue <= currentWeightSum) {
                return event;
            }
        }

        return possibleEvents.get(possibleEvents.size() - 1);
    }
}
