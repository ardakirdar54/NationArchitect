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

public class BudgetPanel extends UIPanel {

    private Region currentRegion;
    private Label regionNameLabel;
    private Label totalExpensesVal;
    private Texture frameTex;

    public BudgetPanel(BaseScreen rootScreen) {
        super(rootScreen, "Budget");
    }

    @Override
    public void buildLayout() {
        Skin skin = rootScreen.getSkin();
        if (skin == null) return;

        this.clear();
        
        // MetricsPanel ile aynı arka plan
        if (Gdx.files.internal("backgrounds/pause frame.png").exists()) {
            frameTex = new Texture(Gdx.files.internal("backgrounds/pause frame.png"));
            this.setBackground(new TextureRegionDrawable(new TextureRegion(frameTex)));
        }

        Table inner = new Table();
        inner.pad(20);
        inner.top();

        // --- 1. Başlık Katmanı ---
        Table header = new Table();
        header.setBackground(makeDarkBg(0.04f, 0.1f, 0.22f, 0.95f));
        header.pad(10, 20, 10, 20);

        Label title = makeLabel("BUDGET", 0x00E5FFFF, 2.5f, skin); // Yazı büyütüldü
        AnimatedMenuButton closeBtn = new AnimatedMenuButton("X", skin);
        closeBtn.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
            @Override public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent e, float x, float y) { hide(); }
        });

        header.add(title).expandX().center();
        header.add(closeBtn).size(50, 50).right();
        inner.add(header).fillX().padBottom(10).row();

        // Bölge ismi alanı (Dinamik)
        regionNameLabel = makeLabel("SELECT A REGION", 0xCCEEFFFF, 1.5f, skin); // Yazı büyütüldü
        inner.add(regionNameLabel).center().padBottom(20).row();

        // --- 2. Dörtlü Tablo Izgarası (Görseldeki gibi geniş yerleşim) ---
        Table grid = new Table();
        
        // Üst İkili
        grid.add(createBudgetCategory("INFRASTRUCTURE", skin, new String[]{"ROAD NETWORK", "ELECTRICITY", "WATER", "INTERNET"})).expand().fill().pad(10);
        grid.add(createBudgetCategory("TRANSPORTATION", skin, new String[]{"ROAD VEHICLES", "RAILROAD", "MARINE", "AIR"})).expand().fill().pad(10);
        grid.row();
        
        // Alt İkili
        grid.add(createBudgetCategory("PUBLIC SERVICES", skin, new String[]{"EDUCATION", "HEALTH", "SECURITY", "SOCIAL"})).expand().fill().pad(10);
        grid.add(createBudgetCategory("INDUSTRY", skin, new String[]{"FACTORIES", "TOURISM", "AGRICULTURE", "OFFICE"})).expand().fill().pad(10);
        
        inner.add(grid).expand().fill().row();

        // --- 3. Total Expenses ---
        Table footer = new Table();
        footer.setBackground(makeDarkBg(0.2f, 0.02f, 0.02f, 0.9f));
        footer.pad(15);
        
        Label footLbl = makeLabel("TOTAL BUDGET EXPENSES: ", 0xFFFFFFFF, 1.3f, skin);
        totalExpensesVal = makeLabel("0 $", 0xFFFF00FF, 1.4f, skin);
        
        footer.add(footLbl);
        footer.add(totalExpensesVal);
        inner.add(footer).fillX().padTop(20);

        this.add(inner).fill().expand();
    }

    private Table createBudgetCategory(String title, Skin skin, String[] items) {
        Table catTable = new Table();
        catTable.setBackground(makeDarkBg(0.12f, 0.08f, 0.08f, 0.85f)); // Görseldeki koyu kahve tonu
        
        // Kategori Başlığı (Kırmızı Şerit)
        Table titleRow = new Table();
        titleRow.setBackground(makeDarkBg(0.5f, 0.1f, 0.1f, 0.9f));
        Label l = makeLabel(title, 0xFFFFFFFF, 1.1f, skin);
        titleRow.add(l).center().pad(5);
        catTable.add(titleRow).fillX().row();

        // İçerik satırları
        for (String item : items) {
            Table row = new Table();
            row.pad(8, 15, 8, 15);
            
            Label itemLbl = makeLabel(item, 0xDDDDDDFF, 0.9f, skin);
            row.add(itemLbl).width(160).left();
            
            // Slider / Bar
            ProgressBar bar = new ProgressBar(50f, 150f, 1f, false, makeSliderStyle(0x00E5FFFF));
            bar.setValue(100f);
            row.add(bar).width(150).height(20).padLeft(15);
            
            catTable.add(row).fillX().row();
        }
        
        return catTable;
    }

    /**
     * Haritadan bir bölge seçildiğinde tetiklenir.
     * Bölge ismini ekrana basar.
     */
    public void loadRegion(Region region) {
        this.currentRegion = region;
        if (region == null) {
            regionNameLabel.setText("SELECT A REGION");
            return;
        }
        regionNameLabel.setText(region.getName().toUpperCase()); // Bölge ismini güncelle
        // İleride buraya bölgeye özel harcama verileri eklenecek.
    }

    // --- Yardımcılar ---
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
