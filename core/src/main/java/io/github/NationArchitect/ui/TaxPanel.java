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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.NationArchitect.controller.GameManager;
import io.github.NationArchitect.model.economy.CountryEconomy;
import io.github.NationArchitect.model.economy.RegionEconomy;
import io.github.NationArchitect.model.economy.Tax;
import io.github.NationArchitect.model.land.Country;
import io.github.NationArchitect.model.land.Region;
import io.github.NationArchitect.modules.UIRegion;
import io.github.NationArchitect.screens.BaseScreen;
import io.github.NationArchitect.screens.GameScreen;

import java.util.ArrayList;
import java.util.Locale;

public class TaxPanel extends UIPanel {

    private UIRegion currentRegion;
    private Texture frameTex;
    private Label regionNameLabel;
    private final ArrayList<TaxBinding> taxBindings = new ArrayList<>();

    public TaxPanel(BaseScreen rootScreen) {
        super(rootScreen, "Taxation");
    }

    @Override
    public void buildLayout() {
        Skin skin = rootScreen.getSkin();
        if (skin == null) return;

        clear();
        taxBindings.clear();

        if (Gdx.files.internal("backgrounds/pause frame.png").exists()) {
            frameTex = new Texture(Gdx.files.internal("backgrounds/pause frame.png"));
            setBackground(new TextureRegionDrawable(new TextureRegion(frameTex)));
        }

        Table inner = new Table();
        inner.top();

        Table header = new Table();
        header.setBackground(makeDarkBg(0.04f, 0.1f, 0.22f, 0.95f));
        header.pad(15, 30, 15, 30);

        Label title = makeLabel("TAXATION", 0x00E5FFFF, 2.8f, skin);
        AnimatedMenuButton closeBtn = new AnimatedMenuButton("X", skin);
        closeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent e, float x, float y) {
                hide();
            }
        });

        header.add(title).expandX().center().padLeft(50);
        header.add(closeBtn).size(55, 55).right();
        inner.add(header).fillX().padTop(45).padBottom(20).row();

        regionNameLabel = makeLabel("COUNTRY", 0xCCEEFFFF, 1.2f, skin);
        inner.add(regionNameLabel).padBottom(30).row();

        inner.add(createTaxCategory("RESIDENTIAL TAXES", skin, new TaxBinding[] {
            new TaxBinding("INCOME TAX", TaxField.INCOME),
            new TaxBinding("PROPERTY TAX", TaxField.PROPERTY)
        })).width(650).padBottom(35).row();

        inner.add(createTaxCategory("COMMERCIAL TAXES", skin, new TaxBinding[] {
            new TaxBinding("VAT", TaxField.VAT),
            new TaxBinding("EXCISE TAX", TaxField.EXCISE)
        })).width(650).padBottom(35).row();

        inner.add(createTaxCategory("INDUSTRIAL TAXES", skin, new TaxBinding[] {
            new TaxBinding("CORPORATE TAX", TaxField.CORPORATE),
            new TaxBinding("PRODUCT TAX", TaxField.PRODUCTION)
        })).width(650).row();

        add(inner).fill().expand();
        loadRegion(currentRegion);
    }

    private Table createTaxCategory(String categoryName, Skin skin, TaxBinding[] taxBindings) {
        Table catTable = new Table();
        catTable.setBackground(makeDarkBg(0.15f, 0.12f, 0.12f, 0.9f));

        Table titleRow = new Table();
        titleRow.setBackground(makeDarkBg(0.55f, 0.08f, 0.08f, 1.0f));
        titleRow.add(makeLabel(categoryName, 0xFFFFFFFF, 1.3f, skin)).center().pad(8);
        catTable.add(titleRow).fillX().row();

        for (TaxBinding binding : taxBindings) {
            Table row = new Table();
            row.pad(15, 30, 15, 30);
            row.add(makeLabel(binding.label, 0xDDDDDDFF, 1.1f, skin)).width(220).left();

            Slider slider = new Slider(2f, 20f, 1f, false, makeSliderStyle(0x00E5FFFF));
            row.add(slider).width(220).height(24).padLeft(20);

            Label valueLabel = makeLabel("8%", 0xFFFFFFFF, 1.2f, skin);
            row.add(valueLabel).width(70).right().padLeft(15);

            slider.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                    applyTax(binding.field, slider.getValue());
                    valueLabel.setText(formatPercent(slider.getValue()));
                }
            });

            binding.slider = slider;
            binding.valueLabel = valueLabel;
            catTable.add(row).fillX().row();
        }

        for (TaxBinding binding : taxBindings) {
            registerBinding(binding);
        }

        return catTable;
    }

    public void loadRegion(UIRegion region) {
        currentRegion = region;
        regionNameLabel.setText(region == null ? "COUNTRY" : region.getName().toUpperCase(Locale.ROOT));
        Tax tax = getActiveTax();
        if (tax == null) {
            return;
        }
        for (TaxBinding binding : taxBindings) {
            if (binding.slider == null || binding.valueLabel == null) {
                continue;
            }
            float value = (float) binding.field.get(tax);
            binding.slider.setValue(value);
            binding.valueLabel.setText(formatPercent(value));
        }
    }

    private void applyTax(TaxField field, float value) {
        Country country = getCountry();
        if (country == null) {
            return;
        }

        if (currentRegion != null) {
            field.set(currentRegion.getBackingRegion().getEconomy().getTax(), value);
            refreshEconomies(country);
            return;
        }

        field.set(country.getEconomy().getTax(), value);
        for (Region region : country.getRegions()) {
            if (region == null) {
                continue;
            }
            field.set(region.getEconomy().getTax(), value);
        }
        refreshEconomies(country);
    }

    private void refreshEconomies(Country country) {
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

    private Tax getActiveTax() {
        if (currentRegion != null) {
            return currentRegion.getBackingRegion().getEconomy().getTax();
        }
        Country country = getCountry();
        return country == null ? null : country.getEconomy().getTax();
    }

    private Country getCountry() {
        GameScreen gameScreen = rootScreen instanceof GameScreen ? (GameScreen) rootScreen : null;
        GameManager gameManager = gameScreen == null ? null : gameScreen.getGameManager();
        return gameManager == null ? null : gameManager.getCountry();
    }

    private void registerBinding(TaxBinding binding) {
        if (!taxBindings.contains(binding)) {
            taxBindings.add(binding);
        }
    }

    private String formatPercent(float value) {
        return String.format(Locale.US, "%.0f%%", value);
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
        Pixmap bgPix = new Pixmap(1, 20, Pixmap.Format.RGBA8888);
        bgPix.setColor(new Color(0.15f, 0.15f, 0.15f, 1f));
        bgPix.fill();
        TextureRegionDrawable background = new TextureRegionDrawable(new TextureRegion(new Texture(bgPix)));
        bgPix.dispose();

        Pixmap knobBeforePix = new Pixmap(1, 20, Pixmap.Format.RGBA8888);
        knobBeforePix.setColor(new Color(rgbaColor));
        knobBeforePix.fill();
        TextureRegionDrawable knobBefore = new TextureRegionDrawable(new TextureRegion(new Texture(knobBeforePix)));
        knobBeforePix.dispose();

        Pixmap knobPix = new Pixmap(12, 24, Pixmap.Format.RGBA8888);
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

    private enum TaxField {
        INCOME {
            @Override double get(Tax tax) { return tax.getIncomeTaxRate(); }
            @Override void set(Tax tax, double value) { tax.setIncomeTaxRate(value); }
        },
        PROPERTY {
            @Override double get(Tax tax) { return tax.getPropertyTaxRate(); }
            @Override void set(Tax tax, double value) { tax.setPropertyTaxRate(value); }
        },
        VAT {
            @Override double get(Tax tax) { return tax.getVatRate(); }
            @Override void set(Tax tax, double value) { tax.setVatRate(value); }
        },
        EXCISE {
            @Override double get(Tax tax) { return tax.getExciseTaxRate(io.github.NationArchitect.model.product.ProductType.TOURISM_SERVICE); }
            @Override void set(Tax tax, double value) { tax.setExciseTaxRate(value); }
        },
        CORPORATE {
            @Override double get(Tax tax) { return tax.getCorporateTaxRate(); }
            @Override void set(Tax tax, double value) { tax.setCorporateTaxRate(value); }
        },
        PRODUCTION {
            @Override double get(Tax tax) { return tax.getProductionTaxRate(); }
            @Override void set(Tax tax, double value) { tax.setProductionTaxRate(value); }
        };

        abstract double get(Tax tax);
        abstract void set(Tax tax, double value);
    }

    private static class TaxBinding {
        private final String label;
        private final TaxField field;
        private Slider slider;
        private Label valueLabel;

        private TaxBinding(String label, TaxField field) {
            this.label = label;
            this.field = field;
        }
    }
}
