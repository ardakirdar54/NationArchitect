package io.github.NationArchitect.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class StaticGameButton extends BaseGameButton {

    public StaticGameButton(String text, Skin skin) {
        super(text, skin);
        this.growFactor = 1.0f; 
        applyTheme();
    }

    @Override
    public void applyTheme() {
    }
}
