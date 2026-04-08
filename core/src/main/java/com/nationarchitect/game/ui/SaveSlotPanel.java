package com.nationarchitect.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.nationarchitect.game.modules.GameMap;
import com.nationarchitect.game.screens.BaseScreen;

public class SaveSlotPanel extends Table {
    private String saveDate;
    private String nationName;
    private GameMap map;
    private Label nameLabel;
    private Label infoLabel;
    private StaticGameButton selectBtn;
    private BaseScreen rootScreen;

    public SaveSlotPanel(BaseScreen rootScreen, String nationName, String saveDate, GameMap map) {
        this.rootScreen = rootScreen;
        this.nationName = nationName;
        this.saveDate   = saveDate;
        this.map        = map;
        buildLayout();
    }

    public void buildLayout() {
        Skin skin = rootScreen.getSkin();

        // button1.json'da default-pane yok, Pixmap ile arka plan yapıyoruz
        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(new Color(0.04f, 0.1f, 0.2f, 0.85f));
        pix.fill();
        this.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pix))));
        pix.dispose();

        this.pad(10);

        Label.LabelStyle ls = new Label.LabelStyle();
        ls.font      = skin.getFont("default-font");
        ls.fontColor = Color.WHITE;

        nameLabel  = new Label(nationName,           ls);
        infoLabel  = new Label("Date: " + saveDate, ls);
        selectBtn  = new StaticGameButton("SELECT",  skin);

        this.add(nameLabel).left().padRight(20);
        this.add(infoLabel).expandX().center();
        this.add(selectBtn).right().size(120, 40);
    }

    public String getSaveName()          { return nationName; }
    public GameMap getMap()              { return map; }
    public StaticGameButton getSelectBtn() { return selectBtn; }
}