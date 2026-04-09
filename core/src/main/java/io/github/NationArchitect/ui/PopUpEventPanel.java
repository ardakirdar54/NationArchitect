package io.github.NationArchitect.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.NationArchitect.model.Effect.PopUpChoice;
import io.github.NationArchitect.model.Effect.PopUpEvent;
import io.github.NationArchitect.screens.GameScreen;

public class PopUpEventPanel extends UIPanel {

    private final GameScreen gameScreen;
    private Label titleLabel;
    private Label bodyLabel;

    public PopUpEventPanel(GameScreen rootScreen) {
        super(rootScreen, "Events");
        this.gameScreen = rootScreen;
    }

    @Override
    public void buildLayout() {
        Skin skin = rootScreen.getSkin();
        if (skin == null) {
            return;
        }

        clear();
        setFillParent(true);
        setTouchable(Touchable.enabled);

        Table overlay = new Table();
        overlay.setFillParent(true);
        overlay.setTouchable(Touchable.enabled);
        overlay.setBackground(makeOverlayBg());
        add(overlay).expand().fill();

        Table content = new Table();
        content.setBackground(makePanelBg());
        content.pad(26);

        Table header = new Table();
        header.add(makeLabel("POP-UP EVENTS", 0xFFE28AFF, 2.2f, skin)).expandX().left();
        AnimatedMenuButton closeBtn = new AnimatedMenuButton("X", skin);
        closeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                hide();
            }
        });
        header.add(closeBtn).size(54, 54).right();
        content.add(header).fillX().padBottom(18).row();

        Label hintLabel = makeLabel("Quarterly event briefing", 0xFFD7A8FF, 1.0f, skin);
        content.add(hintLabel).left().padBottom(14).row();

        titleLabel = makeLabel("NO EVENT", 0xFFFFFFFF, 1.8f, skin);
        content.add(titleLabel).left().padBottom(18).row();

        bodyLabel = makeLabel("", 0xF4F4F4FF, 1.1f, skin);
        bodyLabel.setWrap(true);
        content.add(bodyLabel).width(620f).left().expandY().top().row();

        AnimatedMenuButton acknowledgeBtn = new AnimatedMenuButton("ACKNOWLEDGE", skin);
        acknowledgeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                hide();
            }
        });
        content.add(acknowledgeBtn).size(260, 55).left().padTop(18);

        overlay.add(content).width(720f).height(860f).center();
    }

    @Override
    public void refreshData() {
        if (titleLabel == null || bodyLabel == null) {
            return;
        }

        PopUpEvent event = gameScreen.getGameManager().getActiveEvent();
        if (event == null) {
            event = gameScreen.getGameManager().createEvent();
        }

        if (event == null) {
            titleLabel.setText("NO EVENT");
            bodyLabel.setText("No event is active right now. Open this panel later to check for a new national development.");
            return;
        }

        titleLabel.setText(event.getTitle());
        StringBuilder body = new StringBuilder(event.getDescription());

        if (event.getChoices() != null && !event.getChoices().isEmpty()) {
            body.append("\n\nChoices:");
            for (PopUpChoice choice : event.getChoices()) {
                body.append("\n- ").append(choice.getName());
                if (choice.getDescription() != null && !choice.getDescription().isEmpty()) {
                    body.append(": ").append(choice.getDescription());
                }
            }
        }

        bodyLabel.setText(body.toString());
    }

    @Override
    public void show() {
        refreshData();
        super.show();
        toFront();
    }

    private TextureRegionDrawable makePanelBg() {
        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(new Color(0.14f, 0.08f, 0.06f, 0.96f));
        pix.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pix)));
        pix.dispose();
        return drawable;
    }

    private TextureRegionDrawable makeOverlayBg() {
        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(new Color(0f, 0f, 0f, 0.55f));
        pix.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pix)));
        pix.dispose();
        return drawable;
    }

    private Label makeLabel(String text, int rgba, float scale, Skin skin) {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = skin.getFont("default-font");
        style.fontColor = new Color(rgba);
        Label label = new Label(text, style);
        label.setFontScale(scale);
        return label;
    }
}
