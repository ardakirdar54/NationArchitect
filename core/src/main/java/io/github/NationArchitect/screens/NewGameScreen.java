package io.github.NationArchitect.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.NationArchitect.Main;
import io.github.NationArchitect.controller.savemanager.SaveData;
import io.github.NationArchitect.modules.GameMap;
import io.github.NationArchitect.ui.AnimatedMenuButton;
import io.github.NationArchitect.ui.ChangeableButton;

public class NewGameScreen extends BaseScreen {

    private ChangeableButton mapSelector;
    private TextField nicknameField;
    private AnimatedMenuButton initGovernanceBtn;
    private AnimatedMenuButton backBtn;
    private Texture backgroundTexture, mapPreviewTexture;
    private Image mapPreviewImage;
    private Skin localSkin;
    private Label titleLabel;

    public NewGameScreen(Main game) {
        super(game);
        localSkin = new Skin(Gdx.files.internal("button1.json"), 
                new com.badlogic.gdx.graphics.g2d.TextureAtlas(Gdx.files.internal("button1.atlas")));
        backgroundTexture = new Texture(Gdx.files.internal("backgrounds/new game screen.png"));
        mapPreviewTexture = new Texture(Gdx.files.internal("map/turkeyRegion.png"));
        buildLayout();
        addListeners();
    }

    @Override
    public void buildLayout() {
        stage.clear();

        Image bg = new Image(backgroundTexture);
        bg.setFillParent(true);
        stage.addActor(bg);

        Table root = new Table();
        root.setFillParent(true);
        root.pad(60);
        root.top().left();
        stage.addActor(root);

        Table leftColumn = new Table();
        leftColumn.top().left();

        titleLabel = makeLabel("NEW GAME", 0x00E5FFFF, 3.5f); 
        leftColumn.add(titleLabel).left().padBottom(40).row();

        mapPreviewImage = new Image(mapPreviewTexture);
        leftColumn.add(mapPreviewImage).size(700, 350).left().padBottom(30).row(); 

        mapSelector = new ChangeableButton(localSkin);
        mapSelector.addOption("map1");
        mapSelector.addOption("map2");
        leftColumn.add(mapSelector).width(450).height(80).left().row(); 

        Table rightColumn = new Table();
        rightColumn.top().right();

        TextField.TextFieldStyle tfStyle = new TextField.TextFieldStyle();
        tfStyle.font = localSkin.getFont("default-font");
        tfStyle.fontColor = Color.BLACK; 
        tfStyle.messageFontColor = new Color(0x333333FF); 
        tfStyle.background = localSkin.getDrawable("button");

        nicknameField = new TextField("", tfStyle);
        nicknameField.setMessageText("Enter Nation Name...");
        rightColumn.add(nicknameField).width(600).height(80).padBottom(40).row(); 

        Table nationalDataBox = buildNationalDataBox();
        rightColumn.add(nationalDataBox).width(600).padBottom(40).row(); 

        Table bottomButtons = new Table();
        backBtn = new AnimatedMenuButton("EXIT TO MENU", localSkin);
        initGovernanceBtn = new AnimatedMenuButton("INITILIZE GOVERNANCE", localSkin);
        
        bottomButtons.add(backBtn).size(350, 100).padRight(50);
        bottomButtons.add(initGovernanceBtn).size(500, 100);

        root.add(leftColumn).expandX().fillX().top().left().padRight(100);
        root.add(rightColumn).width(600).top().right();
        root.row();
        root.add(bottomButtons).colspan(2).center().padTop(80);
    }

    private Table buildNationalDataBox() {
        Table box = new Table();
        box.pad(40);
        box.setBackground(makeBorderDrawable(0x00E5FF55, 0x0A1A2FCC));

        Label title = makeLabel("NATIONAL DATA", 0x00E5FFFF, 2.0f); // Alt başlık büyüdü
        box.add(title).left().padBottom(30).row();

        addResourceRow(box, "ORE",              0.55f);
        addResourceRow(box, "FOSSIL FUELS",     0.70f);
        addResourceRow(box, "AGRICULTURE",      0.60f);
        addResourceRow(box, "FOREST",           0.20f);
        addResourceRow(box, "WATER",            0.80f);
        addResourceRow(box, "RENEWABLE ENERGY", 0.40f);

        return box;
    }

    private void addResourceRow(Table parent, String name, float value) {
        Label lbl = makeLabel(name, 0xCCCCCCFF, 1.3f); 
        ProgressBar bar = new ProgressBar(0f, 1f, 0.01f, false, makeProgressBarStyle());
        bar.setValue(value);

        parent.add(lbl).left().width(250).padBottom(18);
        parent.add(bar).width(250).height(35).padBottom(18).row(); 
    }

    private ProgressBar.ProgressBarStyle makeProgressBarStyle() {
        Pixmap bgPix = new Pixmap(1, 35, Pixmap.Format.RGBA8888);
        bgPix.setColor(new Color(0.15f, 0.25f, 0.2f, 1f));
        bgPix.fill();
        TextureRegionDrawable bg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPix)));
        bgPix.dispose();

        Pixmap fillPix = new Pixmap(1, 35, Pixmap.Format.RGBA8888);
        fillPix.setColor(new Color(0.2f, 0.85f, 0.3f, 1f));
        fillPix.fill();
        TextureRegionDrawable fill = new TextureRegionDrawable(new TextureRegion(new Texture(fillPix)));
        fillPix.dispose();

        ProgressBar.ProgressBarStyle style = new ProgressBar.ProgressBarStyle();
        style.background = bg;
        style.knobBefore = fill;
        return style;
    }

    private TextureRegionDrawable makeBorderDrawable(int borderColorHex, int bgColorHex) {
        int w = 600, h = 600; 
        Pixmap pix = new Pixmap(w, h, Pixmap.Format.RGBA8888);
        pix.setColor(new Color(bgColorHex));
        pix.fill();
        pix.setColor(new Color(borderColorHex));
        pix.drawRectangle(0, 0, w, h);
        pix.drawRectangle(1, 1, w - 2, h - 2);
        TextureRegionDrawable d = new TextureRegionDrawable(new TextureRegion(new Texture(pix)));
        pix.dispose();
        return d;
    }

    private Label makeLabel(String text, int rgbaHex, float fontScale) {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = localSkin.getFont("default-font");
        style.fontColor = new Color(rgbaHex);
        Label lbl = new Label(text, style);
        lbl.setFontScale(fontScale);
        return lbl;
    }

    @Override
    public void addListeners() {
        backBtn.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) { onBackClicked(); }
        });
        initGovernanceBtn.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) { onStartClicked(); }
        });
    }

    public void onStartClicked() {
        if (nicknameField.getText() != null && !nicknameField.getText().trim().isEmpty()) {
            GameMap map = mapSelector != null && mapSelector.getSelectedMap() != null
                ? mapSelector.getSelectedMap()
                : new GameMap("map1");
            SaveData starterSave = loadStarterSave(map.getName(), nicknameField.getText().trim());
            if (starterSave != null) {
                game.setScreen(new GameScreen(game, starterSave));
            } else {
                game.setScreen(new GameScreen(game, nicknameField.getText().trim(), map));
            }
        } else {
            Gdx.app.error("ERROR", "Please input a valid name.");
        }
    }

    public void onBackClicked() { game.setScreen(new MainMenuScreen(game)); }

    private SaveData loadStarterSave(String mapName, String nationName) {
        String path = "data/" + mapName + "-country.json";
        if (!Gdx.files.internal(path).exists()) {
            return null;
        }
        try {
            SaveData saveData = SaveData.fromJson(Gdx.files.internal(path).readString("UTF-8"));
            if (saveData != null && saveData.getCountry() != null && nationName != null && !nationName.isBlank()) {
                saveData.getCountry().name = nationName;
            }
            if (saveData != null) {
                saveData.setMapType(mapName);
            }
            return saveData;
        } catch (Exception exception) {
            Gdx.app.error("NewGameScreen", "Failed to load starter country from " + path, exception);
            return null;
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        if (localSkin != null) localSkin.dispose();
        if (backgroundTexture != null) backgroundTexture.dispose();
        if (mapPreviewTexture != null) mapPreviewTexture.dispose();
    }

    @Override
    public void show() {
        super.show();
        game.playMenuMusic();
    }
}
