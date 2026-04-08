package com.nationarchitect.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AnimatedMenuButton extends BaseGameButton {
    
    public AnimatedMenuButton(String text, Skin skin) {
        super(text, skin);
        this.growFactor = 1.2f; 
        this.baseColor = Color.WHITE; 
        applyTheme();
    }

    @Override
    public void applyTheme() {
        this.getStyle().overFontColor = Color.BLUE; 
    }
}