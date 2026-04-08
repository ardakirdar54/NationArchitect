package io.github.NationArchitect.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import io.github.NationArchitect.modules.Building;
import io.github.NationArchitect.modules.BuildingRegistry;
import io.github.NationArchitect.modules.Region;
import io.github.NationArchitect.screens.BaseScreen;

public class HealthPanel extends UIPanel {

    private Region currentRegion;
    private Label regionNameLabel;
    private Array<Building> buildings;
    private int currentPage = 0;
    private static final int PER_PAGE = 2;

    private Table cardContainer;
    private Label pageLabel;
    private Texture frameTex;
    private Array<Texture> loadedTextures = new Array<>();

    public HealthPanel(BaseScreen rootScreen) {
        super(rootScreen, "HEALTH SERVICES");
        this.buildings = BuildingRegistry.getHealthBuildings();
    }

    @Override
    public void buildLayout() {
        Skin skin = rootScreen.getSkin();
        if (skin == null) return;

        this.clear();
        this.pad(0);

        String framePath = Gdx.files.internal("backgrounds/pause frame.png").exists()
            ? "backgrounds/pause frame.png" : "backgrounds/pause_frame.png";
        if (Gdx.files.internal(framePath).exists()) {
            frameTex = new Texture(Gdx.files.internal(framePath));
            this.setBackground(new TextureRegionDrawable(new TextureRegion(frameTex)));
        }

        Table inner = new Table();
        inner.pad(16, 24, 16, 24);
        inner.top();

        Table headerBg = new Table();
        headerBg.setBackground(makeDarkBg(0.04f, 0.1f, 0.22f, 0.95f));
        headerBg.pad(10, 20, 10, 20);
        Label title = makeLabel("HEALTH SERVICES", 0x44FF88FF, 2.0f, skin);
        AnimatedMenuButton closeBtn = new AnimatedMenuButton("X", skin);
        closeBtn.addListener(new ClickListener() {
            @Override public void clicked(InputEvent e, float x, float y) { hide(); }
        });
        headerBg.add(title).expandX().center();
        headerBg.add(closeBtn).size(44, 44).right();
        inner.add(headerBg).fillX().padBottom(8).row();

        regionNameLabel = makeLabel("Select a region first", 0xCCEEFFFF, 1.0f, skin);
        inner.add(regionNameLabel).center().padBottom(12).row();

        cardContainer = new Table();
        inner.add(cardContainer).fillX().expandY().row();

        Table navRow = new Table();
        navRow.setBackground(makeDarkBg(0.04f, 0.08f, 0.18f, 0.9f));
        navRow.pad(12);

        AnimatedMenuButton prevBtn = new AnimatedMenuButton("◀  PREV", skin);
        pageLabel = makeLabel("1 / 3", 0xCCCCCCFF, 1.2f, skin);
        AnimatedMenuButton nextBtn = new AnimatedMenuButton("NEXT  ▶", skin);

        prevBtn.addListener(new ClickListener() {
            @Override public void clicked(InputEvent e, float x, float y) {
                if (currentPage > 0) { currentPage--; refreshCards(skin); }
            }
        });
        nextBtn.addListener(new ClickListener() {
            @Override public void clicked(InputEvent e, float x, float y) {
                int maxPage = (int) Math.ceil(buildings.size / (float) PER_PAGE) - 1;
                if (currentPage < maxPage) { currentPage++; refreshCards(skin); }
            }
        });

        navRow.add(prevBtn).size(140, 52).padRight(20);
        navRow.add(pageLabel).expandX().center();
        navRow.add(nextBtn).size(140, 52).padLeft(20);
        inner.add(navRow).fillX().padTop(10).row();

        this.add(inner).fill().expand();
        refreshCards(skin);
    }

    private void refreshCards(Skin skin) {
        cardContainer.clear();
        disposeLoadedTextures();
        int start = currentPage * PER_PAGE;
        int end   = Math.min(start + PER_PAGE, buildings.size);
        int totalPages = (int) Math.ceil(buildings.size / (float) PER_PAGE);
        pageLabel.setText((currentPage + 1) + " / " + totalPages);
        for (int i = start; i < end; i++) {
            Building b = buildings.get(i);
            Table card = buildCard(b, skin);
            cardContainer.add(card).expandX().fillY().padRight(i < end - 1 ? 12 : 0);
        }
        if (end - start == 1) cardContainer.add().expandX();
    }

    private Table buildCard(Building building, Skin skin) {
        Table card = new Table();
        card.setBackground(makeDarkBg(0.05f, 0.1f, 0.2f, 0.92f));
        card.pad(12).top();
        Table imgContainer = new Table();
        imgContainer.setBackground(makeDarkBg(0.08f, 0.14f, 0.26f, 1f));
        String imgPath = building.getImagePath();
        if (Gdx.files.internal(imgPath).exists()) {
            Texture tex = new Texture(Gdx.files.internal(imgPath));
            loadedTextures.add(tex);
            Image img = new Image(tex);
            imgContainer.add(img).size(220, 140).pad(4);
        } else {
            imgContainer.add(makeLabel("[ No Image ]", 0x666666FF, 0.8f, skin)).size(220, 140).center();
        }
        card.add(imgContainer).fillX().padBottom(8).row();
        card.add(makeLabel(building.getName(), 0xFFFFFFFF, 1.5f, skin)).center().padBottom(6).row();
        Table costTable = new Table();
        costTable.setBackground(makeDarkBg(0.06f, 0.12f, 0.22f, 0.9f));
        costTable.pad(6, 10, 6, 10);
        costTable.add(makeLabel("Construction:", 0xAABBCCFF, 1.0f, skin)).left().padRight(6);
        costTable.add(makeLabel("$" + formatNum(building.getConstructionCost()), 0xFFDD44FF, 1.1f, skin)).left().row();
        costTable.add(makeLabel("Monthly:", 0xAABBCCFF, 1.0f, skin)).left().padRight(6);
        costTable.add(makeLabel("$" + formatNum(building.getMonthlyBudget()) + "/mo", 0xFF8844FF, 1.1f, skin)).left().row();
        card.add(costTable).fillX().padBottom(8).row();
        card.add(makeLabel("EFFECTS:", 0xAABBCCFF, 1.0f, skin)).left().padBottom(4).row();
        addEffectRow(card, "Education", building.getEducationEffect(), 0x00E5FFFF, skin);
        addEffectRow(card, "Happiness", building.getHappinessEffect(), 0x00FF88FF, skin);
        addEffectRow(card, "Health", building.getHealthEffect(), 0x44FF88FF, skin);
        addEffectRow(card, "Security", building.getSecurityEffect(), 0xFFAA00FF, skin);
        addEffectRow(card, "Industry", building.getIndustryEffect(), 0xBB88FFFF, skin);
        AnimatedMenuButton buildBtn = new AnimatedMenuButton("BUILD", skin);
        buildBtn.addListener(new ClickListener() {
            @Override public void clicked(InputEvent e, float x, float y) { onBuildClicked(building); }
        });
        card.add(buildBtn).size(220, 58).center().padTop(12).row();
        return card;
    }

    private void addEffectRow(Table parent, String metric, float value, int color, Skin skin) {
        if (value == 0f) return;
        String sign = value > 0 ? "+" : "";
        Label lbl = makeLabel("▸ " + metric + ":  " + sign + (int) value, new Color(color), 1.0f, skin);
        parent.add(lbl).left().padBottom(2).row();
    }

    private void onBuildClicked(Building building) {
        if (currentRegion == null) return;
        Gdx.app.log("HealthPanel", "Building: " + building.getName() + " in " + currentRegion.getName());
    }

    public void setRegion(Region region) { loadRegion(region); }
    public void loadRegion(Region region) {
        this.currentRegion = region;
        if (region == null) return;
        if (regionNameLabel != null) regionNameLabel.setText(region.getName().toUpperCase());
        currentPage = 0;
        Skin skin = rootScreen.getSkin();
        if (skin != null) refreshCards(skin);
    }

    @Override public void refreshData() {}
    private void disposeLoadedTextures() {
        for (Texture t : loadedTextures) t.dispose();
        loadedTextures.clear();
    }
    private String formatNum(int n) { return String.format("%,d", n); }
    private Label makeLabel(String text, int rgba, float scale, Skin skin) {
        Label.LabelStyle s = new Label.LabelStyle();
        s.font = skin.getFont("default-font");
        s.fontColor = new Color(rgba);
        Label l = new Label(text, s);
        l.setFontScale(scale);
        return l;
    }
    private Label makeLabel(String text, Color color, float scale, Skin skin) {
        Label.LabelStyle s = new Label.LabelStyle();
        s.font = skin.getFont("default-font");
        s.fontColor = color;
        Label l = new Label(text, s);
        l.setFontScale(scale);
        return l;
    }
    private TextureRegionDrawable makeDarkBg(float r, float g, float b, float a) {
        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(r, g, b, a); pix.fill();
        Texture tex = new Texture(pix);
        loadedTextures.add(tex);
        TextureRegionDrawable d = new TextureRegionDrawable(new TextureRegion(tex));
        pix.dispose();
        return d;
    }

    @Override public void dispose() {
        disposeLoadedTextures();
        if (frameTex != null) frameTex.dispose();
    }
}
