package io.github.NationArchitect.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.NationArchitect.modules.Region;
import io.github.NationArchitect.screens.BaseScreen;

public class RegionalDataPanel extends UIPanel {

    private Region currentRegion;
    private Label regionNameLabel;
    private Slider securitySlider, industrySlider, healthSlider,
                   educationSlider, happinessSlider, populationSlider;

    public RegionalDataPanel(BaseScreen rootScreen) {
        super(rootScreen, "Regional Data");
    }

    @Override
    public void buildLayout() {
        Skin skin = rootScreen.getSkin();
        if (skin == null) return;

        this.clear();
        this.setBackground(makePanelBg());
        this.pad(20);

        regionNameLabel = makeLabel("SELECT A REGION", 0x00E5FFFF, 1.4f, skin);
        this.add(regionNameLabel).left().padBottom(16).colspan(2).row();

        securitySlider   = addRow("SECURITY",   50f, skin);
        industrySlider   = addRow("INDUSTRY",   50f, skin);
        healthSlider     = addRow("HEALTH",     50f, skin);
        educationSlider  = addRow("EDUCATION",  50f, skin);
        happinessSlider  = addRow("HAPPINESS",  50f, skin);
        populationSlider = addRow("POPULATION", 50f, skin);

        AnimatedMenuButton closeBtn = new AnimatedMenuButton("CLOSE", skin);
        this.add(closeBtn).colspan(2).center().padTop(16).size(200, 50);
        closeBtn.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent e, float x, float y) {
                hide();
            }
        });
    }

    private Slider addRow(String name, float defaultValue, Skin skin) {
        Label lbl = makeLabel(name, 0xCCCCCCFF, 0.85f, skin);
        Slider slider = new Slider(0f, 100f, 1f, false, makeSliderStyle());
        slider.setValue(defaultValue);
        this.add(lbl).left().width(140).padBottom(10);
        this.add(slider).width(220).height(20).padBottom(10).row();
        return slider;
    }

    public void loadRegion(Region region) {
        this.currentRegion = region;
        if (region == null) return;

        regionNameLabel.setText(region.getName().toUpperCase());

        // Listener birikimini önlemek için slider'ları temizle ve yeniden ata
        securitySlider.clearListeners();
        industrySlider.clearListeners();
        healthSlider.clearListeners();
        educationSlider.clearListeners();
        happinessSlider.clearListeners();
        populationSlider.clearListeners();

        securitySlider.setValue(region.getSecurity());
        industrySlider.setValue(region.getIndustry());
        healthSlider.setValue(region.getHealth());
        educationSlider.setValue(region.getEducation());
        happinessSlider.setValue(region.getHappiness());
        populationSlider.setValue(region.getPopulation());

        securitySlider.addListener(e   -> { region.setSecurity(securitySlider.getValue());    return false; });
        industrySlider.addListener(e   -> { region.setIndustry(industrySlider.getValue());    return false; });
        healthSlider.addListener(e     -> { region.setHealth(healthSlider.getValue());        return false; });
        educationSlider.addListener(e  -> { region.setEducation(educationSlider.getValue());  return false; });
        happinessSlider.addListener(e  -> { region.setHappiness(happinessSlider.getValue());  return false; });
        populationSlider.addListener(e -> { region.setPopulation(populationSlider.getValue()); return false; });
    }

    @Override
    public void refreshData() {
        if (currentRegion != null) loadRegion(currentRegion);
    }

    private Slider.SliderStyle makeSliderStyle() {
        Pixmap bgPix = new Pixmap(1, 20, Pixmap.Format.RGBA8888);
        bgPix.setColor(new Color(0.15f, 0.25f, 0.3f, 1f));
        bgPix.fill();
        TextureRegionDrawable bg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPix)));
        bgPix.dispose();

        Pixmap fillPix = new Pixmap(1, 20, Pixmap.Format.RGBA8888);
        fillPix.setColor(new Color(0f, 0.9f, 1f, 1f));
        fillPix.fill();
        TextureRegionDrawable fill = new TextureRegionDrawable(new TextureRegion(new Texture(fillPix)));
        fillPix.dispose();

        Pixmap knobPix = new Pixmap(18, 18, Pixmap.Format.RGBA8888);
        knobPix.setColor(Color.WHITE);
        knobPix.fillCircle(9, 9, 9);
        TextureRegionDrawable knob = new TextureRegionDrawable(new TextureRegion(new Texture(knobPix)));
        knobPix.dispose();

        Slider.SliderStyle style = new Slider.SliderStyle();
        style.background = bg;
        style.knobBefore = fill;
        style.knob = knob;
        return style;
    }

    private TextureRegionDrawable makePanelBg() {
        int w = 420, h = 580;
        Pixmap pix = new Pixmap(w, h, Pixmap.Format.RGBA8888);
        pix.setColor(new Color(0.04f, 0.1f, 0.18f, 0.95f));
        pix.fill();
        pix.setColor(new Color(0f, 0.9f, 1f, 0.6f));
        pix.drawRectangle(0, 0, w, h);
        pix.drawRectangle(1, 1, w - 2, h - 2);
        TextureRegionDrawable d = new TextureRegionDrawable(new TextureRegion(new Texture(pix)));
        pix.dispose();
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
}
