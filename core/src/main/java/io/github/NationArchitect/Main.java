package io.github.NationArchitect;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.NationArchitect.controller.Settings;
import io.github.NationArchitect.controller.SettingsSaveManager;
import io.github.NationArchitect.controller.savemanager.AuthService;
import io.github.NationArchitect.controller.savemanager.SaveDevServer;
import io.github.NationArchitect.screens.AuthScreen;
import io.github.NationArchitect.screens.MainMenuScreen;

import java.io.IOException;

public class Main extends Game {
    private static final String DEFAULT_CLOUD_API = "http://127.0.0.1:8085";
    private static final String DEFAULT_CLOUD_PASSWORD = "nationarchitect";
    private static final String MENU_MUSIC_PATH = "audio/Whispered_Whispers.mp3";

    private SpriteBatch batch;
    private Settings settings;
    private SettingsSaveManager settingsSaveManager;
    private AuthService authService;
    private SaveDevServer.ServerHandle saveDevServerHandle;
    private Music menuMusic;

    @Override
    public void create() {
        batch = new SpriteBatch();
        settingsSaveManager = new SettingsSaveManager();
        settings = settingsSaveManager.load();
        startEmbeddedCloudSaveServer();
        authService = new AuthService(DEFAULT_CLOUD_API);
        this.setScreen(new AuthScreen(this));
    }

    @Override
    public void render() {
        com.badlogic.gdx.utils.ScreenUtils.clear(0, 0, 0, 1);
        super.render();
    }

    public SpriteBatch getBatch() { return batch; }

    public Settings getSettings() {
        return settings;
    }

    public SettingsSaveManager getSettingsSaveManager() {
        return settingsSaveManager;
    }

    public AuthService getAuthService() {
        return authService;
    }

    public String getCloudApiBaseUrl() {
        return DEFAULT_CLOUD_API;
    }

    public boolean ensureCloudLogin(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }

        String normalizedUsername = username.trim();
        if (authService == null) {
            authService = new AuthService(DEFAULT_CLOUD_API);
        }

        if (authService.isLoggedIn() && normalizedUsername.equals(authService.getCurrentUser())) {
            return true;
        }

        if (authService.isLoggedIn()) {
            authService.logout();
        }

        return authService.login(normalizedUsername, DEFAULT_CLOUD_PASSWORD)
            || authService.register(normalizedUsername, DEFAULT_CLOUD_PASSWORD);
    }

    public boolean login(String username, String password) {
        if (authService == null) {
            authService = new AuthService(DEFAULT_CLOUD_API);
        }
        return authService.login(username, password);
    }

    public boolean login(String username) {
        return login(username, DEFAULT_CLOUD_PASSWORD);
    }

    public boolean register(String username, String password) {
        if (authService == null) {
            authService = new AuthService(DEFAULT_CLOUD_API);
        }
        return authService.register(username, password);
    }

    public boolean register(String username) {
        return register(username, DEFAULT_CLOUD_PASSWORD);
    }

    public void saveSettings() {
        settingsSaveManager.save(settings);
        applyMusicSettings();
    }

    public void playMenuMusic() {
        if (getMusicVolume() <= 0f) {
            stopMenuMusic();
            return;
        }

        if (menuMusic == null && Gdx.files.internal(MENU_MUSIC_PATH).exists()) {
            menuMusic = Gdx.audio.newMusic(Gdx.files.internal(MENU_MUSIC_PATH));
            menuMusic.setLooping(true);
        }

        if (menuMusic != null) {
            menuMusic.setVolume(getMusicVolume());
            if (!menuMusic.isPlaying()) {
                menuMusic.play();
            }
        }
    }

    public void stopMenuMusic() {
        if (menuMusic != null && menuMusic.isPlaying()) {
            menuMusic.stop();
        }
    }

    public void setMusicVolume(float volume) {
        if (settings == null) {
            return;
        }
        settings.setMusicRate(Math.round(Math.max(0f, Math.min(1f, volume)) * 100f));
        applyMusicSettings();
        settingsSaveManager.save(settings);
    }

    public float getMusicVolume() {
        if (settings == null) {
            return 0.7f;
        }
        return (float) Math.max(0.0, Math.min(1.0, settings.getMusicRate() / 100.0));
    }

    @Override
    public void dispose() {
        if (screen != null) screen.hide();
        if (settingsSaveManager != null && settings != null) {
            settingsSaveManager.save(settings);
        }
        if (menuMusic != null) {
            menuMusic.dispose();
        }
        if (saveDevServerHandle != null) {
            saveDevServerHandle.close();
        }
        batch.dispose();
    }

    private void startEmbeddedCloudSaveServer() {
        try {
            saveDevServerHandle = SaveDevServer.start(8085, "mongodb://127.0.0.1:27017", "nationarchitect");
        } catch (IOException ignored) {
            saveDevServerHandle = null;
        }
    }

    private void applyMusicSettings() {
        if (getMusicVolume() <= 0f) {
            stopMenuMusic();
            return;
        }
        if (menuMusic != null) {
            menuMusic.setVolume(getMusicVolume());
        }
    }
}
