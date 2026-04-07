package com.nationarchitect.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nationarchitect.game.Main;
import com.nationarchitect.game.ui.AnimatedMenuButton;

public class MainMenuScreen extends BaseScreen {
    protected AnimatedMenuButton newGameBtn;
    protected AnimatedMenuButton loadGameBtn;
    protected AnimatedMenuButton settingsBtn;
    protected AnimatedMenuButton creditsBtn;
    protected AnimatedMenuButton quitBtn;
    private Skin localSkin;
    private Texture backgroundTexture; 

    public MainMenuScreen(Main game) {
        super(game);
        localSkin = new Skin(Gdx.files.internal("button1.json"), new com.badlogic.gdx.graphics.g2d.TextureAtlas(Gdx.files.internal("button1.atlas")));
        backgroundTexture = new Texture(Gdx.files.internal("backgrounds/main menu screen.png"));
        buildLayout();
        addListeners();
    }

    @Override
public void buildLayout() {
    Image backgroundImage = new Image(backgroundTexture);
    backgroundImage.setFillParent(true);
    stage.addActor(backgroundImage);

    Table root = new Table();
    root.setFillParent(true);
    stage.addActor(root);

    Table buttonTable = new Table();

    newGameBtn = new AnimatedMenuButton("NEW GAME", localSkin);
    loadGameBtn = new AnimatedMenuButton("LOAD GAME", localSkin);
    settingsBtn = new AnimatedMenuButton("SETTINGS", localSkin);
    creditsBtn = new AnimatedMenuButton("CREDITS", localSkin);
    quitBtn = new AnimatedMenuButton("QUIT", localSkin);

    buttonTable.add(newGameBtn).size(300, 60).left().padBottom(10).row();
    buttonTable.add(loadGameBtn).size(300, 60).left().padBottom(10).row();
    buttonTable.add(settingsBtn).size(300, 60).left().padBottom(10).row();
    buttonTable.add(creditsBtn).size(300, 60).left().padBottom(10).row();
    buttonTable.add(quitBtn).size(300, 60).left();

    root.add(buttonTable).expand().bottom().left().padLeft(80).padBottom(100);
}

    @Override
public void addListeners() {
    newGameBtn.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            game.setScreen(new NewGameScreen(game));
        }
    });

    loadGameBtn.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            game.setScreen(new LoadGameScreen(game));
        }
    });

    creditsBtn.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            game.setScreen(new CreditsScreen(game)); 
        }
    });

    settingsBtn.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            game.setScreen(new SettingsScreen(game, MainMenuScreen.this)); 
        }
    });

    quitBtn.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            Gdx.app.exit();
        }
    });
}

    @Override
    public void dispose() {
        super.dispose();
        if (localSkin != null) localSkin.dispose();
        if (backgroundTexture != null) backgroundTexture.dispose(); 
    }
}