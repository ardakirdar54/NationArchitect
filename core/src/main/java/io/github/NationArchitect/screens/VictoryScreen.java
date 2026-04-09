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
import io.github.NationArchitect.ui.AnimatedMenuButton;

public class VictoryScreen extends BaseScreen {

    private final GameScreen gameScreen;
    private Skin localSkin;

    public VictoryScreen(Main game, GameScreen gameScreen) {
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
        Image background = new Image(makeBg(0.02f, 0.15f, 0.04f, 0.97f));
        background.setFillParent(true);
        stage.addActor(background);

        Table root = new Table();
        root.setFillParent(true);
        root.center();
        stage.addActor(root);

        Label.LabelStyle titleStyle = new Label.LabelStyle(localSkin.getFont("default-font"), new Color(0x00FF88FF));
        Label title = new Label("VICTORY!", titleStyle);
        title.setFontScale(5f);
        root.add(title).padBottom(30).row();

        Label.LabelStyle bodyStyle = new Label.LabelStyle(localSkin.getFont("default-font"), new Color(0xCCFFCCFF));
        Label body = new Label("You kept the nation standing long enough to reach the victory year.", bodyStyle);
        body.setFontScale(1.4f);
        root.add(body).padBottom(50).row();

        Table stats = new Table();
        stats.setBackground(makeBg(0.04f, 0.12f, 0.04f, 0.8f));
        stats.pad(20);
        Label.LabelStyle statStyle = new Label.LabelStyle(localSkin.getFont("default-font"), Color.WHITE);
        Label statTitle = new Label("FINAL STATS", statStyle);
        statTitle.setFontScale(1.3f);
        stats.add(statTitle).padBottom(15).row();

        addStatRow(stats, "Avg Happiness", gameScreen.getAverageHappiness(), statStyle);
        addStatRow(stats, "Avg Security", gameScreen.getAverageSecurity(), statStyle);
        addStatRow(stats, "Avg Industry", gameScreen.getAverageIndustry(), statStyle);
        addStatRow(stats, "Stability", gameScreen.getNationalStability(), statStyle);
        addStatRow(stats, "Treasury", gameScreen.getTreasury(), statStyle);
        root.add(stats).padBottom(50).row();

        AnimatedMenuButton menuBtn = new AnimatedMenuButton("MAIN MENU", localSkin);
        menuBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        root.add(menuBtn).size(280, 60);
    }

    private void addStatRow(Table table, String name, float value, Label.LabelStyle style) {
        Label label = new Label(String.format("%s: %.1f", name, value), style);
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
