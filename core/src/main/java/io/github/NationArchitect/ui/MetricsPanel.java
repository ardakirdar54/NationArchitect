package io.github.NationArchitect.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.NationArchitect.modules.Region;
import io.github.NationArchitect.screens.BaseScreen;

public class MetricsPanel extends UIPanel {

    private Region currentRegion;
    private Label regionNameLabel;

    private ProgressBar happinessBar, unemploymentBar, educationBar, healthBar, crimeBar;
    private Label happinessVal, unemploymentVal, educationVal, healthVal, crimeVal;
    private Label populationTotalVal;

    // Pasta grafik için texture'lar
    private Texture genderChartTex;
    private Texture ageChartTex;
    private Image genderChartImg;
    private Image ageChartImg;

    // Pasta grafik legend label'ları
    private Label maleLbl, femaleLbl;
    private Label age0Lbl, age18Lbl, age24Lbl, age65Lbl;

    private Texture frameTex;

    public MetricsPanel(BaseScreen rootScreen) {
        super(rootScreen, "Metrics");
    }

    @Override
    public void buildLayout() {
        Skin skin = rootScreen.getSkin();
        if (skin == null) return;

        this.clear();
        this.pad(0);

        // Arka plan
        String framePath = Gdx.files.internal("backgrounds/pause frame.png").exists()
            ? "backgrounds/pause frame.png" : "backgrounds/pause frame.png";
        if (Gdx.files.internal(framePath).exists()) {
            frameTex = new Texture(Gdx.files.internal(framePath));
            this.setBackground(new TextureRegionDrawable(new TextureRegion(frameTex)));
        }

        Table inner = new Table();
        inner.pad(10, 20, 10, 20);
        inner.top();

        // ── 1. Başlık (en üstte) ──
        Table headerBg = new Table();
        headerBg.setBackground(makeDarkBg(0.04f, 0.1f, 0.22f, 0.95f));
        headerBg.pad(10, 20, 10, 20);

        Label title = makeLabel("METRICS", 0x00E5FFFF, 2.2f, skin);
        AnimatedMenuButton closeBtn = new AnimatedMenuButton("X", skin);
        closeBtn.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
            @Override public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent e, float x, float y) { hide(); }
        });

        headerBg.add(title).expandX().center();
        headerBg.add(closeBtn).size(44, 44).right();
        inner.add(headerBg).fillX().padBottom(6).row();

        regionNameLabel = makeLabel("SELECT A REGION", 0xCCEEFFFF, 1.3f, skin);
        inner.add(regionNameLabel).center().padBottom(12).row();

        // ── 2. Metrik satırları ──
        happinessBar    = addMetricRow(inner, "icons/public happines logo.png", "PUBLIC HAPPINESS",  0x00FF88FF, skin);
        unemploymentBar = addMetricRow(inner, "icons/unemplyoment.png",         "UNEMPLOYMENT",      0xFF4444FF, skin);
        educationBar    = addMetricRow(inner, "icons/education logo.jpeg",       "EDUCATION LEVEL",   0x00E5FFFF, skin);
        healthBar       = addMetricRow(inner, "icons/health status.png",        "HEALTH STATUS",     0x44FF88FF, skin);
        crimeBar        = addMetricRow(inner, "icons/crimerate v2.png",         "CRIME RATE",        0xFF8800FF, skin);

        // ── 3. Population başlığı ──
        Table popHeader = new Table();
        popHeader.setBackground(makeDarkBg(0.06f, 0.12f, 0.22f, 0.9f));
        popHeader.pad(8, 14, 8, 14);
        Label popLbl = makeLabel("POPULATION", 0xCCCCCCFF, 1.1f, skin);
        Label totalLbl = makeLabel("TOTAL", 0xAABBCCFF, 0.9f, skin);
        populationTotalVal = makeLabel("5,000,000", 0xFFFFFFFF, 1.1f, skin);
        popHeader.add(popLbl).left().padRight(12);
        popHeader.add(totalLbl).padRight(8);
        popHeader.add(populationTotalVal).left().expandX();
        inner.add(popHeader).fillX().padTop(10).padBottom(8).row();

        // ── 4. Pasta grafikler ──
        Table chartsRow = new Table();

        // Cinsiyet pasta grafiği
        Table genderBlock = new Table();
        genderBlock.setBackground(makeDarkBg(0.04f, 0.08f, 0.16f, 0.85f));
        genderBlock.pad(14);
        Label gTitle = makeLabel("GENDER", 0xAABBCCFF, 1.0f, skin);
        genderBlock.add(gTitle).center().padBottom(8).row();

        genderChartTex = buildPieChart(new float[]{50f, 50f},
            new Color[]{new Color(0x6699FFFF), new Color(0xFF99CCFF)}, 80);
        genderChartImg = new Image(genderChartTex);
        genderBlock.add(genderChartImg).size(120, 120).padBottom(6).row();

        maleLbl   = makeLabel("● Male   50%", 0x6699FFFF, 1.0f, skin);
        femaleLbl = makeLabel("● Female 50%", 0xFF99CCFF, 1.0f, skin);
        genderBlock.add(maleLbl).left().row();
        genderBlock.add(femaleLbl).left().row();

        chartsRow.add(genderBlock).expandX().fill().padRight(8);

        // Yaş pasta grafiği
        Table ageBlock = new Table();
        ageBlock.setBackground(makeDarkBg(0.04f, 0.08f, 0.16f, 0.85f));
        ageBlock.pad(14);
        Label aTitle = makeLabel("AGE", 0xAABBCCFF, 1.0f, skin);
        ageBlock.add(aTitle).center().padBottom(8).row();

        ageChartTex = buildPieChart(new float[]{25f, 15f, 45f, 15f},
            new Color[]{new Color(0xFFDD88FF), new Color(0x88DDFFFF),
                        new Color(0x88FF99FF), new Color(0xFF8888FF)}, 80);
        ageChartImg = new Image(ageChartTex);
        ageBlock.add(ageChartImg).size(120, 120).padBottom(6).row();

        age0Lbl  = makeLabel("● 0-18   25%", 0xFFDD88FF, 0.9f, skin);
        age18Lbl = makeLabel("● 18-24  15%", 0x88DDFFFF, 0.9f, skin);
        age24Lbl = makeLabel("● 24-65  45%", 0x88FF99FF, 0.9f, skin);
        age65Lbl = makeLabel("● 65+    15%", 0xFF8888FF, 0.9f, skin);
        ageBlock.add(age0Lbl).left().row();
        ageBlock.add(age18Lbl).left().row();
        ageBlock.add(age24Lbl).left().row();
        ageBlock.add(age65Lbl).left().row();

        chartsRow.add(ageBlock).expandX().fill();
        inner.add(chartsRow).fillX().expandY().padTop(6).row();

        this.add(inner).fill().expand();
    }

    // ── Pasta grafik oluşturucu (Pixmap ile) ──
    private Texture buildPieChart(float[] values, Color[] colors, int size) {
        Pixmap pix = new Pixmap(size, size, Pixmap.Format.RGBA8888);
        pix.setColor(0, 0, 0, 0);
        pix.fill();

        float total = 0;
        for (float v : values) total += v;

        float startAngle = 0f;
        int cx = size / 2, cy = size / 2, r = size / 2 - 2;

        for (int i = 0; i < values.length; i++) {
            float sweep = (values[i] / total) * 360f;
            pix.setColor(colors[i]);
            // Doldur: startAngle -> startAngle+sweep
            for (float a = startAngle; a < startAngle + sweep; a += 0.5f) {
                float rad = a * MathUtils.degreesToRadians;
                for (int dr = 0; dr <= r; dr++) {
                    int px = cx + MathUtils.round(dr * MathUtils.cos(rad));
                    int py = cy + MathUtils.round(dr * MathUtils.sin(rad));
                    if (px >= 0 && px < size && py >= 0 && py < size)
                        pix.drawPixel(px, py);
                }
            }
            startAngle += sweep;
        }

        // Kenar çizgisi
        pix.setColor(new Color(1f, 1f, 1f, 0.3f));
        for (float a = 0; a < 360; a += 0.5f) {
            float rad = a * MathUtils.degreesToRadians;
            int px = cx + MathUtils.round(r * MathUtils.cos(rad));
            int py = cy + MathUtils.round(r * MathUtils.sin(rad));
            if (px >= 0 && px < size && py >= 0 && py < size)
                pix.drawPixel(px, py);
        }

        Texture tex = new Texture(pix);
        pix.dispose();
        return tex;
    }

    private ProgressBar addMetricRow(Table parent, String iconPath, String name, int barColor, Skin skin) {
        Table row = new Table();
        row.setBackground(makeDarkBg(0.04f, 0.08f, 0.15f, 0.8f));
        row.pad(6, 10, 6, 10);

        if (Gdx.files.internal(iconPath).exists()) {
            Image icon = new Image(new Texture(Gdx.files.internal(iconPath)));
            row.add(icon).size(40, 40).padRight(10);
        } else {
            row.add().size(40, 40).padRight(10);
        }

        Label lbl = makeLabel(name, 0xCCCCCCFF, 1.0f, skin);
        row.add(lbl).width(190).left().padRight(10);

        ProgressBar bar = new ProgressBar(0f, 100f, 1f, false, makeBarStyle(barColor));
        bar.setValue(50f);
        row.add(bar).width(200).height(18).padRight(8);

        Label val = makeLabel("50%", 0x00E5FFFF, 1.0f, skin);
        storeValLabel(name, val);
        row.add(val).width(50).right();

        parent.add(row).fillX().padBottom(5).row();
        return bar;
    }

    private void storeValLabel(String name, Label val) {
        switch (name) {
            case "PUBLIC HAPPINESS":  happinessVal    = val; break;
            case "UNEMPLOYMENT":      unemploymentVal = val; break;
            case "EDUCATION LEVEL":   educationVal    = val; break;
            case "HEALTH STATUS":     healthVal       = val; break;
            case "CRIME RATE":        crimeVal        = val; break;
        }
    }

    public void loadRegion(Region region) {
        this.currentRegion = region;
        if (region == null) return;

        regionNameLabel.setText(region.getName().toUpperCase());

        float happiness    = region.getHappiness();
        float unemployment = 100f - region.getSecurity();
        float education    = region.getEducation();
        float health       = region.getHealth();
        float crime        = 100f - region.getSecurity();

        happinessBar.setValue(happiness);
        unemploymentBar.setValue(unemployment);
        educationBar.setValue(education);
        healthBar.setValue(health);
        crimeBar.setValue(crime);

        if (happinessVal    != null) happinessVal.setText((int) happiness + "%");
        if (unemploymentVal != null) unemploymentVal.setText((int) unemployment + "%");
        if (educationVal    != null) educationVal.setText((int) education + "%");
        if (healthVal       != null) healthVal.setText((int) health + "%");
        if (crimeVal        != null) crimeVal.setText((int) crime + "%");

        if (populationTotalVal != null)
            populationTotalVal.setText(String.format("%,d", region.getTotalPopulation()));

        // Cinsiyet pasta grafiği güncelle
        float male   = region.getMalePct() * 100f;
        float female = region.getFemalePct() * 100f;
        if (maleLbl   != null) maleLbl.setText(String.format("● Male   %.0f%%", male));
        if (femaleLbl != null) femaleLbl.setText(String.format("● Female %.0f%%", female));
        updateGenderChart(male, female);

        // Yaş pasta grafiği güncelle
        float a0  = region.getAge0_18()  * 100f;
        float a18 = region.getAge18_24() * 100f;
        float a24 = region.getAge24_65() * 100f;
        float a65 = region.getAge65plus()* 100f;
        if (age0Lbl  != null) age0Lbl.setText(String.format("● 0-18   %.0f%%", a0));
        if (age18Lbl != null) age18Lbl.setText(String.format("● 18-24  %.0f%%", a18));
        if (age24Lbl != null) age24Lbl.setText(String.format("● 24-65  %.0f%%", a24));
        if (age65Lbl != null) age65Lbl.setText(String.format("● 65+    %.0f%%", a65));
        updateAgeChart(a0, a18, a24, a65);
    }

    private void updateGenderChart(float male, float female) {
        if (genderChartTex != null) genderChartTex.dispose();
        genderChartTex = buildPieChart(
            new float[]{male, female},
            new Color[]{new Color(0x6699FFFF), new Color(0xFF99CCFF)}, 80);
        if (genderChartImg != null)
            genderChartImg.setDrawable(new TextureRegionDrawable(new TextureRegion(genderChartTex)));
    }

    private void updateAgeChart(float a0, float a18, float a24, float a65) {
        if (ageChartTex != null) ageChartTex.dispose();
        ageChartTex = buildPieChart(
            new float[]{a0, a18, a24, a65},
            new Color[]{new Color(0xFFDD88FF), new Color(0x88DDFFFF),
                        new Color(0x88FF99FF), new Color(0xFF8888FF)}, 80);
        if (ageChartImg != null)
            ageChartImg.setDrawable(new TextureRegionDrawable(new TextureRegion(ageChartTex)));
    }

    @Override
    public void refreshData() { if (currentRegion != null) loadRegion(currentRegion); }

    private ProgressBar.ProgressBarStyle makeBarStyle(int rgbaColor) {
        Color c = new Color(rgbaColor);
        Pixmap bgPix = new Pixmap(1, 18, Pixmap.Format.RGBA8888);
        bgPix.setColor(new Color(0.1f, 0.2f, 0.3f, 1f)); bgPix.fill();
        TextureRegionDrawable bg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPix))); bgPix.dispose();

        Pixmap fillPix = new Pixmap(1, 18, Pixmap.Format.RGBA8888);
        fillPix.setColor(c); fillPix.fill();
        TextureRegionDrawable fill = new TextureRegionDrawable(new TextureRegion(new Texture(fillPix))); fillPix.dispose();

        Pixmap knobPix = new Pixmap(14, 14, Pixmap.Format.RGBA8888);
        knobPix.setColor(Color.WHITE); knobPix.fillCircle(7, 7, 7);
        TextureRegionDrawable knob = new TextureRegionDrawable(new TextureRegion(new Texture(knobPix))); knobPix.dispose();

        ProgressBar.ProgressBarStyle style = new ProgressBar.ProgressBarStyle();
        style.background = bg; style.knobBefore = fill; style.knob = knob;
        return style;
    }

    private TextureRegionDrawable makeDarkBg(float r, float g, float b, float a) {
        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(r, g, b, a); pix.fill();
        TextureRegionDrawable d = new TextureRegionDrawable(new TextureRegion(new Texture(pix))); pix.dispose();
        return d;
    }

    private Label makeLabel(String text, int rgba, float scale, Skin skin) {
        Label.LabelStyle s = new Label.LabelStyle();
        s.font = skin.getFont("default-font");
        s.fontColor = new Color(rgba);
        Label l = new Label(text, s);
        l.setFontScale(scale);
        return l;
    }

    @Override
    public void dispose() {
        if (frameTex != null) frameTex.dispose();
        if (genderChartTex != null) genderChartTex.dispose();
        if (ageChartTex != null) ageChartTex.dispose();
    }
}
