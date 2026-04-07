package com.nationarchitect.game.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nationarchitect.game.Main;
import com.nationarchitect.game.ui.AnimatedMenuButton;

public class CreditsScreen extends BaseScreen {
    private Texture backgroundTexture;
    private Skin localSkin;
    private AnimatedMenuButton backBtn;

    public CreditsScreen(Main game) {
        super(game);
        localSkin = new Skin(Gdx.files.internal("button1.json"), new com.badlogic.gdx.graphics.g2d.TextureAtlas(Gdx.files.internal("button1.atlas"))); 
        backgroundTexture = new Texture(Gdx.files.internal("backgrounds/pause frame.png"));
        
        buildLayout();
        addListeners();
    }

    @Override
    public void buildLayout() {
        Image bg = new Image(backgroundTexture);
        bg.setFillParent(true);
        stage.addActor(bg);

        Table root = new Table();
        root.setFillParent(true);
        root.top().padTop(40);
        stage.addActor(root);

        Label title = new Label("CREDITS", localSkin);
        title.setFontScale(3.0f);
        Label devA = new Label("Halil Akgul", localSkin);
        Label devB = new Label("Arda Aslan Kirdar", localSkin);
        Label devC = new Label("Selim Enes Sezer", localSkin);
        Label devD = new Label("Ilker Can Kaplan", localSkin);
        devA.setFontScale(2.0f);
        devB.setFontScale(2.0f);
        devC.setFontScale(2.0f);
        devD.setFontScale(2.0f);

        root.add(title).center().padBottom(200).row();
        root.add(devA).center().padBottom(20).row();
        root.add(devB).center().padBottom(20).row();
        root.add(devC).center().padBottom(20).row();
        root.add(devD).center().padBottom(60).row();

        backBtn = new AnimatedMenuButton("BACK", localSkin);
        root.add(backBtn).center().size(300, 80);
    }

    @Override
    public void addListeners() {
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game)); 
            }
        });
    }

    @Override
    public void dispose() {
        super.dispose();
        if (backgroundTexture != null) backgroundTexture.dispose();
        if (localSkin != null) localSkin.dispose(); 
    }
}