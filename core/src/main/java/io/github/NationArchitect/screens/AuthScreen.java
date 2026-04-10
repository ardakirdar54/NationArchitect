package io.github.NationArchitect.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.NationArchitect.Main;
import io.github.NationArchitect.ui.AnimatedMenuButton;

public class AuthScreen extends BaseScreen {

    private Skin localSkin;
    private Texture backgroundTexture;
    private TextField usernameField;
    private Label statusLabel;
    private AnimatedMenuButton continueBtn;
    private AnimatedMenuButton offlineBtn;

    public AuthScreen(Main game) {
        super(game);
        localSkin = new Skin(
            Gdx.files.internal("button1.json"),
            new com.badlogic.gdx.graphics.g2d.TextureAtlas(Gdx.files.internal("button1.atlas"))
        );
        backgroundTexture = new Texture(Gdx.files.internal("backgrounds/main menu screen.png"));
        buildLayout();
        addListeners();
    }

    @Override
    public void buildLayout() {
        stage.clear();

        Image bg = new Image(backgroundTexture);
        bg.setFillParent(true);
        stage.addActor(bg);

        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        Table panel = new Table();
        panel.setBackground(localSkin.getDrawable("button"));
        panel.pad(30);

        Label title = new Label("CLOUD ACCESS", makeLabelStyle(Color.WHITE));
        title.setFontScale(2f);

        TextField.TextFieldStyle tfStyle = new TextField.TextFieldStyle();
        tfStyle.font = localSkin.getFont("default-font");
        tfStyle.fontColor = Color.BLACK;
        tfStyle.messageFontColor = new Color(0x333333FF);
        tfStyle.background = localSkin.getDrawable("button");

        usernameField = new TextField("", tfStyle);
        usernameField.setMessageText("Username");

        statusLabel = new Label("Enter your username to access cloud saves.", makeLabelStyle(new Color(0xCCEEFFFF)));
        statusLabel.setWrap(true);

        continueBtn = new AnimatedMenuButton("CONTINUE", localSkin);
        offlineBtn = new AnimatedMenuButton("CONTINUE OFFLINE", localSkin);

        panel.add(title).padBottom(20).row();
        panel.add(usernameField).width(420).height(60).padBottom(14).row();
        panel.add(statusLabel).width(420).padBottom(18).row();
        panel.add(continueBtn).width(320).height(60).padBottom(10).row();
        panel.add(offlineBtn).width(320).height(60).row();

        root.add(panel).center().width(520);
    }

    @Override
    public void addListeners() {
        continueBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                attemptCloudAccess();
            }
        });

        offlineBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
    }

    private void attemptCloudAccess() {
        String username = usernameField.getText() == null ? "" : usernameField.getText().trim();
        if (username.isEmpty()) {
            statusLabel.setText("Username is required.");
            return;
        }

        if (game.login(username)) {
            game.setScreen(new MainMenuScreen(game));
            return;
        }

        if (game.register(username)) {
            game.setScreen(new MainMenuScreen(game));
            return;
        }

        String error = game.getAuthService() == null ? null : game.getAuthService().getLastError();
        statusLabel.setText("Cloud access failed" + (error == null || error.isBlank() ? "." : ": " + error));
    }

    private Label.LabelStyle makeLabelStyle(Color color) {
        return new Label.LabelStyle(localSkin.getFont("default-font"), color);
    }

    @Override
    public void show() {
        super.show();
        game.playMenuMusic();
    }

    @Override
    public void dispose() {
        super.dispose();
        if (localSkin != null) {
            localSkin.dispose();
        }
        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }
    }

    @Override
    public Skin getSkin() {
        return localSkin;
    }
}
