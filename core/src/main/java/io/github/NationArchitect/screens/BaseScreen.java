package io.github.NationArchitect.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.NationArchitect.Main;
import io.github.NationArchitect.ui.UIPanel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public abstract class BaseScreen implements Screen {
    public static final float WIDTH  = 1920f;
    protected static final float HEIGHT = 1080f;

    protected Main game; 
    protected Stage stage;
    protected Skin skin;
    protected OrthographicCamera camera;
    protected Viewport viewport;
    protected Array<UIPanel> panels;

    public BaseScreen(Main game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(WIDTH, HEIGHT, camera);
        this.stage = new Stage(viewport);
        this.panels = new Array<>();
    }

    public abstract void buildLayout(); 
    public abstract void addListeners();

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        if (skin != null) skin.dispose();
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    public Skin getSkin() { return skin; }
    public Main getGame() { return game; }

    public void refreshData() {}

}
