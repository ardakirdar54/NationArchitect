package com.nationarchitect.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public abstract class BaseGameButton extends TextButton {
    protected Color baseColor;
    protected float growFactor;

    public BaseGameButton(String text, Skin skin) {
        super(text, skin);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public abstract void applyTheme();
}