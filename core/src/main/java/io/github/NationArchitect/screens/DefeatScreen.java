package io.github.NationArchitect.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.NationArchitect.Main;
import io.github.NationArchitect.model.metric.MetricType;
import io.github.NationArchitect.ui.AnimatedMenuButton;

public class DefeatScreen extends BaseScreen {

    private final GameScreen gameScreen;
    private Skin localSkin;

    public DefeatScreen(Main game, GameScreen gameScreen) {
        super(game);
        this.gameScreen = gameScreen;
        this.localSkin = new Skin(
            Gdx.files.internal("button1.json"),
            new TextureAtlas(Gdx.files.internal("button1.atlas"))
        );
        buildLayout();
        addListeners();
    }

    @Override
    public void buildLayout() {
        Image background = new Image(makeBg(0.15f, 0.02f, 0.02f, 0.97f));
        background.setFillParent(true);
        stage.addActor(background);

        Table root = new Table();
        root.setFillParent(true);
        root.center();
        stage.addActor(root);

        Label.LabelStyle titleStyle = new Label.LabelStyle(localSkin.getFont("default-font"), new Color(0xFF2222FF));
        Label title = new Label("DEFEAT", titleStyle);
        title.setFontScale(5f);
        root.add(title).padBottom(30).row();

        Label.LabelStyle bodyStyle = new Label.LabelStyle(localSkin.getFont("default-font"), new Color(0xFFCCCCFF));
        Label body = new Label(buildDefeatReason(), bodyStyle);
        body.setWrap(true);
        body.setFontScale(1.4f);
        root.add(body).width(900f).padBottom(50).row();

        Table stats = new Table();
        stats.setBackground(makeBg(0.12f, 0.02f, 0.02f, 0.8f));
        stats.pad(20);
        Label.LabelStyle statStyle = new Label.LabelStyle(localSkin.getFont("default-font"), Color.WHITE);
        Label statTitle = new Label("FINAL STATS", statStyle);
        statTitle.setFontScale(1.3f);
        stats.add(statTitle).padBottom(15).row();

        addStatRow(stats, "National Happiness", gameScreen.getCountryMetric(MetricType.HAPPINESS), statStyle);
        addStatRow(stats, "Unemployment", gameScreen.getCountryMetric(MetricType.UNEMPLOYMENT), statStyle);
        addStatRow(stats, "Crime Rate", gameScreen.getCountryMetric(MetricType.CRIME_RATE), statStyle);
        addStatRow(stats, "Stability", gameScreen.getNationalStability(), statStyle);
        addStatRow(stats, "Treasury", gameScreen.getTreasury(), statStyle);
        addStatRow(stats, "Year Reached", gameScreen.getGameManager().getTimeManager().getCurrentYear(), statStyle);
        root.add(stats).padBottom(50).row();

        Table buttons = new Table();
        AnimatedMenuButton retryBtn = new AnimatedMenuButton("TRY AGAIN", localSkin);
        AnimatedMenuButton menuBtn = new AnimatedMenuButton("MAIN MENU", localSkin);

        retryBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new NewGameScreen(game));
            }
        });
        menuBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        buttons.add(retryBtn).size(250, 60).padRight(20);
        buttons.add(menuBtn).size(250, 60);
        root.add(buttons);
    }

    private String buildDefeatReason() {
        String loseReason = gameScreen.getGameManager().getWinLoseManager().getLastLoseReason();
        if (loseReason != null && !loseReason.isBlank()) {
            return loseReason;
        }

        return "Your nation has fallen into chaos.";
    }

    private void addStatRow(Table table, String name, float value, Label.LabelStyle style) {
        Label label = new Label(String.format("%s: %.1f", name, value), style);
        label.setFontScale(1.1f);
        table.add(label).left().padBottom(8).row();
    }

    private void addStatRow(Table table, String name, int value, Label.LabelStyle style) {
        Label label = new Label(String.format("%s: %d", name, value), style);
        label.setFontScale(1.1f);
        table.add(label).left().padBottom(8).row();
    }

    private TextureRegionDrawable makeBg(float r, float g, float b, float a) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(r, g, b, a);
        pixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        return drawable;
    }

    @Override
    public void addListeners() {
    }

    @Override
    public void dispose() {
        super.dispose();
        if (localSkin != null) {
            localSkin.dispose();
        }
    }
}
