package io.github.NationArchitect.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.NationArchitect.controller.GameManager;
import io.github.NationArchitect.model.component.ComponentType;
import io.github.NationArchitect.model.economy.CountryEconomy;
import io.github.NationArchitect.model.economy.Economy;
import io.github.NationArchitect.model.economy.RegionEconomy;
import io.github.NationArchitect.model.economy.TaxType;
import io.github.NationArchitect.model.land.Country;
import io.github.NationArchitect.model.land.Region;
import io.github.NationArchitect.modules.UIRegion;
import io.github.NationArchitect.screens.BaseScreen;
import io.github.NationArchitect.screens.GameScreen;

import java.util.EnumMap;
import java.util.Locale;

public class EconomyPanel extends UIPanel {

    private UIRegion currentRegion;
    private Label regionNameLabel;
    private Texture frameTex;
    private Label totalIncomeVal;
    private Label totalExpanseVal;
    private Label totalBalanceVal;
    private Label treasuryVal;
    private Label importVal;
    private Label exportVal;
    private final EnumMap<ComponentType, ProgressBar> expenseBars = new EnumMap<>(ComponentType.class);
    private final EnumMap<TaxType, ProgressBar> taxBars = new EnumMap<>(TaxType.class);

    public EconomyPanel(BaseScreen rootScreen) {
        super(rootScreen, "Economy");
    }

    @Override
    public void buildLayout() {
        Skin skin = rootScreen.getSkin();
        if (skin == null) return;

        clear();
        expenseBars.clear();
        taxBars.clear();

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

        Label title = makeLabel("ECONOMY", 0x00E5FFFF, 2.5f, skin);
        AnimatedMenuButton closeBtn = new AnimatedMenuButton("X", skin);
        closeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                hide();
            }
        });

        header.add(title).expandX().center();
        header.add(closeBtn).size(50, 50).right();
        inner.add(header).fillX().padBottom(5).row();

        regionNameLabel = makeLabel("COUNTRY", 0xCCEEFFFF, 1.5f, skin);
        inner.add(regionNameLabel).center().padBottom(15).row();

        Table mainGrid = new Table();

        Table leftCol = new Table();
        leftCol.top();
        leftCol.add(createHeaderLabel("EXPANSES", Color.RED, skin)).fillX().padBottom(10).row();
        leftCol.add(createExpenseBlock("INFRASTRUCTURE", skin, new ComponentType[] {
            ComponentType.ROAD_NETWORK, ComponentType.ELECTRICITY, ComponentType.WATER_MANAGEMENT, ComponentType.INTERNET
        }, new String[] {"ROAD NETWORK", "ELECTRICITY", "WATER", "INTERNET"})).fillX().padBottom(10).row();
        leftCol.add(createExpenseBlock("TRANSPORTATION", skin, new ComponentType[] {
            ComponentType.ROAD_TRANSPORT, ComponentType.RAIL_TRANSPORT, ComponentType.MARINE_TRANSPORT, ComponentType.AIR_TRANSPORT
        }, new String[] {"ROAD VEHICLES", "RAILROAD", "MARINE", "AIR"})).fillX().padBottom(10).row();
        leftCol.add(createExpenseBlock("PUBLIC SERVICES", skin, new ComponentType[] {
            ComponentType.EDUCATION, ComponentType.HEALTH_SERVICES, ComponentType.SECURITY
        }, new String[] {"EDUCATION", "HEALTH", "SECURITY"})).fillX().padBottom(10).row();
        leftCol.add(createExpenseBlock("INDUSTRY", skin, new ComponentType[] {
            ComponentType.FACTORY, ComponentType.TOURISM, ComponentType.AGRICULTURE, ComponentType.OFFICE
        }, new String[] {"FACTORIES", "TOURISM", "AGRICULTURE", "OFFICE"})).fillX().padBottom(10).row();

        Table importRow = new Table();
        importRow.add(makeLabel("IMPORT:", 0xFF8888FF, 1.1f, skin)).padRight(8);
        importVal = makeLabel("$0", 0xFF8888FF, 1.1f, skin);
        importRow.add(importVal);
        leftCol.add(importRow).padTop(5).left().row();

        Table rightCol = new Table();
        rightCol.top();
        rightCol.add(createHeaderLabel("INCOMES", Color.CYAN, skin)).fillX().padBottom(10).row();
        rightCol.add(createTaxBlock(skin)).width(400).padBottom(25).row();

        Table exportRow = new Table();
        exportRow.add(makeLabel("EXPORT:", 0x88FF88FF, 1.2f, skin)).padRight(8);
        exportVal = makeLabel("$0", 0x88FF88FF, 1.2f, skin);
        exportRow.add(exportVal);
        rightCol.add(exportRow).padBottom(25).row();
        rightCol.add(createTotalBlock(skin)).width(400).fillX();

        mainGrid.add(leftCol).expand().fill().padRight(15);
        mainGrid.add(rightCol).expand().fill().top();
        inner.add(mainGrid).expand().fill();
        add(inner).fill().expand();

        loadRegion(currentRegion);
    }

    private Table createExpenseBlock(String title, Skin skin, ComponentType[] componentTypes, String[] labels) {
        Table block = new Table();
        block.setBackground(makeDarkBg(0.12f, 0.08f, 0.08f, 0.9f));

        Table titleRow = new Table();
        titleRow.setBackground(makeDarkBg(0.55f, 0.08f, 0.08f, 1.0f));
        titleRow.add(makeLabel(title, 0xFFFFFFFF, 1.1f, skin)).pad(6);
        block.add(titleRow).fillX().row();

        for (int index = 0; index < componentTypes.length; index++) {
            Table row = new Table();
            row.pad(8, 15, 8, 15);
            row.add(makeLabel(labels[index], 0xCCCCCCFF, 0.9f, skin)).width(160).left();

            ProgressBar bar = new ProgressBar(0, 100, 1, false, makeBarStyle(0x00E5FFFF));
            expenseBars.put(componentTypes[index], bar);
            row.add(bar).width(130).height(20).padLeft(15);
            block.add(row).fillX().row();
        }
        return block;
    }

    private Table createTaxBlock(Skin skin) {
        Table block = new Table();
        block.setBackground(makeDarkBg(0.12f, 0.08f, 0.08f, 0.9f));

        Table titleRow = new Table();
        titleRow.setBackground(makeDarkBg(0.55f, 0.08f, 0.08f, 1.0f));
        titleRow.add(makeLabel("TAXES", 0xFFFFFFFF, 1.1f, skin)).pad(6);
        block.add(titleRow).fillX().row();

        addTaxRow(block, "RESIDENTIAL", TaxType.INCOME_TAX, skin);
        addTaxRow(block, "COMMERCIAL", TaxType.VAT, skin);
        addTaxRow(block, "INDUSTRIAL", TaxType.CORPORATE_TAX, skin);
        return block;
    }

    private void addTaxRow(Table parent, String label, TaxType taxType, Skin skin) {
        Table row = new Table();
        row.pad(8, 15, 8, 15);
        row.add(makeLabel(label, 0xCCCCCCFF, 0.9f, skin)).width(160).left();
        ProgressBar bar = new ProgressBar(0, 100, 1, false, makeBarStyle(0x00E5FFFF));
        taxBars.put(taxType, bar);
        row.add(bar).width(130).height(20).padLeft(15);
        parent.add(row).fillX().row();
    }

    private Table createTotalBlock(Skin skin) {
        Table block = new Table();
        block.setBackground(makeDarkBg(0.12f, 0.08f, 0.08f, 0.95f));

        Table titleRow = new Table();
        titleRow.setBackground(makeDarkBg(0.4f, 0.4f, 0.6f, 1.0f));
        titleRow.add(makeLabel("TOTAL", 0xFFFFFFFF, 1.4f, skin)).pad(10);
        block.add(titleRow).fillX().row();

        totalIncomeVal = addTotalRow(block, "TOTAL INCOME", Color.CYAN, skin);
        totalExpanseVal = addTotalRow(block, "TOTAL EXPENSE", Color.RED, skin);
        totalBalanceVal = addTotalRow(block, "TOTAL BALANCE", Color.GREEN, skin);
        treasuryVal = addTotalRow(block, "TREASURY", Color.GOLD, skin);
        return block;
    }

    private Label addTotalRow(Table parent, String label, Color color, Skin skin) {
        Table row = new Table();
        row.setBackground(makeDarkBg(0.25f, 0.25f, 0.25f, 0.6f));
        row.pad(12, 20, 12, 20);
        row.add(makeLabel(label, 0xFFFFFFFF, 1.1f, skin)).expandX().left();
        Label valueLabel = makeLabel("$0", 0xFFFFFFFF, 1.2f, skin);
        valueLabel.setColor(color);
        row.add(valueLabel).right();
        parent.add(row).fillX().pad(4).row();
        return valueLabel;
    }

    public void loadRegion(UIRegion region) {
        currentRegion = region;
        refreshEconomySummary();
    }

    private void refreshEconomySummary() {
        Economy economy = getActiveEconomy();
        Country country = getCountry();
        if (economy == null || country == null) {
            return;
        }

        refreshAllEconomies(country);
        economy = getActiveEconomy();

        regionNameLabel.setText(currentRegion == null ? getCountryDisplayName(country) : currentRegion.getName().toUpperCase(Locale.ROOT));
        totalIncomeVal.setText(formatMoney(economy.getIncome()));
        totalExpanseVal.setText(formatMoney(economy.getExpanse()));
        totalBalanceVal.setText(formatMoney(economy.getBalance()));

        CountryEconomy countryEconomy = country.getEconomy() instanceof CountryEconomy
            ? (CountryEconomy) country.getEconomy()
            : null;

        if (economy instanceof RegionEconomy) {
            RegionEconomy regionEconomy = (RegionEconomy) economy;
            importVal.setText(formatMoney(regionEconomy.getImport()));
            exportVal.setText(formatMoney(regionEconomy.getExport()));
        } else {
            importVal.setText(formatMoney(countryEconomy == null ? 0.0 : countryEconomy.getImport()));
            exportVal.setText(formatMoney(countryEconomy == null ? 0.0 : countryEconomy.getExport()));
        }
        treasuryVal.setText(formatMoney(countryEconomy == null ? 0.0 : countryEconomy.getTreasury()));

        updateExpenseBars(economy.getComponentBudgets());
        updateTaxBars(economy.getTaxRevenues());
    }

    private void updateExpenseBars(EnumMap<ComponentType, Double> budgets) {
        double maxValue = 1.0;
        for (Double budget : budgets.values()) {
            maxValue = Math.max(maxValue, budget == null ? 0.0 : budget);
        }
        for (ComponentType componentType : expenseBars.keySet()) {
            double value = budgets.getOrDefault(componentType, 0.0);
            expenseBars.get(componentType).setValue((float) ((value / maxValue) * 100.0));
        }
    }

    private void updateTaxBars(EnumMap<TaxType, Double> revenues) {
        double residentialRevenue = revenues.getOrDefault(TaxType.INCOME_TAX, 0.0)
            + revenues.getOrDefault(TaxType.PROPERTY_TAX, 0.0);
        double commercialRevenue = revenues.getOrDefault(TaxType.VAT, 0.0)
            + revenues.getOrDefault(TaxType.EXCISE_TAX, 0.0);
        double industrialRevenue = revenues.getOrDefault(TaxType.CORPORATE_TAX, 0.0)
            + revenues.getOrDefault(TaxType.PRODUCTION_TAX, 0.0);

        double maxValue = 1.0;
        maxValue = Math.max(maxValue, residentialRevenue);
        maxValue = Math.max(maxValue, commercialRevenue);
        maxValue = Math.max(maxValue, industrialRevenue);

        if (taxBars.containsKey(TaxType.INCOME_TAX)) {
            taxBars.get(TaxType.INCOME_TAX).setValue(toVisibleBarValue(residentialRevenue, maxValue));
        }
        if (taxBars.containsKey(TaxType.VAT)) {
            taxBars.get(TaxType.VAT).setValue(toVisibleBarValue(commercialRevenue, maxValue));
        }
        if (taxBars.containsKey(TaxType.CORPORATE_TAX)) {
            taxBars.get(TaxType.CORPORATE_TAX).setValue(toVisibleBarValue(industrialRevenue, maxValue));
        }
    }

    private float toVisibleBarValue(double value, double maxValue) {
        if (value <= 0.0 || maxValue <= 0.0) {
            return 0f;
        }
        float normalized = (float) ((value / maxValue) * 100.0);
        return Math.max(8f, Math.min(100f, normalized));
    }

    private Economy getActiveEconomy() {
        if (currentRegion != null) {
            return currentRegion.getBackingRegion().getEconomy();
        }
        Country country = getCountry();
        return country == null ? null : country.getEconomy();
    }

    private Country getCountry() {
        GameScreen gameScreen = getGameScreen();
        if (gameScreen == null || gameScreen.getGameManager() == null) {
            return null;
        }
        return gameScreen.getGameManager().getCountry();
    }

    private String getCountryDisplayName(Country country) {
        if (country == null || country.getName() == null || country.getName().isBlank()) {
            return "COUNTRY";
        }
        return country.getName().toUpperCase(Locale.ROOT);
    }

    private void refreshAllEconomies(Country country) {
        if (country == null) {
            return;
        }
        for (Region region : country.getRegions()) {
            if (region == null) {
                continue;
            }
            if (region.getEconomy() instanceof RegionEconomy) {
                ((RegionEconomy) region.getEconomy()).update(region);
            }
        }
        if (country.getEconomy() instanceof CountryEconomy) {
            CountryEconomy countryEconomy = (CountryEconomy) country.getEconomy();
            countryEconomy.calculateImport(country);
            countryEconomy.calculateExport(country);
            countryEconomy.calculateBalance(country);
        }
    }

    private Label createHeaderLabel(String text, Color color, Skin skin) {
        Label label = makeLabel(text, 0xFFFFFFFF, 1.2f, skin);
        label.setColor(color);
        return label;
    }

    private String formatMoney(double value) {
        return String.format(Locale.US, "%,.2f$", value);
    }

    private Label makeLabel(String text, int rgba, float scale, Skin skin) {
        Label.LabelStyle style = new Label.LabelStyle(skin.getFont("default-font"), new Color(rgba));
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

    private ProgressBar.ProgressBarStyle makeBarStyle(int rgbaColor) {
        Pixmap bgPix = new Pixmap(1, 16, Pixmap.Format.RGBA8888);
        bgPix.setColor(new Color(0.15f, 0.15f, 0.15f, 1f));
        bgPix.fill();
        TextureRegionDrawable bg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPix)));
        bgPix.dispose();

        Pixmap fillPix = new Pixmap(1, 16, Pixmap.Format.RGBA8888);
        fillPix.setColor(new Color(rgbaColor));
        fillPix.fill();
        TextureRegionDrawable fill = new TextureRegionDrawable(new TextureRegion(new Texture(fillPix)));
        fillPix.dispose();

        ProgressBar.ProgressBarStyle style = new ProgressBar.ProgressBarStyle();
        style.background = bg;
        style.knobBefore = fill;
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
