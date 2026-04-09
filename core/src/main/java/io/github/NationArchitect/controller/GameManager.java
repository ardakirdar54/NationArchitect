package io.github.NationArchitect.controller;

import io.github.NationArchitect.controller.WinLoseManager.GameResult;
import io.github.NationArchitect.controller.savemanager.AuthService;
import io.github.NationArchitect.controller.savemanager.CloudSaveManager;
import io.github.NationArchitect.controller.savemanager.JsonSaveManager;
import io.github.NationArchitect.controller.savemanager.SaveData;
import io.github.NationArchitect.model.Effect.PopUpChoice;
import io.github.NationArchitect.model.Effect.PopUpEvent;
import io.github.NationArchitect.model.component.Component;
import io.github.NationArchitect.model.economy.CountryEconomy;
import io.github.NationArchitect.model.economy.RegionEconomy;
import io.github.NationArchitect.model.economy.Tax;
import io.github.NationArchitect.model.land.Country;
import io.github.NationArchitect.model.land.Region;
import io.github.NationArchitect.model.population.Age;
import io.github.NationArchitect.model.population.Gender;
import io.github.NationArchitect.model.population.Population;
import io.github.NationArchitect.model.social.SocialMediaComment;
import io.github.NationArchitect.modules.GameMap;
import io.github.NationArchitect.modules.UIBuilding;
import io.github.NationArchitect.modules.UIRegion;

import java.sql.Date;
import java.util.EnumMap;

public class GameManager {

    private static final int DEFAULT_SAVE_SLOT = 0;
    private static final int DEFAULT_MAX_SLOTS = 10;
    private static final long DEFAULT_AUTOSAVE_INTERVAL_MS = 60_000L;
    private static final String DEFAULT_VERSION = "1.0.0";
    private static final String DEFAULT_CLOUD_API = "http://127.0.0.1:8085";

    private final GameMap gameMap;
    private final TimeManager timeManager;
    private final WinLoseManager winLoseManager;
    private final JsonSaveManager jsonSaveManager;
    private final CloudSaveManager cloudSaveManager;
    private final PopUpEventGenerator popUpEventGenerator;
    private final SocialMediaGenerator socialMediaGenerator;
    private final Settings settings;
    private final SettingsSaveManager settingsSaveManager;

    private Country country;
    private PopUpEvent activeEvent;
    private SaveData saveData;
    private boolean isGameOver;
    private GameResult gameResult;
    private float playTime;

    public GameManager(GameMap gameMap) {
        this(gameMap, createCountry(gameMap, "NationArchitect"), null, null, null);
    }

    public GameManager(GameMap gameMap, Country country) {
        this(gameMap, country, null, null, null);
    }

    public GameManager(GameMap gameMap, Country country, Settings settings) {
        this(gameMap, country, settings, null, null);
    }

    public GameManager(GameMap gameMap, String countryName, Settings settings, String cloudApiBaseUrl, AuthService authService) {
        this(gameMap, createCountry(gameMap, countryName), settings, cloudApiBaseUrl, authService);
    }

    public GameManager(GameMap gameMap, Country country, Settings settings, String cloudApiBaseUrl, AuthService authService) {
        this.gameMap = gameMap;
        this.country = country == null ? createCountry(gameMap, "NationArchitect") : country;
        this.settingsSaveManager = new SettingsSaveManager();
        this.settings = settings == null ? settingsSaveManager.load() : settings;
        this.jsonSaveManager = new JsonSaveManager(DEFAULT_MAX_SLOTS, DEFAULT_AUTOSAVE_INTERVAL_MS);
        this.cloudSaveManager = authService == null
                ? null
                : new CloudSaveManager(
                    cloudApiBaseUrl == null || cloudApiBaseUrl.trim().isEmpty() ? DEFAULT_CLOUD_API : cloudApiBaseUrl,
                    authService,
                    DEFAULT_MAX_SLOTS,
                    DEFAULT_AUTOSAVE_INTERVAL_MS
                );
        DataLoader dataLoader = new DataLoader();
        this.popUpEventGenerator = new PopUpEventGenerator(dataLoader);
        this.socialMediaGenerator = new SocialMediaGenerator(dataLoader);
        this.timeManager = new TimeManager(this, this.country);
        this.winLoseManager = new WinLoseManager(this.country);
        this.isGameOver = false;
        this.gameResult = GameResult.CONTINUE;
        this.playTime = 0f;
    }

    public void startGame() {
        isGameOver = false;
        gameResult = GameResult.CONTINUE;
        timeManager.start();
    }

    public void update(float delta) {
        if (isGameOver) {
            return;
        }

        playTime += delta;
        timeManager.update(delta);
    }

    public void simulationStep() {
        if (isGameOver || country == null) {
            return;
        }

        country.update();
        activeEvent = popUpEventGenerator.generateEvent(country);
        GameResult result = winLoseManager.checkGameState(getCurrentDate());
        if (result != GameResult.CONTINUE) {
            endGame(result);
        }
    }

    public void applyEconomyStep(float secondsElapsed) {
        if (isGameOver || country == null || !(country.getEconomy() instanceof CountryEconomy)) {
            return;
        }

        CountryEconomy countryEconomy = (CountryEconomy) country.getEconomy();
        countryEconomy.update(country);

        double speedMultiplier = timeManager.getCurrentSpeed().multiplier;
        double treasuryDelta = (countryEconomy.getBalance() / timeManager.getMonthDuration()) * secondsElapsed * speedMultiplier;
        countryEconomy.applyTreasuryDelta(treasuryDelta);
    }

    public void handleRegionSelection() {
    }

    public UIRegion handleRegionSelection(float x, float y) {
        if (gameMap == null) {
            return null;
        }
        return gameMap.getRegionAt(x, y);
    }

    public PopUpEvent createEvent() {
        activeEvent = popUpEventGenerator.generateEvent(country);
        return activeEvent;
    }

    public void resolveEvent(PopUpEvent event) {
        if (event == null || event.getChoices() == null || event.getChoices().isEmpty()) {
            activeEvent = null;
            return;
        }
        resolveEvent(event, event.getChoices().get(0));
    }

    public void resolveEvent(PopUpEvent event, PopUpChoice choice) {
        if (event == null || choice == null || country == null) {
            activeEvent = null;
            return;
        }

        for (Region region : country.getRegions()) {
            if (region != null) {
                region.addTemporaryEffect(choice, Math.max(choice.getDuration(), 1));
            }
        }
        country.update();
        activeEvent = null;
    }

    public SocialMediaComment generatePost() {
        return socialMediaGenerator.generateComment(country);
    }

    public boolean saveGame() {
        return saveGame(DEFAULT_SAVE_SLOT);
    }

    public boolean saveGame(int slot) {
        saveData = SaveData.fromCountry(
            country,
            gameMap == null ? null : gameMap.getMapName(),
            playTime,
            DEFAULT_VERSION,
            "snapshots/slot-" + slot + ".png"
        );
        return jsonSaveManager.save(saveData, slot);
    }

    public boolean saveGameToCloud(int slot) {
        if (cloudSaveManager == null) {
            return false;
        }
        saveData = SaveData.fromCountry(
            country,
            gameMap == null ? null : gameMap.getMapName(),
            playTime,
            DEFAULT_VERSION,
            "snapshots/slot-" + slot + ".png"
        );
        return cloudSaveManager.save(saveData, slot);
    }

    public SaveData loadGame(int slot) {
        SaveData loaded = jsonSaveManager.load(slot);
        if (loaded == null) {
            return null;
        }
        applyLoadedSave(loaded);
        return loaded;
    }

    public SaveData loadGameFromCloud(int slot) {
        if (cloudSaveManager == null) {
            return null;
        }
        SaveData loaded = cloudSaveManager.load(slot);
        if (loaded == null) {
            return null;
        }
        applyLoadedSave(loaded);
        return loaded;
    }

    public void applyLoadedSave(SaveData loaded) {
        if (loaded == null) {
            return;
        }
        this.saveData = loaded;
        if (this.country == null) {
            this.country = loaded.restoreCountry();
        }
        this.playTime = (float) loaded.getPlayTime();
        refreshLoadedCountryState();
    }

    public void pauseGame() {
        timeManager.setCurrentSpeed(TimeManager.GameSpeed.PAUSED);
    }

    public void resumeGame() {
        timeManager.start();
    }

    public void speedUpGame() {
        timeManager.speedUp();
    }

    public void slowDownGame() {
        TimeManager.GameSpeed currentSpeed = timeManager.getCurrentSpeed();
        if (currentSpeed == TimeManager.GameSpeed.VERY_FAST) {
            timeManager.setCurrentSpeed(TimeManager.GameSpeed.FAST);
        } else if (currentSpeed == TimeManager.GameSpeed.FAST) {
            timeManager.setCurrentSpeed(TimeManager.GameSpeed.NORMAL);
        } else if (currentSpeed == TimeManager.GameSpeed.NORMAL) {
            timeManager.setCurrentSpeed(TimeManager.GameSpeed.PAUSED);
        }
    }

    public void endGame(GameResult result) {
        gameResult = result;
        isGameOver = result != GameResult.CONTINUE;
        timeManager.stop();
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public TimeManager getTimeManager() {
        return timeManager;
    }

    public WinLoseManager getWinLoseManager() {
        return winLoseManager;
    }

    public JsonSaveManager getJsonSaveManager() {
        return jsonSaveManager;
    }

    public CloudSaveManager getCloudSaveManager() {
        return cloudSaveManager;
    }

    public PopUpEventGenerator getPopUpEventGenerator() {
        return popUpEventGenerator;
    }

    public SocialMediaGenerator getSocialMediaGenerator() {
        return socialMediaGenerator;
    }

    public Settings getSettings() {
        return settings;
    }

    public SettingsSaveManager getSettingsSaveManager() {
        return settingsSaveManager;
    }

    public Country getCountry() {
        return country;
    }

    public SaveData getSaveData() {
        return saveData;
    }

    public PopUpEvent getActiveEvent() {
        return activeEvent;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public GameResult getGameResult() {
        return gameResult;
    }

    public float getPlayTime() {
        return playTime;
    }

    private Date getCurrentDate() {
        int month = Math.max(1, timeManager.getCurrentMonth());
        int day = Math.max(1, timeManager.getCurrentDay());
        return Date.valueOf(String.format("%04d-%02d-%02d", timeManager.getCurrentYear(), month, day));
    }

    private static Country createCountry(GameMap gameMap, String countryName) {
        int regionCount = gameMap != null && gameMap.getRegions() != null ? gameMap.getRegions().size : 0;
        Region[] regions = new Region[Math.max(regionCount, 1)];
        if (gameMap != null && gameMap.getRegions() != null) {
            for (int index = 0; index < gameMap.getRegions().size; index++) {
                UIRegion uiRegion = gameMap.getRegions().get(index);
                regions[index] = uiRegion == null ? null : uiRegion.getBackingRegion();
            }
        } else {
            regions[0] = new UIRegion(0, "Region-1", 0, 0, 100, 100).getBackingRegion();
        }

        Population countryPopulation = createCountryPopulation(regions);
        Country country = new Country(
            countryName == null || countryName.trim().isEmpty() ? "NationArchitect" : countryName.trim(),
            new CountryEconomy(new Tax()),
            countryPopulation
        );
        country.setRegions(regions);
        country.calculatePopulation();
        country.update();
        return country;
    }

    private static Population createCountryPopulation(Region[] regions) {
        EnumMap<Age, Integer> ageDistribution = new EnumMap<>(Age.class);
        EnumMap<Gender, Integer> genderDistribution = new EnumMap<>(Gender.class);

        for (Age age : Age.values()) {
            ageDistribution.put(age, 0);
        }
        for (Gender gender : Gender.values()) {
            genderDistribution.put(gender, 0);
        }

        if (regions != null) {
            for (Region region : regions) {
                if (region == null) {
                    continue;
                }
                for (Age age : Age.values()) {
                    ageDistribution.put(age, ageDistribution.get(age) + region.getPopulation().getAgeDistribution(age));
                }
                for (Gender gender : Gender.values()) {
                    genderDistribution.put(gender,
                        genderDistribution.get(gender) + region.getMutablePopulation().getGenderDistribution().get(gender));
                }
            }
        }

        return new Population()
            .setAgeDistribution(ageDistribution)
            .setGenderDistribution(genderDistribution);
    }

    private void refreshLoadedCountryState() {
        if (country == null) {
            return;
        }

        Region[] regions = country.getRegions();
        if (regions != null) {
            for (Region region : regions) {
                if (region == null) {
                    continue;
                }
                if (region.getComponents() != null) {
                    for (Component component : region.getComponents().values()) {
                        if (component != null) {
                            component.update();
                        }
                    }
                }
                region.calculateLandValue();
                if (region.getEconomy() instanceof RegionEconomy) {
                    ((RegionEconomy) region.getEconomy()).update(region);
                }
            }
        }

        country.calculatePopulation();
        if (country.getEconomy() instanceof CountryEconomy) {
            ((CountryEconomy) country.getEconomy()).update(country);
        }
    }
}
