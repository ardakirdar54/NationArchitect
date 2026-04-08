package io.github.NationArchitect.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.NationArchitect.Main;
import io.github.NationArchitect.ui.AnimatedMenuButton;

public class PauseScreen extends BaseScreen {

    private GameScreen gameScreen;

    private AnimatedMenuButton returnToGameBtn;
    private AnimatedMenuButton saveGameBtn;
    private AnimatedMenuButton loadGameBtn;
    private AnimatedMenuButton settingsBtn;
    private AnimatedMenuButton mainMenuBtn;
    private AnimatedMenuButton exitToDesktop;

    private Texture frameTexture;
    private Skin localSkin;

    public PauseScreen(Main game, GameScreen gameScreen) {
        super(game);
        this.gameScreen = gameScreen;
        localSkin = new Skin(Gdx.files.internal("button1.json"), new com.badlogic.gdx.graphics.g2d.TextureAtlas(Gdx.files.internal("button1.atlas")));
        buildLayout();
        addListeners();
    }

    @Override
    public void buildLayout() {
        frameTexture = new Texture(Gdx.files.internal("backgrounds/pause frame.png"));
        Image frameImage = new Image(frameTexture);
        frameImage.setFillParent(true);
        stage.addActor(frameImage);

        returnToGameBtn = new AnimatedMenuButton("RETURN TO GAME", localSkin);
        saveGameBtn     = new AnimatedMenuButton("SAVE GAME",      localSkin);
        loadGameBtn     = new AnimatedMenuButton("LOAD GAME",      localSkin);
        settingsBtn     = new AnimatedMenuButton("SETTINGS",       localSkin);
        mainMenuBtn     = new AnimatedMenuButton("MAIN MENU",      localSkin);
        exitToDesktop   = new AnimatedMenuButton("EXIT TO DESKTOP",localSkin);

        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        Table menu = new Table();
        menu.add(returnToGameBtn).size(300, 50).padBottom(10).row();
        menu.add(saveGameBtn)    .size(300, 50).padBottom(10).row();
        menu.add(loadGameBtn)    .size(300, 50).padBottom(10).row();
        menu.add(settingsBtn)    .size(300, 50).padBottom(10).row();
        menu.add(mainMenuBtn)    .size(300, 50).padBottom(10).row();
        menu.add(exitToDesktop)  .size(300, 50);

        root.add(menu).center();
    }

    @Override
    public void addListeners() {
        returnToGameBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(gameScreen);
            }
        });

        saveGameBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // TO DO: save logic
            }
        });

        loadGameBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // TO DO: load logic
            }
        });

        settingsBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingsScreen(game, PauseScreen.this));
            }
        });

        mainMenuBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        exitToDesktop.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void dispose() {
        super.dispose();
        if (frameTexture != null) frameTexture.dispose();
        if (localSkin != null) localSkin.dispose();
    }
}
