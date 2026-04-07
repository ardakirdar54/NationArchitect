package io.github.NationArchitect.controller;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

import io.github.NationArchitect.model.Effect.PopUpEvent;
import io.github.NationArchitect.model.social.SocialMediaComment;

public class DataLoader {

    /**
     * Loads a list of PopUpEvents or SocialMediaComments from a JSON file located in the assets folder.
     * @param internalPath The path relative to the assets directory (e.g., "data/events.json")
     * @return An ArrayList containing the parsed events.
     */
    public ArrayList<PopUpEvent> loadEventsFromJson(String internalPath) {
        Json json = new Json();
        
        
        json.setIgnoreUnknownFields(true);
        
        try {
            
            @SuppressWarnings("unchecked")
            ArrayList<PopUpEvent> events = json.fromJson(
                ArrayList.class, 
                PopUpEvent.class, 
                Gdx.files.internal(internalPath)
            );
            
            Gdx.app.log("DataLoader", "Successfully loaded " + events.size() + " events.");
            return events;
            
        } catch (Exception e) {
            Gdx.app.error("DataLoader", "Error reading the JSON file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public ArrayList<SocialMediaComment> loadCommentsFromJson(String internalPath) {
        Json json = new Json();
        json.setIgnoreUnknownFields(true);
        
        try {
            @SuppressWarnings("unchecked")
            ArrayList<SocialMediaComment> comments = json.fromJson(
                ArrayList.class, 
                SocialMediaComment.class, 
                Gdx.files.internal(internalPath)
            );
            Gdx.app.log("DataLoader", "Successfully loaded " + comments.size() + " social media comments.");
            return comments;
            
        } catch (Exception e) {
            Gdx.app.error("DataLoader", "Error reading the comments JSON file: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
