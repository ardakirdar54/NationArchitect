package io.github.NationArchitect.controller.savemanager;

/**
 * Account model for username-based authentication.
 */
public class Account {

    private String userID;
    private String username;
    private String passwordHash;
    private SaveDateTime createdAt;

    public Account() {
    }

    public Account(String userID, String username, String passwordHash, SaveDateTime createdAt) {
        this.userID = userID;
        this.username = username;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
    }

    public String getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public SaveDateTime getCreatedAt() {
        return createdAt;
    }
}
