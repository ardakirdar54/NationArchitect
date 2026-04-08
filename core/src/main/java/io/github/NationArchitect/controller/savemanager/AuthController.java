package io.github.NationArchitect.controller.savemanager;

/**
 * Server-side authentication controller for REST endpoints.
 */
public class AuthController {

    private final DatabaseManager databaseManager;

    public AuthController(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public AuthResponse register(String username, String password) {
        Account account = databaseManager.register(username, password);
        if (account == null) {
            return null;
        }
        String token = TokenService.generateToken(account.getUserID(), account.getUsername());
        return new AuthResponse(account.getUserID(), token);
    }

    public AuthResponse login(String username, String password) {
        Account account = databaseManager.login(username, password);
        if (account == null) {
            return null;
        }
        String token = TokenService.generateToken(account.getUserID(), account.getUsername());
        return new AuthResponse(account.getUserID(), token);
    }
}
