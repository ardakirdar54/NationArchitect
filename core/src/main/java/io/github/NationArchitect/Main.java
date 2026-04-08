package io.github.NationArchitect;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.NationArchitect.screens.MainMenuScreen;

public class Main extends Game {
    private SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        com.badlogic.gdx.utils.ScreenUtils.clear(0, 0, 0, 1);
        super.render();
    }

    public SpriteBatch getBatch() { return batch; }

    @Override
    public void dispose() {
        if (screen != null) screen.hide();
        batch.dispose();
    }
}
