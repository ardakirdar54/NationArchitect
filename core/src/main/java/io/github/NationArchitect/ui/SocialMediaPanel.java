package io.github.NationArchitect.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.NationArchitect.model.social.SocialMediaComment;
import io.github.NationArchitect.modules.UIRegion;
import io.github.NationArchitect.screens.GameScreen;

public class SocialMediaPanel extends UIPanel {

    private final GameScreen gameScreen;
    private Label feedLabel;

    public SocialMediaPanel(GameScreen rootScreen) {
        super(rootScreen, "Social Feed");
        this.gameScreen = rootScreen;
    }

    @Override
    public void buildLayout() {
        Skin skin = rootScreen.getSkin();
        if (skin == null) {
            return;
        }

        clear();
        setBackground(makePanelBg());
        pad(24);

        Table header = new Table();
        header.add(makeLabel("SOCIAL MEDIA", 0x00E5FFFF, 2.1f, skin)).expandX().left();
        AnimatedMenuButton closeBtn = new AnimatedMenuButton("X", skin);
        closeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                hide();
            }
        });
        header.add(closeBtn).size(54, 54).right();
        add(header).fillX().padBottom(18).row();

        Label hint = makeLabel("National mood and public reaction feed", 0xAACCEEFF, 1.0f, skin);
        add(hint).left().padBottom(18).row();

        feedLabel = makeLabel("", 0xF4F8FFFF, 1.0f, skin);
        feedLabel.setWrap(true);
        add(feedLabel).width(630f).left().top().expandY().fillY().row();

        AnimatedMenuButton refreshBtn = new AnimatedMenuButton("REFRESH FEED", skin);
        refreshBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                refreshData();
            }
        });
        add(refreshBtn).size(260, 55).left().padTop(18);
    }

    @Override
    public void refreshData() {
        if (feedLabel == null) {
            return;
        }

        UIRegion happiest = null;
        UIRegion mostSecure = null;
        float avgHappiness = 0f;
        float avgSecurity = 0f;
        int count = gameScreen.getGameMap().getRegions().size;

        for (UIRegion region : gameScreen.getGameMap().getRegions()) {
            avgHappiness += region.getHappiness();
            avgSecurity += region.getSecurity();
            if (happiest == null || region.getHappiness() > happiest.getHappiness()) {
                happiest = region;
            }
            if (mostSecure == null || region.getSecurity() > mostSecure.getSecurity()) {
                mostSecure = region;
            }
        }

        if (count > 0) {
            avgHappiness /= count;
            avgSecurity /= count;
        }

        UIRegion selected = gameScreen.getSelectedRegion();
        SocialMediaComment generatedPost = gameScreen.getGameManager().generatePost();

        StringBuilder builder = new StringBuilder();
        builder.append("@statewatch  National average happiness is ")
            .append((int) avgHappiness)
            .append("% and security is ")
            .append((int) avgSecurity)
            .append("%.\n\n");

        if (generatedPost != null) {
            builder.append(generatedPost.getAuthor())
                .append("  ")
                .append(generatedPost.getContent())
                .append("\n\n");
        } else {
            builder.append("@dailybrief  Citizens are watching services, taxes, and stability closely today.\n\n");
        }

        if (happiest != null) {
            builder.append("@citypulse  ")
                .append(happiest.getName())
                .append(" is leading the country with ")
                .append((int) happiest.getHappiness())
                .append("% happiness.\n\n");
        }

        if (mostSecure != null) {
            builder.append("@safetyindex  ")
                .append(mostSecure.getName())
                .append(" is currently the most secure region at ")
                .append((int) mostSecure.getSecurity())
                .append("%.\n\n");
        }

        if (selected != null) {
            builder.append("@regionaldesk  ")
                .append(selected.getName())
                .append(" has population ")
                .append(String.format("%,d", selected.getTotalPopulation()))
                .append(", education ")
                .append((int) selected.getEducation())
                .append("%, and health ")
                .append((int) selected.getHealth())
                .append("%.");
        } else {
            builder.append("@regionaldesk  Select a region on the map to see local reaction.");
        }

        feedLabel.setText(builder.toString());
    }

    private TextureRegionDrawable makePanelBg() {
        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(new Color(0.05f, 0.08f, 0.14f, 0.96f));
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
