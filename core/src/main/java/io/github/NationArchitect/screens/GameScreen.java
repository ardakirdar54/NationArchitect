package io.github.NationArchitect.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.NationArchitect.Main;
import io.github.NationArchitect.modules.GameMap;
import io.github.NationArchitect.modules.MapModes;
import io.github.NationArchitect.modules.Region;
import io.github.NationArchitect.ui.AnimatedMenuButton;
import io.github.NationArchitect.ui.BudgetPanel;
import io.github.NationArchitect.ui.EconomyPanel;
import io.github.NationArchitect.ui.MetricsPanel;
import io.github.NationArchitect.ui.PoliciesPanel;
import io.github.NationArchitect.ui.RegionalDataPanel;
import io.github.NationArchitect.ui.SecurityPanel;
import io.github.NationArchitect.ui.TaxPanel;
import io.github.NationArchitect.ui.TransportationPanel;
import io.github.NationArchitect.ui.EducationPanel;
import io.github.NationArchitect.ui.HealthPanel;
import io.github.NationArchitect.ui.IndustryPanel;
import io.github.NationArchitect.ui.InfrastructurePanel;

public class GameScreen extends BaseScreen {

    private String nationName;
    private GameMap gameMap;
    private Skin localSkin;
    private Texture bgTexture;
    private Texture mapTexture;
    private Image mapImage;
    private Image mapOverlay;
    private Texture overlayTexture;
    private MapModes currentMapMode = MapModes.DEFAULT;
    private Region selectedRegion;
    private PoliciesPanel policiesPanel;
    private BudgetPanel budgetPanel;
    private TaxPanel taxPanel;
    private EconomyPanel economyPanel;
    private HealthPanel healthPanel;
    private InfrastructurePanel infrastructurePanel;
    private SecurityPanel securityPanel;
    private TransportationPanel transportationPanel;

    private EducationPanel educationPanel;
    private IndustryPanel industryPanel;
    private RegionalDataPanel regionalDataPanel;
    private MetricsPanel metricsPanel;

    private static final float MAP_X      = 0f;
    private static final float MAP_Y      = 0f;
    private static final float MAP_WIDTH  = WIDTH;
    private static final float MAP_HEIGHT = HEIGHT;

    public GameScreen(Main game, String nationName, GameMap gameMap) {
        super(game);
        this.nationName = nationName;
        this.gameMap = gameMap;
        localSkin = new Skin(Gdx.files.internal("button1.json"),
                new com.badlogic.gdx.graphics.g2d.TextureAtlas(Gdx.files.internal("button1.atlas")));

        bgTexture = new Texture(Gdx.files.internal("backgrounds/new game screen.png"));

        if (Gdx.files.internal("map/turkeyRegion.png").exists()) {
            mapTexture = new Texture(Gdx.files.internal("map/turkeyRegion.png"));
        } else {
            mapTexture = new Texture(Gdx.files.internal("backgrounds/pause_frame.png"));
        }

        buildLayout();
        addListeners();
    }

    @Override
    public void buildLayout() {
        stage.clear();

        Image bg = new Image(bgTexture);
        bg.setFillParent(true);
        stage.addActor(bg);

        mapImage = new Image(mapTexture);
        mapImage.setBounds(MAP_X, MAP_Y, MAP_WIDTH, MAP_HEIGHT);
        stage.addActor(mapImage);

        mapOverlay = new Image();
        mapOverlay.setBounds(MAP_X, MAP_Y, MAP_WIDTH, MAP_HEIGHT);
        mapOverlay.setVisible(false);
        mapOverlay.setTouchable(com.badlogic.gdx.scenes.scene2d.Touchable.disabled);
        stage.addActor(mapOverlay);

        buildRegionLabels();
        buildTopLeftBar();
        buildTopRightBar();
        buildBottomRightBar();
        buildTimeWidget();

        regionalDataPanel = new RegionalDataPanel(this);
        regionalDataPanel.buildLayout();
        regionalDataPanel.setPosition(WIDTH - 460f, HEIGHT / 2f - 290f);
        stage.addActor(regionalDataPanel);

        metricsPanel = new MetricsPanel(this);
        metricsPanel.buildLayout();
        float mpW = 800f, mpH = 980f;
        metricsPanel.setSize(mpW, mpH);
        metricsPanel.setPosition(WIDTH / 2f - mpW / 2f, HEIGHT / 2f - mpH / 2f);
        stage.addActor(metricsPanel);
        policiesPanel = new PoliciesPanel(this);
        policiesPanel.buildLayout();
        policiesPanel.setSize(500f, 600f); 
        policiesPanel.setPosition(WIDTH / 2f - 250f, HEIGHT / 2f - 300f);
        stage.addActor(policiesPanel);
        budgetPanel = new BudgetPanel(this);
        budgetPanel.buildLayout();
        budgetPanel.setSize(800f, 980f); 
        budgetPanel.setPosition(WIDTH / 2f - 400f, HEIGHT / 2f - 490f);
        stage.addActor(budgetPanel);
        taxPanel = new TaxPanel(this);
        stage.addActor(taxPanel); 
        taxPanel.buildLayout(); 
        taxPanel.setSize(800f, 980f);
        taxPanel.setPosition(WIDTH / 2f - 400f, HEIGHT / 2f - 490f);
        economyPanel = new EconomyPanel(this);
        stage.addActor(economyPanel); 
        economyPanel.buildLayout();   
        economyPanel.setVisible(false); 
        economyPanel.setSize(800f, 980f); 
        economyPanel.setPosition(WIDTH / 2f - 400f, HEIGHT / 2f - 490f);

        educationPanel = new EducationPanel(this);
        educationPanel.buildLayout();
        educationPanel.setSize(700f, 860f);
        educationPanel.setPosition(WIDTH / 2f - 350f, HEIGHT / 2f - 430f);
        stage.addActor(educationPanel);

        industryPanel = new IndustryPanel(this);
        industryPanel.buildLayout();
        industryPanel.setSize(700f, 860f);
        industryPanel.setPosition(WIDTH / 2f - 350f, HEIGHT / 2f - 430f);
        stage.addActor(industryPanel);

        healthPanel = new HealthPanel(this);
        healthPanel.buildLayout();
        healthPanel.setSize(700f, 860f);
        healthPanel.setPosition(WIDTH / 2f - 350f, HEIGHT / 2f - 430f);
        stage.addActor(healthPanel);

        securityPanel = new SecurityPanel(this);
        securityPanel.buildLayout();
        securityPanel.setSize(700f, 860f);
        securityPanel.setPosition(WIDTH / 2f - 350f, HEIGHT / 2f - 430f);
        stage.addActor(securityPanel);

        infrastructurePanel = new InfrastructurePanel(this);
        infrastructurePanel.buildLayout();
        infrastructurePanel.setSize(700f, 860f);
        infrastructurePanel.setPosition(WIDTH / 2f - 350f, HEIGHT / 2f - 430f);
        stage.addActor(infrastructurePanel);

        transportationPanel = new TransportationPanel(this);
        transportationPanel.buildLayout();
        transportationPanel.setSize(700f, 860f);
        transportationPanel.setPosition(WIDTH / 2f - 350f, HEIGHT / 2f - 430f);
        stage.addActor(transportationPanel);
    }


    private void onMapModeClicked(MapModes mode) {
        if (mode == currentMapMode && mode != MapModes.DEFAULT) {
            mode = MapModes.DEFAULT;
        }
        currentMapMode = mode;

        mapOverlay.setVisible(false);
        if (overlayTexture != null) { 
            overlayTexture.dispose(); 
            overlayTexture = null; 
        }

        if (mode == MapModes.DEFAULT) return;

        if (!mode.isDynamic()) {
            String path = mode.getTexturePath();
            if (path != null && Gdx.files.internal(path).exists()) {
                overlayTexture = new Texture(Gdx.files.internal(path));
                mapOverlay.setDrawable(new TextureRegionDrawable(new TextureRegion(overlayTexture)));
                mapOverlay.setVisible(true);
            }
        } else {
            generateDynamicOverlay(mode);
        }
    }

    private void generateDynamicOverlay(MapModes mode) {
        Pixmap pixmap = new Pixmap((int)MAP_WIDTH, (int)MAP_HEIGHT, Pixmap.Format.RGBA8888);
        
        for (Region r : gameMap.getRegions()) {
            float value = 0;
            switch (mode) {
                case SECURITY:   value = r.getSecurity(); break;
                case HAPPINESS:  value = r.getHappiness(); break;
                case POPULATION: value = r.getPopulation(); break; 
                case EDUCATION:  value = r.getEducation(); break;
                case INDUSTRY:   value = r.getIndustry(); break;
                default: break;
            }

            float normValue = value / 100f;

            Color regionColor = new Color(1f - normValue, normValue, 0.2f, 0.5f); 
            pixmap.setColor(regionColor);

            float centerX = r.getBounds().x + (r.getBounds().width / 2f);
            float centerY = r.getBounds().y + (r.getBounds().height / 2f);

            pixmap.fillCircle((int)centerX, (int)(MAP_HEIGHT - centerY), 100); 
        }

        overlayTexture = new Texture(pixmap);
        mapOverlay.setDrawable(new TextureRegionDrawable(new TextureRegion(overlayTexture)));
        mapOverlay.setVisible(true);
        pixmap.dispose(); 
    }

    private void buildBottomRightBar() {
        Table bar = new Table();
        bar.setBackground(makeBarBg());
        bar.pad(6);

        Table defaultCell = makeMapModeCell(MapModes.DEFAULT);
        defaultCell.addListener(new ClickListener() {
            @Override public void clicked(InputEvent e, float x, float y) {
                onMapModeClicked(MapModes.DEFAULT);
            }
        });
        bar.add(defaultCell).size(110, 70).padRight(4);

        MapModes[] modes = {
            MapModes.TERRAIN, MapModes.SECURITY, MapModes.INDUSTRY,
            MapModes.POPULATION, MapModes.EDUCATION, MapModes.HAPPINESS
        };

        for (MapModes mode : modes) {
            Table cell = makeMapModeCell(mode);
            final MapModes m = mode;
            cell.addListener(new ClickListener() {
                @Override public void clicked(InputEvent e, float x, float y) {
                    onMapModeClicked(m);
                }
            });
            bar.add(cell).size(110, 70).padRight(4);
        }

        bar.pack();
        bar.setPosition(WIDTH - bar.getWidth() - 10f, 10f);
        stage.addActor(bar);
    }

    private Table makeMapModeCell(MapModes mode) {
        Table cell = new Table();
        cell.setBackground(makeBarBg());
        cell.pad(4);

        String iconPath = mode.getIconPath();
        if (iconPath != null && Gdx.files.internal(iconPath).exists()) {
            Image icon = new Image(new Texture(Gdx.files.internal(iconPath)));
            cell.add(icon).size(36, 36).row();
        } else {
            Label.LabelStyle ls = new Label.LabelStyle();
            ls.font = localSkin.getFont("default-font");
            ls.fontColor = new Color(0x00E5FFFF);
            Label arrow = new Label("→", ls);
            arrow.setFontScale(1.4f);
            cell.add(arrow).row();
        }

        Label.LabelStyle ls = new Label.LabelStyle();
        ls.font = localSkin.getFont("default-font");
        ls.fontColor = new Color(0x00E5FFFF);
        Label lbl = new Label(mode.getLabel(), ls);
        lbl.setFontScale(0.45f);
        cell.add(lbl);

        return cell;
    }

    private void buildRegionLabels() {
        Label.LabelStyle ls = new Label.LabelStyle();
        ls.font = localSkin.getFont("default-font");
        ls.fontColor = new Color(0x00E5FFFF);

        float[][] labelPositions = {
            {145f, 820f}, {155f, 390f}, {430f, 200f},
            {680f, 490f}, {750f, 870f}, {1300f, 490f}, {1200f, 210f},
        };

        for (int i = 0; i < gameMap.getRegions().size && i < labelPositions.length; i++) {
            Region r = gameMap.getRegions().get(i);
            Label lbl = new Label(r.getName(), ls);
            lbl.setFontScale(0.7f);
            lbl.setPosition(labelPositions[i][0], labelPositions[i][1]);
            stage.addActor(lbl);
        }
    }

    

    private void buildTopLeftBar() {
    Table bar = new Table();
    bar.setBackground(makeBarBg());
    bar.pad(10); // Dış boşluk artırıldı

    String[][] buttons = {
        {"icons/policies.jpeg", "POLICIES"},
        {"icons/budget.jpeg",   "BUDGET"},
        {"icons/tax.jpeg",      "TAX"},
        {"icons/metric.jpeg",   "METRIC"},
        {"icons/economy.jpeg",  "ECONOMY"}
    };

    for (String[] btn : buttons) {
        Table cell = makeOnlyIconCell(btn[0]); 
        final String actionLabel = btn[1];
        
        cell.addListener(new ClickListener() {
            @Override 
            public void clicked(InputEvent e, float x, float y) {
                onTopLeftClicked(actionLabel);
            }
        });
        // Hücre boyutu 100x100 yapıldı
        bar.add(cell).size(100, 100).padRight(10); 
    }

    bar.pack();
    bar.setPosition(20f, HEIGHT - bar.getHeight() - 20f);
    stage.addActor(bar);
}

private Table makeOnlyIconCell(String iconPath) {
    Table cell = new Table();
    cell.setBackground(makeBarBg());
    cell.pad(6);
    if (Gdx.files.internal(iconPath).exists()) {
        Image icon = new Image(new Texture(Gdx.files.internal(iconPath)));
        // İkon boyutu 80x80 yapıldı
        cell.add(icon).size(80, 80).center(); 
    } else {
        Label placeholder = new Label("?", new Label.LabelStyle(localSkin.getFont("default-font"), Color.WHITE));
        placeholder.setFontScale(1.5f);
        cell.add(placeholder).center();
    }
    return cell;
}

    private void buildTopRightBar() {
    Table bar = new Table();
    bar.setBackground(makeBarBg());
    bar.pad(10);

    String[][] buttons = {
        {"SECURITY",       "icons/security_logo.png"},
        {"INDUSTRY",       "map modes/industry.png"},
        {"HEALTH SERVICES","icons/health_status.png"},
        {"EDUCATION",      "icons/education logo.jpeg"},
        {"TRANSPORT",      "icons/transport_logo.png"},
        {"INFRASTRUCTURE", "icons/infrastructure_logo.png"}
    };

    for (String[] btn : buttons) {
        Table cell = makeIconCell(btn[0], btn[1]); 
        final String lbl = btn[0];
        cell.addListener(new ClickListener() {
            @Override public void clicked(InputEvent e, float x, float y) {
                onTopRightClicked(lbl);
            }
        });
        bar.add(cell).size(140, 100).padRight(6);
    }

    bar.pack();
    bar.setPosition(WIDTH - bar.getWidth() - 20f, HEIGHT - bar.getHeight() - 20f);
    stage.addActor(bar);
}

private Table makeIconCell(String label, String iconPath) {
    Table cell = new Table();
    cell.setBackground(makeBarBg());
    cell.pad(6);

    if (Gdx.files.internal(iconPath).exists()) {
        Image icon = new Image(new Texture(Gdx.files.internal(iconPath)));
        cell.add(icon).size(50, 50).padBottom(4).row();
    }

    Label.LabelStyle ls = new Label.LabelStyle(localSkin.getFont("default-font"), new Color(0x00E5FFFF));
    Label lbl = new Label(label, ls);
    lbl.setFontScale(0.6f); // Yazı scale 0.45'ten 0.6'ya çıkarıldı
    cell.add(lbl);
    return cell;
}

    private void onTopLeftClicked(String label) {
    if (policiesPanel != null) policiesPanel.hide();
    if (metricsPanel != null) metricsPanel.hide();
    if (budgetPanel != null) budgetPanel.hide();
    if (taxPanel != null) taxPanel.hide();
    if (economyPanel != null) economyPanel.hide();
    if (educationPanel != null) educationPanel.hide();
    if (industryPanel != null) industryPanel.hide();

    switch (label) {
        case "POLICIES":
            policiesPanel.show();
            Gdx.app.log("GameScreen", "Policies Panel Opened");
            break;

        case "METRIC":
            if (selectedRegion != null) {
                metricsPanel.loadRegion(selectedRegion);
            }
            metricsPanel.show();
            Gdx.app.log("GameScreen", "Metrics Panel Opened");
            break;

        case "BUDGET":
            budgetPanel.show(); 
            Gdx.app.log("GameScreen", "Budget Panel Triggered");
            break;

        case "TAX":
             taxPanel.show();
            Gdx.app.log("GameScreen", "Tax Settings Triggered");
            break;

        case "ECONOMY":
            economyPanel.show();
            Gdx.app.log("GameScreen", "National Economy Overview Triggered");
            break;

        default:
            Gdx.app.log("GameScreen", "Unknown Action: " + label);
            break;
    }
}

    private void onTopRightClicked(String label) {
    if (policiesPanel != null) policiesPanel.hide();
    if (metricsPanel != null) metricsPanel.hide();
    if (budgetPanel != null) budgetPanel.hide();
    if (taxPanel != null) taxPanel.hide();
    if (economyPanel != null) economyPanel.hide();
    if (educationPanel != null) educationPanel.hide();
    if (industryPanel != null) industryPanel.hide();
    if (healthPanel != null) healthPanel.hide();
    if (securityPanel != null) securityPanel.hide();
    if (infrastructurePanel != null) infrastructurePanel.hide();
    if (transportationPanel != null) transportationPanel.hide();

    switch (label) {
        case "EDUCATION":
            if (selectedRegion != null) educationPanel.setRegion(selectedRegion);
            educationPanel.show();
            Gdx.app.log("GameScreen", "Education Panel Opened");
            break;

        case "INDUSTRY":
            if (selectedRegion != null) industryPanel.setRegion(selectedRegion);
            industryPanel.show();
            Gdx.app.log("GameScreen", "Industry Panel Opened");
            break;

        case "HEALTH":
            if (selectedRegion != null) healthPanel.setRegion(selectedRegion);
            healthPanel.show();
            Gdx.app.log("GameScreen", "Health Panel Opened");
            break;

        case "SECURITY":
            if (selectedRegion != null) securityPanel.setRegion(selectedRegion);
            securityPanel.show();
            Gdx.app.log("GameScreen", "Security Panel Opened");
            break;

        case "INFRASTRUCTURE":
            if (selectedRegion != null) infrastructurePanel.setRegion(selectedRegion);
            infrastructurePanel.show();
            Gdx.app.log("GameScreen", "Infrastructure Panel Opened");
            break;

        case "TRANSPORTATION":
            if (selectedRegion != null) transportationPanel.setRegion(selectedRegion);
            transportationPanel.show();
            Gdx.app.log("GameScreen", "Transportation Panel Opened");
            break;

        default:
            Gdx.app.log("GameScreen", label + " clicked - TO DO");
            break;
    }
}

    private void buildTimeWidget() {
    Table widget = new Table();
    widget.setBackground(makeBarBg());
    widget.pad(15);

    Label dateLabel = new Label("2/2026", new Label.LabelStyle(localSkin.getFont("default-font"), new Color(0x00E5FFFF)));
    dateLabel.setFontScale(1.5f); // Tarih büyüdü

    AnimatedMenuButton resumeBtn = new AnimatedMenuButton("▶", localSkin);
    AnimatedMenuButton pauseBtn  = new AnimatedMenuButton("⏸", localSkin);
    AnimatedMenuButton slowBtn   = new AnimatedMenuButton("−", localSkin);
    AnimatedMenuButton speedBtn  = new AnimatedMenuButton("+", localSkin);

    widget.add(dateLabel).colspan(4).center().padBottom(12).row();
    widget.add(resumeBtn).size(60, 60).padRight(6);
    widget.add(pauseBtn).size(60, 60).padRight(6);
    widget.add(slowBtn).size(60, 60).padRight(6);
    widget.add(speedBtn).size(60, 60);

    widget.pack();
    widget.setPosition(20f, 20f);
    stage.addActor(widget);
}

    @Override
    public void addListeners() {
        mapImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Tıklama koordinatlarını ölçeklendir
                float regionX = ((x - 60f) / MAP_WIDTH) * 1300f;
                float regionY = (y / MAP_HEIGHT) * 580f;

                Region clicked = gameMap.getRegionAt(regionX, regionY);
                if (clicked != null) {
                    selectedRegion = clicked;
                    regionalDataPanel.loadRegion(clicked);
                    regionalDataPanel.show();
                    if (metricsPanel.isOpen) metricsPanel.loadRegion(clicked);
                    if (educationPanel != null && educationPanel.isOpen) educationPanel.loadRegion(clicked);
                    if (industryPanel != null && industryPanel.isOpen) industryPanel.loadRegion(clicked);
                    if (budgetPanel != null && budgetPanel.isOpen()) {
                    budgetPanel.loadRegion(clicked);
                }
                }
            }
        });
    }

    private TextureRegionDrawable makeBarBg() {
        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(new Color(0.04f, 0.1f, 0.2f, 0.85f));
        pix.fill();
        TextureRegionDrawable d = new TextureRegionDrawable(new TextureRegion(new Texture(pix)));
        pix.dispose();
        return d;
    }

    @Override
    public void dispose() {
        super.dispose();
        if (localSkin != null) localSkin.dispose();
        if (bgTexture != null) bgTexture.dispose();
        if (mapTexture != null) mapTexture.dispose();
        if (overlayTexture != null) overlayTexture.dispose();
        if (educationPanel != null) educationPanel.dispose();
        if (industryPanel != null) industryPanel.dispose();
    }

    @Override public void show() { super.show(); Gdx.input.setInputProcessor(stage); }
    @Override public Skin getSkin() { return localSkin; }
}
