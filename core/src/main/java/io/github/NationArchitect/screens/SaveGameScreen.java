package io.github.NationArchitect.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import io.github.NationArchitect.controller.savemanager.SaveData;
import io.github.NationArchitect.modules.GameMap;
import io.github.NationArchitect.ui.AnimatedMenuButton;
import io.github.NationArchitect.ui.SaveSlotPanel;

public class SaveGameScreen extends BaseScreen {

    private static final int SAVE_SLOT_COUNT = 3;

    private final GameScreen gameScreen;
    private final Screen backScreen;
    private final Array<SaveSlotPanel> saveSlots;

    private Skin localSkin;
    private Texture backgroundTexture;
    private Texture previewTexture;
    private Image previewImage;
    private Label infoLabel;
    private SaveSlotPanel selectedSlot;
    private AnimatedMenuButton saveBtn;
    private AnimatedMenuButton backBtn;
    private final boolean cloudAvailable;

    public SaveGameScreen(Main game, GameScreen gameScreen, Screen backScreen) {
        super(game);
        this.gameScreen = gameScreen;
        this.backScreen = backScreen;
        this.saveSlots = new Array<>();
        this.cloudAvailable = game.getAuthService() != null && game.getAuthService().isLoggedIn();
        this.localSkin = new Skin(Gdx.files.internal("button1.json"), new TextureAtlas(Gdx.files.internal("button1.atlas")));
        this.backgroundTexture = new Texture(Gdx.files.internal("backgrounds/load game screen.png"));
        loadSaveSlots();
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

        Label titleLabel = new Label("SAVE GAME", localSkin);
        titleLabel.setFontScale(2f);
        titleLabel.setPosition(80f, HEIGHT - 120f);
        stage.addActor(titleLabel);

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

        infoLabel = new Label("Select a slot to save", localSkin);
        infoLabel.setPosition(85f, 425f);
        stage.addActor(infoLabel);

        saveBtn = new AnimatedMenuButton("SAVE TO SLOT", localSkin);
        backBtn = new AnimatedMenuButton("BACK", localSkin);

        float btnWidth = 350f;
        float btnHeight = 65f;
        float posX = WIDTH - btnWidth - 60f;

        saveBtn.setSize(btnWidth, btnHeight);
        saveBtn.setPosition(posX, HEIGHT / 2f + 15f);
        backBtn.setSize(btnWidth, btnHeight);
        backBtn.setPosition(posX, HEIGHT / 2f - 65f);

        stage.addActor(saveBtn);
        stage.addActor(backBtn);
    }

    @Override
    public void addListeners() {
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(backScreen == null ? gameScreen : backScreen);
            }
        });

        saveBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedSlot == null) {
                    return;
                }
                int slotIndex = selectedSlot.getSlot();
                boolean localSaved = false;
                boolean cloudSaved = false;
                if (selectedSlot.isCloud()) {
                    cloudSaved = cloudAvailable && gameScreen.getGameManager().saveGameToCloud(slotIndex);
                } else {
                    localSaved = gameScreen.getGameManager().saveGame(slotIndex);
                }
                Gdx.app.log("SaveGameScreen", "Save complete. slot=" + slotIndex + ", local=" + localSaved + ", cloud=" + cloudSaved);
                game.setScreen(new PauseScreen(game, gameScreen));
            }
        });

        for (SaveSlotPanel slot : saveSlots) {
            ClickListener selectListener = new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedSlot = slot;
                    updatePreview(slot);
                }
            };
            slot.addListener(selectListener);
            slot.getSelectBtn().addListener(selectListener);
        }
    }

    private void loadSaveSlots() {
        saveSlots.clear();
        for (int slot = 0; slot < SAVE_SLOT_COUNT; slot++) {
            SaveData data = gameScreen.getGameManager().getJsonSaveManager().load(slot);
            String countryName = data != null && data.getCountryName() != null && !data.getCountryName().trim().isEmpty()
                ? data.getCountryName()
                : "--- EMPTY ---";
            String saveDate = data != null && data.getSaveTimestamp() != null
                ? data.getSaveTimestamp().toString()
                : "";
            GameMap map = data != null ? new GameMap(resolveMapName(data)) : gameScreen.getGameMap();
            SaveSlotPanel panel = new SaveSlotPanel(this, countryName, saveDate, map, slot, false, data);
            saveSlots.add(panel);
            if (selectedSlot == null) {
                selectedSlot = panel;
            }
        }

        if (cloudAvailable && gameScreen.getGameManager().getCloudSaveManager() != null) {
            for (int slot = 0; slot < SAVE_SLOT_COUNT; slot++) {
                SaveData data = gameScreen.getGameManager().getCloudSaveManager().load(slot);
                String countryName = data != null && data.getCountryName() != null && !data.getCountryName().trim().isEmpty()
                    ? data.getCountryName()
                    : "--- EMPTY ---";
                String saveDate = data != null && data.getSaveTimestamp() != null
                    ? data.getSaveTimestamp().toString()
                    : "";
                GameMap map = data != null ? new GameMap(resolveMapName(data)) : gameScreen.getGameMap();
                SaveSlotPanel panel = new SaveSlotPanel(this, countryName, saveDate, map, slot, true, data);
                saveSlots.add(panel);
            }
        }
    }

    private void updatePreview(SaveSlotPanel slot) {
        if (slot == null) {
            infoLabel.setText("Select a slot to save");
            previewImage.setDrawable(new TextureRegionDrawable(new TextureRegion(createPlaceholderPreview())));
            return;
        }

        GameMap map = slot.getMap() == null ? gameScreen.getGameMap() : slot.getMap();
        if (previewTexture != null) {
            previewTexture.dispose();
        }
        previewTexture = loadPreviewTexture(map);
        previewImage.setDrawable(new TextureRegionDrawable(new TextureRegion(previewTexture)));
        String targetName = gameScreen.getGameManager().getCountry() == null
            ? "Unnamed Country"
            : gameScreen.getGameManager().getCountry().getName();
        String overwriteText = slot.getSaveData() == null ? "Empty slot" : "Overwrite " + slot.getSaveName();
        infoLabel.setText("Slot " + (slot.getSlot() + 1) + " | " + overwriteText + " | Save as " + targetName);
    }

    private Texture loadPreviewTexture(GameMap map) {
        String mapsPath = "maps/" + map.getName() + ".png";
        String mapPath = "map/" + map.getName() + ".png";
        if (Gdx.files.internal(mapsPath).exists()) {
            return new Texture(Gdx.files.internal(mapsPath));
        }
        if (Gdx.files.internal(mapPath).exists()) {
            return new Texture(Gdx.files.internal(mapPath));
        }
        if (Gdx.files.internal("map/turkeyRegion.png").exists()) {
            return new Texture(Gdx.files.internal("map/turkeyRegion.png"));
        }
        return createPlaceholderPreview();
    }

    private Texture createPlaceholderPreview() {
        Pixmap pixmap = new Pixmap(2, 2, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0.04f, 0.1f, 0.2f, 1f));
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    private String resolveMapName(SaveData data) {
        if (data == null || data.getMapType() == null || data.getMapType().trim().isEmpty()) {
            return "map1";
        }
        return data.getMapType();
    }

    @Override
    public void dispose() {
        super.dispose();
        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }
        if (previewTexture != null) {
            previewTexture.dispose();
        }
        if (localSkin != null) {
            localSkin.dispose();
        }
    }

    @Override
    public Skin getSkin() {
        return localSkin;
    }
}
