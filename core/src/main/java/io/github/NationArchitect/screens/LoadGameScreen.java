package io.github.NationArchitect.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import io.github.NationArchitect.Main;
import io.github.NationArchitect.controller.savemanager.CloudSaveManager;
import io.github.NationArchitect.controller.savemanager.JsonSaveManager;
import io.github.NationArchitect.controller.savemanager.SaveData;
import io.github.NationArchitect.modules.GameMap;
import io.github.NationArchitect.ui.AnimatedMenuButton;
import io.github.NationArchitect.ui.SaveSlotPanel;

public class LoadGameScreen extends BaseScreen {

    private static final int SLOT_COUNT = 3;
    private static final long AUTOSAVE_INTERVAL_MS = 60_000L;

    private Array<SaveSlotPanel> saveSlots;
    private AnimatedMenuButton backBtn;
    private AnimatedMenuButton loadSelectedBtn;
    private AnimatedMenuButton deleteBtn;
    private Texture previewTexture;
    private Texture backgroundTexture;
    private Image previewImage;
    private Label stabilityLabel;
    private Label playTimeLabel;
    private SaveSlotPanel selectedSlot;
    private Skin localSkin;
    private final Screen backScreen;
    private final boolean cloudAvailable;

    public LoadGameScreen(Main game) {
        this(game, null);
    }

    public LoadGameScreen(Main game, Screen backScreen) {
        super(game);
        this.backScreen = backScreen;
        this.cloudAvailable = game.getAuthService() != null && game.getAuthService().isLoggedIn();
        localSkin = new Skin(
            Gdx.files.internal("button1.json"),
            new com.badlogic.gdx.graphics.g2d.TextureAtlas(Gdx.files.internal("button1.atlas"))
        );
        backgroundTexture = new Texture(Gdx.files.internal("backgrounds/load game screen.png"));
        saveSlots = new Array<>();
        loadSaveFiles();
        buildLayout();
        addListeners();
        if (selectedSlot != null) {
            updatePreview(selectedSlot);
        }
    }

    @Override
    public void buildLayout() {
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        Table slotsTable = new Table();
        slotsTable.top().left();
        slotsTable.pad(80, 60, 0, 0);
        slotsTable.setFillParent(true);

        for (SaveSlotPanel slot : saveSlots) {
            slotsTable.add(slot).fillX().height(80).padBottom(12).row();
        }
        stage.addActor(slotsTable);

        previewImage = new Image(createPlaceholderPreview());
        previewImage.setBounds(80f, 110f, 620f, 300f);
        stage.addActor(previewImage);

        Table statsTable = new Table();
        stabilityLabel = new Label("Country: --", localSkin);
        playTimeLabel = new Label("Date: --", localSkin);
        statsTable.add(playTimeLabel).left().padRight(90f);
        statsTable.add(stabilityLabel).left();
        statsTable.pack();
        statsTable.setPosition(85f, 425f);
        stage.addActor(statsTable);

        loadSelectedBtn = new AnimatedMenuButton("LOAD", localSkin);
        deleteBtn = new AnimatedMenuButton("DELETE", localSkin);
        backBtn = new AnimatedMenuButton("BACK TO MAIN MENU", localSkin);

        float btnWidth = 350f;
        float btnHeight = 65f;
        float posX = WIDTH - btnWidth - 60f;

        backBtn.setSize(btnWidth, btnHeight);
        backBtn.setPosition(posX, HEIGHT / 2f - 100f);
        deleteBtn.setSize(btnWidth, btnHeight);
        deleteBtn.setPosition(posX, HEIGHT / 2f - 25f);
        loadSelectedBtn.setSize(btnWidth, btnHeight);
        loadSelectedBtn.setPosition(posX, HEIGHT / 2f + 50f);

        stage.addActor(loadSelectedBtn);
        stage.addActor(deleteBtn);
        stage.addActor(backBtn);
    }

    @Override
    public void addListeners() {
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onBackClicked();
            }
        });

        loadSelectedBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedSlot == null || selectedSlot.getSaveData() == null) {
                    return;
                }
                game.setScreen(new GameScreen(game, selectedSlot.getSaveData()));
            }
        });

        deleteBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedSlot == null || selectedSlot.getSaveData() == null) {
                    return;
                }
                if (selectedSlot.isCloud()) {
                    if (game.getAuthService() != null && game.getAuthService().isLoggedIn()) {
                        new CloudSaveManager(game.getCloudApiBaseUrl(), game.getAuthService(), SLOT_COUNT, AUTOSAVE_INTERVAL_MS)
                            .deleteSave(selectedSlot.getSlot());
                    }
                } else {
                    new JsonSaveManager(SLOT_COUNT, AUTOSAVE_INTERVAL_MS).deleteSave(selectedSlot.getSlot());
                }
                game.setScreen(new LoadGameScreen(game, backScreen));
            }
        });

        for (SaveSlotPanel slot : saveSlots) {
            slot.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedSlot = slot;
                    updatePreview(slot);
                }
            });
            slot.getSelectBtn().addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedSlot = slot;
                    updatePreview(slot);
                }
            });
        }
    }

    public void loadSaveFiles() {
        saveSlots.clear();

        JsonSaveManager localManager = new JsonSaveManager(SLOT_COUNT, AUTOSAVE_INTERVAL_MS);
        for (int slot = 0; slot < SLOT_COUNT; slot++) {
            SaveData data = localManager.load(slot);
            String countryName = data != null && data.getCountryName() != null && !data.getCountryName().trim().isEmpty()
                ? data.getCountryName()
                : "--- EMPTY ---";
            String saveDate = data != null ? formatTimestamp(data) : "";
            GameMap map = data != null ? resolveMap(data) : null;
            saveSlots.add(new SaveSlotPanel(this, countryName, saveDate, map, slot, false, data));
        }

        CloudSaveManager cloudManager = cloudAvailable
            ? new CloudSaveManager(game.getCloudApiBaseUrl(), game.getAuthService(), SLOT_COUNT, AUTOSAVE_INTERVAL_MS)
            : null;
        if (cloudManager != null) {
            for (int slot = 0; slot < SLOT_COUNT; slot++) {
                SaveData data = cloudManager.load(slot);
                String countryName = data == null || data.getCountryName() == null || data.getCountryName().trim().isEmpty()
                    ? "--- EMPTY ---"
                    : data.getCountryName();
                String saveDate = data == null ? "" : formatTimestamp(data);
                saveSlots.add(new SaveSlotPanel(this, countryName, saveDate, data == null ? null : resolveMap(data), slot, true, data));
            }
        }

        selectedSlot = null;
        for (SaveSlotPanel slot : saveSlots) {
            if (slot.getSaveData() != null) {
                selectedSlot = slot;
                break;
            }
        }
    }

    public void updatePreview(SaveSlotPanel slot) {
        if (slot == null || slot.getMap() == null || slot.getSaveData() == null) {
            stabilityLabel.setText("Country: --");
            playTimeLabel.setText("Date: -- | Play Time: --");
            previewImage.setDrawable(new TextureRegionDrawable(new TextureRegion(createPlaceholderPreview())));
            return;
        }

        uploadTexture(slot.getMap());
        if (previewTexture != null) {
            previewImage.setDrawable(new TextureRegionDrawable(new TextureRegion(previewTexture)));
        }
        stabilityLabel.setText("Country: " + slot.getSaveName());
        playTimeLabel.setText("Date: " + formatTimestamp(slot.getSaveData()) + " | Play Time: " + formatPlayTime(slot.getSaveData().getPlayTime()));
    }

    public void uploadTexture(GameMap map) {
        if (previewTexture != null) {
            previewTexture.dispose();
        }
        String mapsPath = "maps/" + map.getName() + ".png";
        String mapPath = "map/" + map.getName() + ".png";
        if (Gdx.files.internal(mapsPath).exists()) {
            previewTexture = new Texture(Gdx.files.internal(mapsPath));
        } else if (Gdx.files.internal(mapPath).exists()) {
            previewTexture = new Texture(Gdx.files.internal(mapPath));
        } else if (Gdx.files.internal("map/turkeyRegion.png").exists()) {
            previewTexture = new Texture(Gdx.files.internal("map/turkeyRegion.png"));
        } else {
            previewTexture = createPlaceholderPreview();
        }
    }

    public void onBackClicked() {
        if (backScreen != null) {
            game.setScreen(backScreen);
        } else {
            game.setScreen(new MainMenuScreen(game));
        }
    }

    private Texture createPlaceholderPreview() {
        Pixmap pixmap = new Pixmap(2, 2, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0.04f, 0.1f, 0.2f, 1f));
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    private GameMap resolveMap(SaveData data) {
        String mapName = data.getMapType();
        if (mapName == null || mapName.trim().isEmpty()) {
            mapName = "map1";
        }
        return new GameMap(mapName);
    }

    private String formatTimestamp(SaveData data) {
        return data.getSaveTimestamp() == null ? "Unknown date" : data.getSaveTimestamp().toString();
    }

    private String formatPlayTime(double playTimeSeconds) {
        int totalSeconds = (int) Math.round(playTimeSeconds);
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return minutes + "m " + seconds + "s";
    }

    @Override
    public void dispose() {
        super.dispose();
        if (previewTexture != null) previewTexture.dispose();
        if (backgroundTexture != null) backgroundTexture.dispose();
        if (localSkin != null) localSkin.dispose();
    }

    @Override
    public Skin getSkin() {
        return localSkin;
    }

    @Override
    public void show() {
        super.show();
        game.playMenuMusic();
    }
}

