package io.github.NationArchitect.controller.savemanager;

import com.badlogic.gdx.utils.Json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * REST client for username-based registration and login.
 */
public class AuthService {

    private final String apiBaseURL;
    private String currentUser;
    private String authToken;
    private boolean loggedIn;
    private String lastError;

    public AuthService(String apiBaseURL) {
        this.apiBaseURL = apiBaseURL;
    }

    public boolean register(String username, String password) {
        AuthResponse response = postAuth("/auth/register", username, password);
        if (response == null || response.getToken() == null) {
            return false;
        }
        currentUser = username;
        authToken = response.getToken();
        loggedIn = true;
        return true;
    }

    public boolean login(String username, String password) {
        AuthResponse response = postAuth("/auth/login", username, password);
        if (response == null || response.getToken() == null) {
            return false;
        }
        currentUser = username;
        authToken = response.getToken();
        loggedIn = true;
        return true;
    }

    public void logout() {
        currentUser = null;
        authToken = null;
        loggedIn = false;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public String getLastError() {
        return lastError;
    }

    String sendRequest(String method, String endpoint, String body, String bearerToken) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(apiBaseURL + endpoint).openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setRequestProperty("Accept", "application/json");
        if (bearerToken != null && !bearerToken.isEmpty()) {
            connection.setRequestProperty("Authorization", "Bearer " + bearerToken);
        }
        if (body != null) {
            connection.setDoOutput(true);
            byte[] payload = body.getBytes(StandardCharsets.UTF_8);
            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(payload);
            }
        }

        int statusCode = connection.getResponseCode();
        InputStream stream = statusCode >= 200 && statusCode < 300
            ? connection.getInputStream()
            : connection.getErrorStream();

        String responseBody = readAll(stream);
        if (statusCode < 200 || statusCode >= 300) {
            throw new IOException("Request failed with status " + statusCode + ": " + responseBody);
        }
        return responseBody;
    }

    private AuthResponse postAuth(String endpoint, String username, String password) {
        try {
            Json json = new Json();
            Credentials credentials = new Credentials(username, password);
            String responseBody = sendRequest("POST", endpoint, json.toJson(credentials), null);
            lastError = null;
            return responseBody == null || responseBody.isEmpty()
                ? null
                : json.fromJson(AuthResponse.class, responseBody);
        } catch (IOException exception) {
            lastError = exception.getMessage();
            return null;
        }
    }

    private String readAll(InputStream stream) throws IOException {
        if (stream == null) {
            return "";
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line).append('\n');
        }
        return builder.toString();
    }

    private static class Credentials {
        private String username;
        private String password;

        Credentials(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }
}
