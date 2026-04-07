package com.nationarchitect.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.nationarchitect.game.modules.GameMap;

public class ChangeableButton extends BaseGameButton {
    private Array<String> options;
    private Array<GameMap> mapList;
    private int currentIndex = 0;
    private GameMap selectedMap;

    public ChangeableButton(Skin skin) {
        this("", skin);
    }

    public ChangeableButton(String text, Skin skin) {
        super(text, skin);
        this.options = new Array<>();
        this.mapList = new Array<>();
        applyTheme();
        setupClickListener();
    }

    public void addOption(String mapName) {
        options.add(mapName);
        mapList.add(new GameMap(mapName));
        if (mapList.size == 1) updateSelection(0);
    }

    private void setupClickListener() {
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                nextMap();
            }
        });
    }

    public void nextMap() {
        if (mapList.size > 0) {
            currentIndex = (currentIndex + 1) % mapList.size;
            updateSelection(currentIndex);
        }
    }

    private void updateSelection(int index) {
        this.selectedMap = mapList.get(index);
        this.setText("< " + selectedMap.getName() + " >");
    }

    public String getSelectedOption()  { return selectedMap != null ? selectedMap.getName() : ""; }
    public GameMap getSelectedMap()    { return selectedMap; }

    public void setMapList(Array<GameMap> maps) {
        this.mapList = maps;
        if (maps.size > 0) updateSelection(0);
    }

    @Override
    public void applyTheme() {
        if (this.getStyle() != null)
            this.getStyle().overFontColor = new Color(0f, 0.9f, 1f, 1f);
    }
}