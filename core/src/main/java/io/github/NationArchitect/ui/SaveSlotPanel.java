package io.github.NationArchitect.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.NationArchitect.controller.savemanager.SaveData;
import io.github.NationArchitect.modules.GameMap;
import io.github.NationArchitect.screens.BaseScreen;

public class SaveSlotPanel extends Table {
    private final String saveDate;
    private final String nationName;
    private final GameMap map;
    private final int slot;
    private final boolean cloud;
    private final SaveData saveData;
    private Label nameLabel;
    private Label infoLabel;
    private StaticGameButton selectBtn;
    private final BaseScreen rootScreen;

    public SaveSlotPanel(BaseScreen rootScreen, String nationName, String saveDate, GameMap map, int slot, boolean cloud, SaveData saveData) {
        this.rootScreen = rootScreen;
        this.nationName = nationName;
        this.saveDate = saveDate;
        this.map = map;
        this.slot = slot;
        this.cloud = cloud;
        this.saveData = saveData;
        buildLayout();
    }

    public void buildLayout() {
        Skin skin = rootScreen.getSkin();
        if (skin == null) {
            return;
        }

        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(new Color(0.04f, 0.1f, 0.2f, 0.85f));
        pix.fill();
        setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pix))));
        pix.dispose();

        pad(10);

        Label.LabelStyle ls = new Label.LabelStyle();
        ls.font = skin.getFont("default-font");
        ls.fontColor = Color.WHITE;

        String sourceLabel = (cloud ? "CLOUD" : "LOCAL") + " SLOT " + (slot + 1);
        String displayName = nationName == null || nationName.trim().isEmpty() ? "--- EMPTY ---" : nationName;
        String displayDate = saveDate == null || saveDate.trim().isEmpty() ? "Empty slot" : saveDate;

        nameLabel = new Label(sourceLabel + " | " + displayName, ls);
        infoLabel = new Label(displayDate, ls);
        selectBtn = new StaticGameButton("SELECT", skin);

        add(nameLabel).left().padRight(20);
        add(infoLabel).expandX().center();
        add(selectBtn).right().size(120, 40);
    }

    public String getSaveName() { return nationName; }
    public GameMap getMap() { return map; }
    public StaticGameButton getSelectBtn() { return selectBtn; }
    public int getSlot() { return slot; }
    public boolean isCloud() { return cloud; }
    public SaveData getSaveData() { return saveData; }
}
