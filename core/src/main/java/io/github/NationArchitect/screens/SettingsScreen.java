package io.github.NationArchitect.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import io.github.NationArchitect.Main;
import io.github.NationArchitect.ui.SettingsPanel;

public class SettingsScreen extends BaseScreen {
    private Texture backgroundTexture;
    private SettingsPanel settingsPanel;
    private BaseScreen parentScreen;
    private Skin localSkin;

    public SettingsScreen(Main game, BaseScreen parentScreen) {
        super(game);
        this.parentScreen = parentScreen; 

        localSkin = new Skin(Gdx.files.internal("button1.json"), new com.badlogic.gdx.graphics.g2d.TextureAtlas(Gdx.files.internal("button1.atlas"))); 
        backgroundTexture = new Texture(Gdx.files.internal("backgrounds/pause frame.png"));

        buildLayout();
    }

    @Override
public void buildLayout() {
    stage.clear();

    Image bg = new Image(backgroundTexture);
    bg.setFillParent(true);
    stage.addActor(bg);

    settingsPanel = new SettingsPanel(this); 
    settingsPanel.buildLayout();         
    settingsPanel.show();

    Table root = new Table();
    root.setFillParent(true);
    root.add(settingsPanel).size(400, 300).center();
    stage.addActor(root);
}

    @Override
public void render(float delta) {
    super.render(delta);
    if (settingsPanel != null && !settingsPanel.isOpen) {
        game.setScreen(parentScreen);
    }
}

    @Override
    public Skin getSkin() { return localSkin; }

    @Override
    public void addListeners() {}

    @Override
    public void dispose() {
        super.dispose();
        if (backgroundTexture != null) backgroundTexture.dispose();
        if (localSkin != null) localSkin.dispose();
    }
}
