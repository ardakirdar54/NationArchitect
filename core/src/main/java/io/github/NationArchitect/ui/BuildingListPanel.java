package io.github.NationArchitect.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.NationArchitect.model.component.Building;
import io.github.NationArchitect.model.component.Component;
import io.github.NationArchitect.model.component.ComponentType;
import io.github.NationArchitect.modules.UIRegion;

import java.util.ArrayList;

public class BuildingListPanel extends UIPanel {

    private UIRegion currentRegion;
    private Label regionNameLabel;
    private Table listTable;
    private Texture frameTex;

    public BuildingListPanel(io.github.NationArchitect.screens.BaseScreen rootScreen) {
        super(rootScreen, "Buildings");
    }

    @Override
    public void buildLayout() {
        Skin skin = rootScreen.getSkin();
        if (skin == null) {
            return;
        }

        clear();
        pad(0);

        String framePath = "backgrounds/pause frame.png";
        if (com.badlogic.gdx.Gdx.files.internal(framePath).exists()) {
            frameTex = new Texture(com.badlogic.gdx.Gdx.files.internal(framePath));
            setBackground(new TextureRegionDrawable(new TextureRegion(frameTex)));
        }

        Table inner = new Table();
        inner.pad(14, 18, 14, 18);
        inner.top();

        Table header = new Table();
        header.setBackground(makeDarkBg(0.04f, 0.1f, 0.22f, 0.95f));
        header.pad(10, 16, 10, 16);
        header.add(makeLabel("BUILDINGS", 0x00E5FFFF, 1.5f, skin)).expandX().left();
        AnimatedMenuButton closeBtn = new AnimatedMenuButton("X", skin);
        closeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });
        header.add(closeBtn).size(40, 40).right();
        inner.add(header).fillX().padBottom(8).row();

        regionNameLabel = makeLabel("Select a region first", 0xCCEEFFFF, 1.0f, skin);
        inner.add(regionNameLabel).left().padBottom(10).row();

        listTable = new Table();
        listTable.top().left();
        ScrollPane scrollPane = new ScrollPane(listTable, makeScrollPaneStyle());
        scrollPane.setFadeScrollBars(false);
        inner.add(scrollPane).width(520f).height(760f).fill().expand().row();

        add(inner).fill().expand();
        refreshRows();
    }

    public void loadRegion(UIRegion region) {
        currentRegion = region;
        if (regionNameLabel != null) {
            regionNameLabel.setText(region == null ? "Select a region first" : region.getName().toUpperCase());
        }
        refreshRows();
    }

    @Override
    public void refreshData() {
        refreshRows();
    }

    private void refreshRows() {
        if (listTable == null) {
            return;
        }
        listTable.clear();
        Skin skin = rootScreen.getSkin();
        if (skin == null) {
            return;
        }

        if (currentRegion == null || currentRegion.getBackingRegion() == null || currentRegion.getBackingRegion().getComponents() == null) {
            listTable.add(makeLabel("No region selected", 0xAAAAAAFF, 1.0f, skin)).left().padTop(10);
            return;
        }

        int rowCount = 0;
        for (ComponentType componentType : currentRegion.getBackingRegion().getComponents().keySet()) {
            Component component = currentRegion.getBackingRegion().getComponents().get(componentType);
            if (component == null) {
                continue;
            }
            ArrayList<Building> buildings = component.getBuildings();
            if (buildings.isEmpty()) {
                continue;
            }

            listTable.add(makeLabel(componentType.name().replace('_', ' '), 0x00E5FFFF, 1.0f, skin))
                .left().padTop(rowCount == 0 ? 0 : 12).padBottom(4).row();

            for (Building building : buildings) {
                Table row = new Table();
                row.setBackground(makeDarkBg(0.08f, 0.11f, 0.16f, 0.9f));
                row.pad(8, 10, 8, 10);

                String detail = building.getType().getName()
                    + " | $" + formatMoney(building.getMaintenanceCost()) + "/mo";
                row.add(makeLabel(building.getName(), 0xFFFFFFFF, 0.9f, skin)).width(250f).left().padRight(10);
                row.add(makeLabel(detail, 0xBFD8E6FF, 0.75f, skin)).width(170f).left().padRight(10);

                AnimatedMenuButton destroyBtn = new AnimatedMenuButton("DESTROY", skin);
                destroyBtn.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (currentRegion != null && getGameScreen() != null && getGameScreen().getGameManager() != null) {
                            boolean removed = currentRegion.destroyBuilding(componentType, building, getGameScreen().getGameManager().getCountry());
                            if (removed) {
                                refreshRows();
                            }
                        }
                    }
                });
                row.add(destroyBtn).size(135, 42).right();

                listTable.add(row).fillX().padBottom(6).row();
                rowCount++;
            }
        }

        if (rowCount == 0) {
            listTable.add(makeLabel("No buildings in this region", 0xAAAAAAFF, 1.0f, skin)).left().padTop(10);
        }
    }

    private String formatMoney(double value) {
        return String.format("%,.0f", value);
    }

    private Label makeLabel(String text, int rgba, float scale, Skin skin) {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = skin.getFont("default-font");
        style.fontColor = new Color(rgba);
        Label label = new Label(text, style);
        label.setFontScale(scale);
        return label;
    }

    private TextureRegionDrawable makeDarkBg(float r, float g, float b, float a) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(r, g, b, a);
        pixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        return drawable;
    }

    private ScrollPane.ScrollPaneStyle makeScrollPaneStyle() {
        ScrollPane.ScrollPaneStyle style = new ScrollPane.ScrollPaneStyle();
        style.background = makeDarkBg(0.04f, 0.07f, 0.12f, 0.85f);
        style.vScroll = makeDarkBg(0.12f, 0.16f, 0.22f, 0.95f);
        style.vScrollKnob = makeDarkBg(0.0f, 0.9f, 1.0f, 0.95f);
        return style;
    }

    @Override
    public void dispose() {
        if (frameTex != null) {
            frameTex.dispose();
        }
    }
}
