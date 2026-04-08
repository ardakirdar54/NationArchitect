package io.github.NationArchitect.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.NationArchitect.modules.Region;
import io.github.NationArchitect.screens.BaseScreen;

public class EconomyPanel extends UIPanel {

    private Region currentRegion;
    private Label regionNameLabel;
    private Texture frameTex;
    private Label totalIncomeVal, totalExpanseVal, totalBalanceVal, treasuryVal;

    public EconomyPanel(BaseScreen rootScreen) {
        super(rootScreen, "Economy");
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
        inner.pad(20);
        inner.top();

        // --- 2. BAŞLIK KATMANI ---
        Table header = new Table();
        header.setBackground(makeDarkBg(0.04f, 0.1f, 0.22f, 0.95f));
        header.pad(10, 20, 10, 20);

        Label title = makeLabel("ECONOMY", 0x00E5FFFF, 2.5f, skin);
        AnimatedMenuButton closeBtn = new AnimatedMenuButton("X", skin);
        closeBtn.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
            @Override public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent e, float x, float y) { hide(); }
        });

        header.add(title).expandX().center();
        header.add(closeBtn).size(50, 50).right();
        inner.add(header).fillX().padBottom(5).row();

        regionNameLabel = makeLabel("SELECT A REGION", 0xCCEEFFFF, 1.5f, skin);
        inner.add(regionNameLabel).center().padBottom(15).row();

        // --- 3. ANA İÇERİK (SOL: EXPANSES | SAĞ: INCOMES & TOTAL) ---
        Table mainGrid = new Table();
        
        // --- SOL SÜTUN (EXPANSES) ---
        Table leftCol = new Table();
        leftCol.top();
        leftCol.add(createHeaderLabel("EXPANSES", Color.RED, skin)).fillX().padBottom(10).row();
        
        // Gider blokları
        leftCol.add(createSubBlock("INFRASTRUCTURE", skin, new String[]{"ROAD NETWORK", "ELECTRICITY", "WATER", "INTERNET"})).fillX().padBottom(10).row();
        leftCol.add(createSubBlock("TRANSPORTATION", skin, new String[]{"ROAD VEHICLES", "RAILROAD", "MARINE", "AIR"})).fillX().padBottom(10).row();
        leftCol.add(createSubBlock("PUBLIC SERVICES", skin, new String[]{"EDUCATION", "HEALTH", "SECURITY"})).fillX().padBottom(10).row();
        leftCol.add(createSubBlock("INDUSTRY", skin, new String[]{"FACTORIES", "TOURISM", "AGRICULTURE", "OFFICE"})).fillX().padBottom(10).row();
        
        Label importLbl = makeLabel("IMPORT: 57.135$", 0xFF8888FF, 1.1f, skin);
        leftCol.add(importLbl).padTop(5).row();

        // --- SAĞ SÜTUN (INCOMES & TOTAL) ---
        Table rightCol = new Table();
        rightCol.top();
        rightCol.add(createHeaderLabel("INCOMES", Color.CYAN, skin)).fillX().padBottom(10).row();
        
        // Taxes bloğu genişliği 400 yapılarak büyütüldü
        rightCol.add(createSubBlock("TAXES", skin, new String[]{"RESIDENTIAL", "COMMERCIAL", "INDUSTRIAL"})).width(400).padBottom(25).row();
        
        Label exportLbl = makeLabel("EXPORT: 83.865$", 0x88FF88FF, 1.2f, skin);
        rightCol.add(exportLbl).padBottom(25).row();

        // TOTAL bloğu
        Table totalBlock = createTotalBlock(skin);
        rightCol.add(totalBlock).width(400).fillX();

        mainGrid.add(leftCol).expand().fill().padRight(15);
        mainGrid.add(rightCol).expand().fill().top();
        
        inner.add(mainGrid).expand().fill();
        this.add(inner).fill().expand();
    }

    private Table createSubBlock(String title, Skin skin, String[] items) {
        Table block = new Table();
        block.setBackground(makeDarkBg(0.12f, 0.08f, 0.08f, 0.9f));
        
        Table tRow = new Table();
        tRow.setBackground(makeDarkBg(0.55f, 0.08f, 0.08f, 1.0f));
        tRow.add(makeLabel(title, 0xFFFFFFFF, 1.1f, skin)).pad(6);
        block.add(tRow).fillX().row();

        for (String item : items) {
            Table row = new Table();
            row.pad(8, 15, 8, 15); // Padding artırıldı
            row.add(makeLabel(item, 0xCCCCCCFF, 0.9f, skin)).width(160).left();
            
            ProgressBar bar = new ProgressBar(0, 100, 1, false, makeSliderStyle(0x00E5FFFF));
            bar.setValue(50); // Dummy değer
            row.add(bar).width(130).height(20).padLeft(15);
            
            block.add(row).fillX().row();
        }
        return block;
    }

    private Table createTotalBlock(Skin skin) {
        Table block = new Table();
        block.setBackground(makeDarkBg(0.12f, 0.08f, 0.08f, 0.95f));
        
        Table tRow = new Table();
        tRow.setBackground(makeDarkBg(0.4f, 0.4f, 0.6f, 1.0f));
        tRow.add(makeLabel("TOTAL", 0xFFFFFFFF, 1.4f, skin)).pad(10); // Başlık büyüdü
        block.add(tRow).fillX().row();

        // Satır fontları ve paddingleri artırıldı
        totalIncomeVal  = addTotalRow(block, "TOTAL INCOME",  "622.333$", Color.CYAN,  skin);
        totalExpanseVal = addTotalRow(block, "TOTAL EXPANSE", "606.382$", Color.RED,   skin);
        totalBalanceVal = addTotalRow(block, "TOTAL BALANCE", "+15.951$", Color.GREEN, skin);
        treasuryVal     = addTotalRow(block, "TREASURY",      "943.372$", Color.GOLD,  skin);
        
        return block;
    }

    private Label addTotalRow(Table parent, String label, String val, Color c, Skin skin) {
        Table r = new Table();
        r.setBackground(makeDarkBg(0.25f, 0.25f, 0.25f, 0.6f));
        r.pad(12, 20, 12, 20); // Dikey boşluk artırıldı
        r.add(makeLabel(label, 0xFFFFFFFF, 1.1f, skin)).expandX().left();
        Label vLbl = makeLabel(val, 0xFFFFFFFF, 1.2f, skin);
        vLbl.setColor(c);
        r.add(vLbl).right();
        parent.add(r).fillX().pad(4).row();
        return vLbl;
    }

    private Label createHeaderLabel(String text, Color c, Skin skin) {
        Label l = makeLabel(text, 0xFFFFFFFF, 1.2f, skin);
        l.setColor(c);
        return l;
    }

    public void loadRegion(Region region) {
        this.currentRegion = region;
        if (region == null) return;
        regionNameLabel.setText(region.getName().toUpperCase());
    }

    private Label makeLabel(String text, int rgba, float scale, Skin skin) {
        Label.LabelStyle s = new Label.LabelStyle(skin.getFont("default-font"), new Color(rgba));
        Label l = new Label(text, s);
        l.setFontScale(scale);
        return l;
    }

    private TextureRegionDrawable makeDarkBg(float r, float g, float b, float a) {
        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(r, g, b, a); pix.fill();
        TextureRegionDrawable d = new TextureRegionDrawable(new TextureRegion(new Texture(pix)));
        pix.dispose();
        return d;
    }

    private ProgressBar.ProgressBarStyle makeSliderStyle(int rgbaColor) {
        Pixmap bgPix = new Pixmap(1, 16, Pixmap.Format.RGBA8888);
        bgPix.setColor(new Color(0.15f, 0.15f, 0.15f, 1f)); bgPix.fill();
        TextureRegionDrawable bg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPix))); bgPix.dispose();
        
        Pixmap fillPix = new Pixmap(1, 16, Pixmap.Format.RGBA8888);
        fillPix.setColor(new Color(rgbaColor)); fillPix.fill();
        TextureRegionDrawable fill = new TextureRegionDrawable(new TextureRegion(new Texture(fillPix))); fillPix.dispose();
        
        ProgressBar.ProgressBarStyle style = new ProgressBar.ProgressBarStyle();
        style.background = bg; style.knobBefore = fill;
        return style;
    }

    @Override public void refreshData() { if (currentRegion != null) loadRegion(currentRegion); }
    @Override public void dispose() { if (frameTex != null) frameTex.dispose(); }
}
