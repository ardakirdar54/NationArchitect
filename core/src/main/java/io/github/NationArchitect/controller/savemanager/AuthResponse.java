package io.github.NationArchitect.controller.savemanager;

/**
 * Authentication response carrying user id and bearer token.
 */
public class AuthResponse {

    private String userID;
    private String token;

    public AuthResponse() {
    }

    public AuthResponse(String userID, String token) {
        this.userID = userID;
        this.token = token;
    }

    public String getUserID() {
        return userID;
    }

    public String getToken() {
        return token;
    }
}
