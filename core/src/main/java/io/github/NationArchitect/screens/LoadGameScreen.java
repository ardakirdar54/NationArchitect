package io.github.NationArchitect.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import io.github.NationArchitect.Main;
import io.github.NationArchitect.modules.GameMap;
import io.github.NationArchitect.ui.AnimatedMenuButton;
import io.github.NationArchitect.ui.SaveSlotPanel;

public class LoadGameScreen extends BaseScreen {
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

    public LoadGameScreen(Main game) {
        super(game);
        localSkin = new Skin(Gdx.files.internal("button1.json"), new com.badlogic.gdx.graphics.g2d.TextureAtlas(Gdx.files.internal("button1.atlas")));
        
        backgroundTexture = new Texture(Gdx.files.internal("backgrounds/load game screen.png"));
        
        saveSlots = new Array<>();
        loadSaveFiles();
        buildLayout();
        addListeners();
    }

    @Override
    public void buildLayout() {
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        Table statsTable = new Table();
        stabilityLabel = new Label("Stability: 85%", localSkin);
        playTimeLabel = new Label("Time: 12h 40m", localSkin);
        statsTable.add(playTimeLabel).padLeft(85).padBottom(45); 
        statsTable.add(stabilityLabel).padLeft(165).padBottom(45);
        statsTable.setFillParent(true);
        statsTable.bottom().left();
        stage.addActor(statsTable);

        loadSelectedBtn = new AnimatedMenuButton("LOAD", localSkin);
        deleteBtn = new AnimatedMenuButton("DELETE", localSkin);
        backBtn = new AnimatedMenuButton("BACK TO MAIN MENU", localSkin);

        float btnWidth = 350f;
        float btnHeight = 65f;
    
        float sWidth = stage.getViewport().getWorldWidth();
        float sHeight = stage.getViewport().getWorldHeight();


        float posX = sWidth - btnWidth - 60f; 

        backBtn.setSize(btnWidth, btnHeight);
        backBtn.setPosition(posX, (sHeight / 2) - 100f);

        deleteBtn.setSize(btnWidth, btnHeight);
        deleteBtn.setPosition(posX, (sHeight / 2) - 25f);

        loadSelectedBtn.setSize(btnWidth, btnHeight);
        loadSelectedBtn.setPosition(posX, (sHeight / 2) + 50f);

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
                if (selectedSlot != null) {
                    System.out.println("Loading save: " + selectedSlot.getSaveName());
                }
            }
        });

        for (final SaveSlotPanel slot : saveSlots) {
            slot.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedSlot = slot;
                    updatePreview(slot.getMap());
                }
            });
        }
    }

    public void loadSaveFiles() { //TO DO 
    }

    public void updatePreview(GameMap map) {
        if (map != null) {
            uploadTexture(map);
            previewImage.setDrawable(new TextureRegionDrawable(previewTexture));
            stabilityLabel.setText("Stability: 85%");
            playTimeLabel.setText("Time: 12h 40m");
        }
    }

    public void uploadTexture(GameMap map) {
        if (previewTexture != null) previewTexture.dispose();
        previewTexture = new Texture(Gdx.files.internal("maps/" + map.getName() + ".png"));
    }

    public void onBackClicked() {
        game.setScreen(new MainMenuScreen(game));
    }

    @Override
    public void dispose() {
        super.dispose();
        if (previewTexture != null) previewTexture.dispose();
        if (backgroundTexture != null) backgroundTexture.dispose(); 
        if (localSkin != null) localSkin.dispose();
    }
}
