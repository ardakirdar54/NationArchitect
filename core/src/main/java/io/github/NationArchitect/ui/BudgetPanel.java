package io.github.NationArchitect.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.NationArchitect.controller.GameManager;
import io.github.NationArchitect.model.component.Component;
import io.github.NationArchitect.model.component.ComponentType;
import io.github.NationArchitect.model.economy.CountryEconomy;
import io.github.NationArchitect.model.economy.RegionEconomy;
import io.github.NationArchitect.model.land.Country;
import io.github.NationArchitect.model.land.Region;
import io.github.NationArchitect.modules.UIRegion;
import io.github.NationArchitect.screens.BaseScreen;
import io.github.NationArchitect.screens.GameScreen;

import java.util.EnumMap;
import java.util.Locale;

public class BudgetPanel extends UIPanel {

    private UIRegion currentRegion;
    private Label regionNameLabel;
    private Label totalExpensesVal;
    private Texture frameTex;
    private final EnumMap<ComponentType, Slider> budgetSliders = new EnumMap<>(ComponentType.class);
    private final EnumMap<ComponentType, Label> budgetValueLabels = new EnumMap<>(ComponentType.class);

    public BudgetPanel(BaseScreen rootScreen) {
        super(rootScreen, "Budget");
    }

    @Override
    public void buildLayout() {
        Skin skin = rootScreen.getSkin();
        if (skin == null) return;

        clear();
        budgetSliders.clear();
        budgetValueLabels.clear();

        if (Gdx.files.internal("backgrounds/pause frame.png").exists()) {
            frameTex = new Texture(Gdx.files.internal("backgrounds/pause frame.png"));
            setBackground(new TextureRegionDrawable(new TextureRegion(frameTex)));
        }

        Table inner = new Table();
        inner.pad(20);
        inner.top();

        Table header = new Table();
        header.setBackground(makeDarkBg(0.04f, 0.1f, 0.22f, 0.95f));
        header.pad(10, 20, 10, 20);

        Label title = makeLabel("BUDGET", 0x00E5FFFF, 2.5f, skin);
        AnimatedMenuButton closeBtn = new AnimatedMenuButton("X", skin);
        closeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent e, float x, float y) {
                hide();
            }
        });

        header.add(title).expandX().center();
        header.add(closeBtn).size(50, 50).right();
        inner.add(header).fillX().padBottom(10).row();

        regionNameLabel = makeLabel("COUNTRY", 0xCCEEFFFF, 1.5f, skin);
        inner.add(regionNameLabel).center().padBottom(20).row();

        Table grid = new Table();
        grid.add(createBudgetCategory("INFRASTRUCTURE", skin, new ComponentType[] {
            ComponentType.ROAD_NETWORK, ComponentType.ELECTRICITY, ComponentType.WATER_MANAGEMENT, ComponentType.INTERNET
        }, new String[] {"ROAD NETWORK", "ELECTRICITY", "WATER", "INTERNET"})).expand().fill().pad(10);
        grid.add(createBudgetCategory("TRANSPORTATION", skin, new ComponentType[] {
            ComponentType.ROAD_TRANSPORT, ComponentType.RAIL_TRANSPORT, ComponentType.MARINE_TRANSPORT, ComponentType.AIR_TRANSPORT
        }, new String[] {"ROAD VEHICLES", "RAILROAD", "MARINE", "AIR"})).expand().fill().pad(10);
        grid.row();
        grid.add(createBudgetCategory("PUBLIC SERVICES", skin, new ComponentType[] {
            ComponentType.EDUCATION, ComponentType.HEALTH_SERVICES, ComponentType.SECURITY
        }, new String[] {"EDUCATION", "HEALTH", "SECURITY"})).expand().fill().pad(10);
        grid.add(createBudgetCategory("INDUSTRY", skin, new ComponentType[] {
            ComponentType.FACTORY, ComponentType.TOURISM, ComponentType.AGRICULTURE, ComponentType.OFFICE
        }, new String[] {"FACTORIES", "TOURISM", "AGRICULTURE", "OFFICE"})).expand().fill().pad(10);
        inner.add(grid).expand().fill().row();

        Table footer = new Table();
        footer.setBackground(makeDarkBg(0.2f, 0.02f, 0.02f, 0.9f));
        footer.pad(15);
        footer.add(makeLabel("TOTAL BUDGET EXPENSES:", 0xFFFFFFFF, 1.3f, skin)).padRight(12);
        totalExpensesVal = makeLabel("0$", 0xFFFF00FF, 1.4f, skin);
        footer.add(totalExpensesVal);
        inner.add(footer).fillX().padTop(20);

        add(inner).fill().expand();
        loadRegion(currentRegion);
    }

    private Table createBudgetCategory(String title, Skin skin, ComponentType[] componentTypes, String[] labels) {
        Table catTable = new Table();
        catTable.setBackground(makeDarkBg(0.12f, 0.08f, 0.08f, 0.85f));

        Table titleRow = new Table();
        titleRow.setBackground(makeDarkBg(0.5f, 0.1f, 0.1f, 0.9f));
        titleRow.add(makeLabel(title, 0xFFFFFFFF, 1.1f, skin)).center().pad(5);
        catTable.add(titleRow).fillX().row();

        for (int index = 0; index < componentTypes.length; index++) {
            ComponentType componentType = componentTypes[index];
            Table row = new Table();
            row.pad(8, 15, 8, 15);

            row.add(makeLabel(labels[index], 0xDDDDDDFF, 0.9f, skin)).width(160).left();

            Slider slider = new Slider(50f, 150f, 1f, false, makeSliderStyle(0x00E5FFFF));
            budgetSliders.put(componentType, slider);
            row.add(slider).width(150).height(20).padLeft(15);

            Label valueLabel = makeLabel("100%", 0xFFFFFFFF, 0.9f, skin);
            budgetValueLabels.put(componentType, valueLabel);
            row.add(valueLabel).width(60).padLeft(10).right();

            slider.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                    applyBudget(componentType, slider.getValue());
                    valueLabel.setText(formatPercent(slider.getValue()));
                    refreshSummary();
                }
            });

            catTable.add(row).fillX().row();
        }
        return catTable;
    }

    public void loadRegion(UIRegion region) {
        currentRegion = region;
        regionNameLabel.setText(region == null ? "COUNTRY" : region.getName().toUpperCase(Locale.ROOT));
        loadSliderValues();
        refreshSummary();
    }

    private void loadSliderValues() {
        for (ComponentType componentType : budgetSliders.keySet()) {
            float value = currentRegion == null ? getCountryAverageBudget(componentType) : getRegionBudget(componentType);
            budgetSliders.get(componentType).setValue(value);
            budgetValueLabels.get(componentType).setText(formatPercent(value));
        }
    }

    private float getRegionBudget(ComponentType componentType) {
        if (currentRegion == null) {
            return 100f;
        }
        Component component = currentRegion.getBackingRegion().getComponents().get(componentType);
        return component == null ? 100f : (float) component.getBudgetPercentage();
    }

    private float getCountryAverageBudget(ComponentType componentType) {
        Country country = getCountry();
        if (country == null || country.getRegions() == null) {
            return 100f;
        }

        float total = 0f;
        int count = 0;
        for (Region region : country.getRegions()) {
            if (region == null || region.getComponents() == null || region.getComponents().get(componentType) == null) {
                continue;
            }
            total += region.getComponents().get(componentType).getBudgetPercentage();
            count++;
        }
        return count == 0 ? 100f : total / count;
    }

    private void applyBudget(ComponentType componentType, float budgetValue) {
        Country country = getCountry();
        if (country == null) {
            return;
        }

        if (currentRegion != null) {
            Region region = currentRegion.getBackingRegion();
            Component component = region.getComponents().get(componentType);
            if (component != null) {
                component.setBudgetPercentage(budgetValue);
            }
            refreshRegionState(region);
            refreshCountryState(country);
            return;
        }

        for (Region region : country.getRegions()) {
            if (region == null || region.getComponents() == null) {
                continue;
            }
            Component component = region.getComponents().get(componentType);
            if (component != null) {
                component.setBudgetPercentage(budgetValue);
            }
            refreshRegionState(region);
        }
        refreshCountryState(country);
    }

    private void refreshSummary() {
        Country country = getCountry();
        if (country == null) {
            return;
        }

        refreshAllEconomies(country);
        if (currentRegion != null && currentRegion.getBackingRegion().getEconomy() instanceof RegionEconomy) {
            totalExpensesVal.setText(formatMoney(currentRegion.getBackingRegion().getEconomy().getExpanse()));
            return;
        }

        if (country.getEconomy() instanceof CountryEconomy) {
            totalExpensesVal.setText(formatMoney(country.getEconomy().getExpanse()));
        }
    }

    private void refreshAllEconomies(Country country) {
        for (Region region : country.getRegions()) {
            if (region == null) {
                continue;
            }
            refreshRegionState(region);
        }
        refreshCountryState(country);
    }

    private void refreshRegionState(Region region) {
        if (region == null) {
            return;
        }
        if (region.getComponents() != null) {
            for (Component component : region.getComponents().values()) {
                if (component != null) {
                    component.update();
                }
            }
        }
        region.calculateLandValue();
        if (region.getEconomy() instanceof RegionEconomy) {
            ((RegionEconomy) region.getEconomy()).update(region);
        }
    }

    private void refreshCountryState(Country country) {
        if (country == null) {
            return;
        }
        country.calculatePopulation();
        if (country.getEconomy() instanceof CountryEconomy) {
            ((CountryEconomy) country.getEconomy()).update(country);
        }
    }

    private Country getCountry() {
        GameScreen gameScreen = rootScreen instanceof GameScreen ? (GameScreen) rootScreen : null;
        GameManager gameManager = gameScreen == null ? null : gameScreen.getGameManager();
        return gameManager == null ? null : gameManager.getCountry();
    }

    private String formatPercent(float value) {
        return String.format(Locale.US, "%.0f%%", value);
    }

    private String formatMoney(double value) {
        return String.format(Locale.US, "%,.2f$", value);
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
        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(r, g, b, a);
        pix.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pix)));
        pix.dispose();
        return drawable;
    }

    private Slider.SliderStyle makeSliderStyle(int rgbaColor) {
        Pixmap bgPix = new Pixmap(1, 16, Pixmap.Format.RGBA8888);
        bgPix.setColor(new Color(0.15f, 0.15f, 0.15f, 1f));
        bgPix.fill();
        TextureRegionDrawable background = new TextureRegionDrawable(new TextureRegion(new Texture(bgPix)));
        bgPix.dispose();

        Pixmap knobBeforePix = new Pixmap(1, 16, Pixmap.Format.RGBA8888);
        knobBeforePix.setColor(new Color(rgbaColor));
        knobBeforePix.fill();
        TextureRegionDrawable knobBefore = new TextureRegionDrawable(new TextureRegion(new Texture(knobBeforePix)));
        knobBeforePix.dispose();

        Pixmap knobPix = new Pixmap(12, 20, Pixmap.Format.RGBA8888);
        knobPix.setColor(Color.WHITE);
        knobPix.fill();
        TextureRegionDrawable knob = new TextureRegionDrawable(new TextureRegion(new Texture(knobPix)));
        knobPix.dispose();

        Slider.SliderStyle style = new Slider.SliderStyle();
        style.background = background;
        style.knobBefore = knobBefore;
        style.knob = knob;
        return style;
    }

    @Override
    public void refreshData() {
        loadRegion(currentRegion);
    }

    @Override
    public void dispose() {
        if (frameTex != null) frameTex.dispose();
    }
}
