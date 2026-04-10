package io.github.NationArchitect.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.NationArchitect.controller.Settings;
import io.github.NationArchitect.screens.BaseScreen;

public class SettingsPanel extends UIPanel {

    private Slider musicSlider;
    private Slider soundSlider;
    private boolean autosaveOn = true;
    private Label autosaveStatusLabel;
    private Label autosaveDurationLabel;
    private int autosaveDuration = 5;
    private AnimatedMenuButton closeBtn;
    private final Settings settings;

    public SettingsPanel(BaseScreen rootScreen) {
        super(rootScreen, "Settings");
        this.settings = rootScreen.getGame().getSettings();
    }

    @Override
    public void buildLayout() {
        Skin skin = rootScreen.getSkin();
        if (skin == null) {
            return;
        }

        clear();
        pad(20);
        autosaveOn = settings.getAutoSave();
        autosaveDuration = settings.getAutoSaveDurationMinutes();

        Label title = makeLabel("SETTINGS", 0x222222FF, 2.2f, skin);
        add(title).colspan(2).center().padBottom(16).row();

        Label soundHeader = makeLabel("Music & Sound Effects", 0x222222FF, 1.6f, skin);
        add(soundHeader).colspan(2).left().padBottom(10).row();

        add(makeLabel("Music:", 0x555555FF, 1.3f, skin)).left().width(200);
        musicSlider = makeSlider();
        musicSlider.setValue((float) (settings.getMusicRate() / 100.0));
        add(musicSlider).width(220).height(18).padBottom(8).row();

        add(makeLabel("Sound Effect:", 0x555555FF, 1.3f, skin)).left().width(200);
        soundSlider = makeSlider();
        soundSlider.setValue((float) (settings.getSoundRate() / 100.0));
        add(soundSlider).width(220).height(18).padBottom(16).row();

        Label autosaveHeader = makeLabel("Autosave", 0x222222FF, 1.6f, skin);
        add(autosaveHeader).colspan(2).left().padBottom(10).row();

        add(makeLabel("Autosave:", 0x555555FF, 1.3f, skin)).left().width(200);
        AnimatedMenuButton autosaveToggle = new AnimatedMenuButton(autosaveOn ? "ON" : "OFF", skin);
        autosaveStatusLabel = makeLabel(
            autosaveOn ? "ON" : "OFF",
            autosaveOn ? 0x00AA55FF : 0xFF4444FF,
            0.8f,
            skin
        );
        autosaveToggle.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                autosaveOn = !autosaveOn;
                autosaveToggle.setText(autosaveOn ? "ON" : "OFF");
                autosaveStatusLabel.setText(autosaveOn ? "ON" : "OFF");
                autosaveStatusLabel.setColor(autosaveOn ? new Color(0x00AA55FF) : new Color(0xFF4444FF));
                settings.setAutoSave(autosaveOn);
                rootScreen.getGame().saveSettings();
            }
        });
        Table autosaveRow = new Table();
        autosaveRow.add(autosaveToggle).size(80, 36).padRight(10);
        autosaveRow.add(autosaveStatusLabel).left();
        add(autosaveRow).left().padBottom(8).row();

        add(makeLabel("Autosave Duration:", 0x555555FF, 1.3f, skin)).left().width(200);
        Table durationRow = new Table();
        AnimatedMenuButton minusBtn = new AnimatedMenuButton("-", skin);
        autosaveDurationLabel = makeLabel(autosaveDuration + " min", 0x333333FF, 1.3f, skin);
        AnimatedMenuButton plusBtn = new AnimatedMenuButton("+", skin);

        minusBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                if (autosaveDuration > 1) {
                    autosaveDuration--;
                    settings.setAutoSaveDurationMinutes(autosaveDuration);
                    autosaveDurationLabel.setText(autosaveDuration + " min");
                    rootScreen.getGame().saveSettings();
                }
            }
        });
        plusBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                if (autosaveDuration < 60) {
                    autosaveDuration++;
                    settings.setAutoSaveDurationMinutes(autosaveDuration);
                    autosaveDurationLabel.setText(autosaveDuration + " min");
                    rootScreen.getGame().saveSettings();
                }
            }
        });

        durationRow.add(minusBtn).size(36, 36).padRight(8);
        durationRow.add(autosaveDurationLabel).width(60);
        durationRow.add(plusBtn).size(36, 36).padLeft(8);
        add(durationRow).left().padBottom(16).row();

        closeBtn = new AnimatedMenuButton("CLOSE", skin);
        add(closeBtn).colspan(2).center().padTop(20).size(200, 50);

        addListeners();
    }

    private void addListeners() {
        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rootScreen.getGame().setMusicVolume(musicSlider.getValue());
            }
        });

        soundSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                settings.setSoundRate(Math.round(soundSlider.getValue() * 100f));
                rootScreen.getGame().saveSettings();
            }
        });

        closeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                rootScreen.getGame().saveSettings();
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

    private Label makeLabel(String text, int rgba, float scale, Skin skin) {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = skin.getFont("default-font");
        style.fontColor = new Color(rgba);
        Label label = new Label(text, style);
        label.setFontScale(scale);
        return label;
    }

    @Override
    public void refreshData() {
    }
}
