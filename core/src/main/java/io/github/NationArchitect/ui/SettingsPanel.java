package io.github.NationArchitect.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.NationArchitect.screens.BaseScreen;

public class SettingsPanel extends UIPanel {

    private Slider musicSlider;
    private Slider soundSlider;
    private boolean autosaveOn = true;
    private Label autosaveStatusLabel;
    private Label autosaveDurationLabel;
    private int autosaveDuration = 5;
    private AnimatedMenuButton closeBtn;

    public SettingsPanel(BaseScreen rootScreen) {
        super(rootScreen, "Settings");
    }

    @Override
    public void buildLayout() {
        Skin skin = rootScreen.getSkin();
        if (skin == null) return;

        this.clear();
        this.pad(20);

        // Başlık
        Label title = makeLabel("SETTINGS", 0x222222FF, 2.2f, skin);
        this.add(title).colspan(2).center().padBottom(16).row();

        // ── Music & Sound Effects ──
        Label soundHeader = makeLabel("Music & Sound Effects", 0x222222FF, 1.6f, skin);
        this.add(soundHeader).colspan(2).left().padBottom(10).row();

        this.add(makeLabel("Music:", 0x555555FF, 1.3f, skin)).left().width(200);
        musicSlider = makeSlider();
        musicSlider.setValue(0.7f);
        this.add(musicSlider).width(220).height(18).padBottom(8).row();

        this.add(makeLabel("Sound Effect:", 0x555555FF, 1.3f, skin)).left().width(200);
        soundSlider = makeSlider();
        soundSlider.setValue(0.5f);
        this.add(soundSlider).width(220).height(18).padBottom(16).row();

        // ── Autosave ──
        Label autosaveHeader = makeLabel("Autosave", 0x222222FF, 1.6f, skin);
        this.add(autosaveHeader).colspan(2).left().padBottom(10).row();

        this.add(makeLabel("Autosave:", 0x555555FF, 1.3f, skin)).left().width(200);
        AnimatedMenuButton autosaveToggle = new AnimatedMenuButton("ON", skin);
        autosaveStatusLabel = makeLabel("ON", 0x00AA55FF, 0.8f, skin);
        autosaveToggle.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                autosaveOn = !autosaveOn;
                autosaveStatusLabel.setText(autosaveOn ? "ON" : "OFF");
                autosaveStatusLabel.setColor(autosaveOn ?
                    new Color(0x00AA55FF) : new Color(0xFF4444FF));
            }
        });
        this.add(autosaveToggle).size(80, 36).left().padBottom(8).row();

        this.add(makeLabel("Autosave Duration:", 0x555555FF, 1.3f, skin)).left().width(200);
        Table durationRow = new Table();
        AnimatedMenuButton minusBtn = new AnimatedMenuButton("-", skin);
        autosaveDurationLabel = makeLabel("5 min", 0x333333FF, 1.3f, skin);
        AnimatedMenuButton plusBtn = new AnimatedMenuButton("+", skin);

        minusBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                if (autosaveDuration > 1) {
                    autosaveDuration--;
                    autosaveDurationLabel.setText(autosaveDuration + " min");
                }
            }
        });
        plusBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                if (autosaveDuration < 60) {
                    autosaveDuration++;
                    autosaveDurationLabel.setText(autosaveDuration + " min");
                }
            }
        });

        durationRow.add(minusBtn).size(36, 36).padRight(8);
        durationRow.add(autosaveDurationLabel).width(60);
        durationRow.add(plusBtn).size(36, 36).padLeft(8);
        this.add(durationRow).left().padBottom(16).row();

        // ── Key Bindings ──
        Label keyHeader = makeLabel("Key Bindings", 0x222222FF, 1.6f, skin);
        this.add(keyHeader).colspan(2).left().padBottom(10).row();

        addKeyRow("Zoom in:",       "Scroll Up",   skin);
        addKeyRow("Zoom out:",      "Scroll Down", skin);
        addKeyRow("Budget Screen:", "1",           skin);
        addKeyRow("Tax Screen:",    "2",           skin);

        // ── Close ──
        closeBtn = new AnimatedMenuButton("CLOSE", skin);
        this.add(closeBtn).colspan(2).center().padTop(20).size(200, 50);

        addListeners();
    }

    private void addKeyRow(String label, String key, Skin skin) {
        this.add(makeLabel(label, 0x555555FF, 1.3f, skin)).left().width(200);
        Label keyLabel = makeLabel(key, 0x333333FF, 1.3f, skin);
        Table keyBox = new Table();
        keyBox.setBackground(makeKeyBg());
        keyBox.pad(4, 10, 4, 10);
        keyBox.add(keyLabel);
        this.add(keyBox).left().padBottom(8).row();
    }

    private void addListeners() {
        closeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });
    }

    private Slider makeSlider() {
        Pixmap bgPix = new Pixmap(1, 18, Pixmap.Format.RGBA8888);
        bgPix.setColor(new Color(0.3f, 0.3f, 0.4f, 1f));
        bgPix.fill();
        TextureRegionDrawable bg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPix)));
        bgPix.dispose();

        Pixmap fillPix = new Pixmap(1, 18, Pixmap.Format.RGBA8888);
        fillPix.setColor(new Color(0.5f, 0.5f, 0.7f, 1f));
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
        return new Slider(0f, 1f, 0.01f, false, style);
    }

   

    private TextureRegionDrawable makeKeyBg() {
        Pixmap pix = new Pixmap(5, 5, Pixmap.Format.RGBA8888);
        pix.setColor(new Color(0.35f, 0.4f, 0.6f, 1f));
        pix.fill();
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

    @Override
    public void refreshData() {}
}
