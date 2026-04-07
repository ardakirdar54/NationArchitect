package com.nationarchitect.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.nationarchitect.game.screens.BaseScreen;

public class TaxPanel extends UIPanel {

    private Texture frameTex;

    public TaxPanel(BaseScreen rootScreen) {
        super(rootScreen, "Taxation");
    }

    @Override
    public void buildLayout() {
        Skin skin = rootScreen.getSkin();
        if (skin == null) return;

        this.clear();
        
        // 1. Arka Plan
        if (Gdx.files.internal("backgrounds/pause frame.png").exists()) {
            frameTex = new Texture(Gdx.files.internal("backgrounds/pause frame.png"));
            this.setBackground(new TextureRegionDrawable(new TextureRegion(frameTex)));
        }

        Table inner = new Table();
        inner.top();

        // --- 2. BAŞLIK (Tablo İçinde Sabitlendi) ---
        Table header = new Table();
        header.setBackground(makeDarkBg(0.04f, 0.1f, 0.22f, 0.95f));
        header.pad(15, 30, 15, 30);

        Label title = makeLabel("TAXATION", 0x00E5FFFF, 2.8f, skin);
        AnimatedMenuButton closeBtn = new AnimatedMenuButton("X", skin);
        closeBtn.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
            @Override public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent e, float x, float y) { hide(); }
        });

        header.add(title).expandX().center().padLeft(50);
        header.add(closeBtn).size(55, 55).right();
        
        // padTop(45) ile başlığı yukarıdan ayırdık, padBottom(65) ile tablolarla arasını açtık
        // Bu değerler o istediğin Y=842.5 ortalamasını panel içinde sağlar.
        inner.add(header).fillX().padTop(45).padBottom(65).row(); 

        // --- 3. VERGİ KATEGORİLERİ ---
        
        // RESIDENTIAL TAXES
        inner.add(createTaxCategory("RESIDENTIAL TAXES", skin, new String[][]{
            {"INCOME TAX", "icons/tax_income.png", "14%"},
            {"PROPERTY TAX", "icons/tax_property.png", "12%"}
        })).width(650).padBottom(35).row();

        // COMMERCIAL TAXES
        inner.add(createTaxCategory("COMMERCIAL TAXES", skin, new String[][]{
            {"VAT", "icons/tax_vat.png", "18%"},
            {"EXCISE TAX", "icons/tax_excise.png", "8%"}
        })).width(650).padBottom(35).row();

        // INDUSTRIAL TAXES
        inner.add(createTaxCategory("INDUSTRIAL TAXES", skin, new String[][]{
            {"CORPORATE TAX", "icons/tax_corporate.png", "15%"},
            {"PRODUCT TAX", "icons/tax_product.png", "15%"}
        })).width(650).row();

        this.add(inner).fill().expand();
    }

    private Table createTaxCategory(String categoryName, Skin skin, String[][] taxItems) {
        Table catTable = new Table();
        catTable.setBackground(makeDarkBg(0.15f, 0.12f, 0.12f, 0.9f));
        
        Table titleRow = new Table();
        titleRow.setBackground(makeDarkBg(0.55f, 0.08f, 0.08f, 1.0f));
        Label l = makeLabel(categoryName, 0xFFFFFFFF, 1.3f, skin);
        titleRow.add(l).center().pad(8);
        catTable.add(titleRow).fillX().row();

        for (String[] item : taxItems) {
            Table row = new Table();
            row.pad(15, 30, 15, 30);
            
            Label nameLbl = makeLabel(item[0], 0xDDDDDDFF, 1.1f, skin);
            row.add(nameLbl).width(220).left();
            
            ProgressBar bar = new ProgressBar(2f, 20f, 0.5f, false, makeSliderStyle(0x00E5FFFF));
            bar.setValue(Float.parseFloat(item[2].replace("%", "")));
            row.add(bar).width(220).height(24).padLeft(20);
            
            Label valLbl = makeLabel(item[2], 0xFFFFFFFF, 1.2f, skin);
            row.add(valLbl).width(70).right().padLeft(15);
            
            catTable.add(row).fillX().row();
        }
        
        return catTable;
    }

    private Label makeLabel(String text, int rgba, float scale, Skin skin) {
        Label.LabelStyle s = new Label.LabelStyle();
        s.font = skin.getFont("default-font");
        s.fontColor = new Color(rgba);
        Label l = new Label(text, s);
        l.setFontScale(scale);
        return l;
    }

    private TextureRegionDrawable makeDarkBg(float r, float g, float b, float a) {
        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(r, g, b, a); pix.fill();
        TextureRegionDrawable d = new TextureRegionDrawable(new TextureRegion(new Texture(pix))); pix.dispose();
        return d;
    }

    private ProgressBar.ProgressBarStyle makeSliderStyle(int rgbaColor) {
        Pixmap bgPix = new Pixmap(1, 20, Pixmap.Format.RGBA8888);
        bgPix.setColor(new Color(0.15f, 0.15f, 0.15f, 1f)); bgPix.fill();
        TextureRegionDrawable bg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPix))); bgPix.dispose();

        Pixmap fillPix = new Pixmap(1, 20, Pixmap.Format.RGBA8888);
        fillPix.setColor(new Color(rgbaColor)); fillPix.fill();
        TextureRegionDrawable fill = new TextureRegionDrawable(new TextureRegion(new Texture(fillPix))); fillPix.dispose();

        ProgressBar.ProgressBarStyle style = new ProgressBar.ProgressBarStyle();
        style.background = bg; style.knobBefore = fill;
        return style;
    }

    @Override public void refreshData() { }
    @Override public void dispose() { if (frameTex != null) frameTex.dispose(); }
}