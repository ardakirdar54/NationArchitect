package io.github.NationArchitect.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import io.github.NationArchitect.screens.BaseScreen;
import io.github.NationArchitect.screens.GameScreen;

public abstract class UIPanel extends Table {
    public boolean isOpen = false;
    protected Table container;
    protected String title;
    protected BaseScreen rootScreen;

    public UIPanel(BaseScreen rootScreen, String title) {
        this.rootScreen = rootScreen;
        this.title = title;
        this.container = this;
        this.setVisible(false);
    }

    public void toggle() {
        if (isOpen) hide();
        else show();
    }

    public void show() {
        isOpen = true;
        this.setVisible(true);
    }

    public void hide() {
        isOpen = false;
        this.setVisible(false);
    }

    public abstract void refreshData();

    public Table getContainer() { return this; }

    public boolean isOpen() { return isOpen; }

    protected GameScreen getGameScreen() {
        return rootScreen instanceof GameScreen ? (GameScreen) rootScreen : null;
    }

    public abstract void buildLayout();
     public void dispose() {}
}
