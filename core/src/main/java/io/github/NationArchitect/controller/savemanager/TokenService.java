package io.github.NationArchitect.controller.savemanager;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Small stateless token helper suitable for simple internal REST authentication.
 */
public final class TokenService {

    private static final String SECRET_KEY = "nationarchitect-save-secret";

    private TokenService() {
    }

    public static String generateToken(String userId, String username) {
        long issuedAt = System.currentTimeMillis();
        String payload = userId + ":" + username + ":" + issuedAt;
        String signature = Integer.toHexString((payload + ":" + SECRET_KEY).hashCode());
        String rawToken = payload + ":" + signature;
        return Base64.getUrlEncoder().withoutPadding().encodeToString(rawToken.getBytes(StandardCharsets.UTF_8));
    }

    public static boolean validateToken(String token) {
        try {
            String decoded = decode(token);
            String[] parts = decoded.split(":");
            if (parts.length != 4) {
                return false;
            }
            String payload = parts[0] + ":" + parts[1] + ":" + parts[2];
            String expectedSignature = Integer.toHexString((payload + ":" + SECRET_KEY).hashCode());
            return expectedSignature.equals(parts[3]);
        } catch (Exception exception) {
            return false;
        }
    }

    public static String extractUserId(String token) {
        if (!validateToken(token)) {
            return null;
        }
        return decode(token).split(":")[0];
    }

    public static String extractUsername(String token) {
        if (!validateToken(token)) {
            return null;
        }
        return decode(token).split(":")[1];
    }

    private static String decode(String token) {
        return new String(Base64.getUrlDecoder().decode(token), StandardCharsets.UTF_8);
    }
}
