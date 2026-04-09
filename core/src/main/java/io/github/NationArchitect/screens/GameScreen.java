package io.github.NationArchitect.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.NationArchitect.Main;
import io.github.NationArchitect.controller.GameManager;
import io.github.NationArchitect.controller.WinLoseManager;
import io.github.NationArchitect.controller.savemanager.SaveData;
import io.github.NationArchitect.model.economy.CountryEconomy;
import io.github.NationArchitect.model.land.Country;
import io.github.NationArchitect.model.metric.MetricType;
import io.github.NationArchitect.modules.GameMap;
import io.github.NationArchitect.modules.MapModes;
import io.github.NationArchitect.modules.UIRegion;
import io.github.NationArchitect.ui.AnimatedMenuButton;
import io.github.NationArchitect.ui.BudgetPanel;
import io.github.NationArchitect.ui.EconomyPanel;
import io.github.NationArchitect.ui.EducationPanel;
import io.github.NationArchitect.ui.HealthPanel;
import io.github.NationArchitect.ui.IndustryPanel;
import io.github.NationArchitect.ui.InfrastructurePanel;
import io.github.NationArchitect.ui.MetricsPanel;
import io.github.NationArchitect.ui.PoliciesPanel;
import io.github.NationArchitect.ui.PopUpEventPanel;
import io.github.NationArchitect.ui.RegionalDataPanel;
import io.github.NationArchitect.ui.BuildingListPanel;
import io.github.NationArchitect.ui.SecurityPanel;
import io.github.NationArchitect.ui.SocialMediaPanel;
import io.github.NationArchitect.ui.TaxPanel;
import io.github.NationArchitect.ui.TransportationPanel;

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
    private UIRegion selectedRegion;
    private PoliciesPanel policiesPanel;
    private BudgetPanel budgetPanel;
    private TaxPanel taxPanel;
    private EconomyPanel economyPanel;
    private HealthPanel healthPanel;
    private InfrastructurePanel infrastructurePanel;
    private SecurityPanel securityPanel;
    private BuildingListPanel buildingListPanel;
    private TransportationPanel transportationPanel;
    private EducationPanel educationPanel;
    private IndustryPanel industryPanel;
    private RegionalDataPanel regionalDataPanel;
    private MetricsPanel metricsPanel;
    private SocialMediaPanel socialMediaPanel;
    private PopUpEventPanel popUpEventPanel;
    private GameManager gameManager;
    private Label dateLabel;
    private Label speedLabel;
    private Label selectedRegionLabel;
    private boolean endStateHandled;

    private static final float MAP_X = 0f;
    private static final float MAP_Y = 0f;
    private static final float MAP_WIDTH = WIDTH;
    private static final float MAP_HEIGHT = HEIGHT;

    public GameScreen(Main game, String nationName, GameMap gameMap) {
        super(game);
        this.nationName = nationName;
        this.gameMap = gameMap;
        this.gameManager = new GameManager(
            gameMap,
            nationName,
            game.getSettings(),
            game.getCloudApiBaseUrl(),
            game.getAuthService()
        );
        this.endStateHandled = false;
        this.gameManager.startGame();
        initAssets();
        buildLayout();
        addListeners();
    }

    public GameScreen(Main game, String nationName, GameMap gameMap, SaveData initialState) {
        super(game);
        this.nationName = nationName == null || nationName.trim().isEmpty() ? "NationArchitect" : nationName;
        this.gameMap = gameMap == null ? new GameMap(resolveMapName(initialState)) : gameMap;
        Country initialCountry = initialState == null ? null : initialState.restoreCountry();
        syncMapRegions(initialCountry);
        this.gameManager = new GameManager(
            this.gameMap,
            initialCountry,
            game.getSettings(),
            game.getCloudApiBaseUrl(),
            game.getAuthService()
        );
        this.endStateHandled = false;
        if (initialState != null) {
            this.gameManager.applyLoadedSave(initialState);
            syncMapRegions(this.gameManager.getCountry());
        }
        this.gameManager.startGame();
        initAssets();
        buildLayout();
        addListeners();
    }

    public GameScreen(Main game, SaveData saveData) {
        super(game);
        this.nationName = saveData == null || saveData.getCountryName() == null ? "NationArchitect" : saveData.getCountryName();
        this.gameMap = new GameMap(resolveMapName(saveData));
        Country loadedCountry = saveData == null ? null : saveData.restoreCountry();
        syncMapRegions(loadedCountry);
        this.gameManager = new GameManager(
            gameMap,
            loadedCountry,
            game.getSettings(),
            game.getCloudApiBaseUrl(),
            game.getAuthService()
        );
        this.endStateHandled = false;
        this.gameManager.applyLoadedSave(saveData);
        syncMapRegions(this.gameManager.getCountry());
        this.gameManager.startGame();
        initAssets();
        buildLayout();
        addListeners();
    }

    private void initAssets() {
        localSkin = new Skin(
            Gdx.files.internal("button1.json"),
            new com.badlogic.gdx.graphics.g2d.TextureAtlas(Gdx.files.internal("button1.atlas"))
        );

        bgTexture = new Texture(Gdx.files.internal("backgrounds/new game screen.png"));
        if (Gdx.files.internal("map/turkeyRegion.png").exists()) {
            mapTexture = new Texture(Gdx.files.internal("map/turkeyRegion.png"));
        } else {
            mapTexture = new Texture(Gdx.files.internal("backgrounds/pause_frame.png"));
        }
    }

    private String resolveMapName(SaveData saveData) {
        if (saveData == null || saveData.getMapType() == null || saveData.getMapType().trim().isEmpty()) {
            return "map1";
        }
        return saveData.getMapType();
    }

    private void syncMapRegions(Country loadedCountry) {
        if (loadedCountry == null || loadedCountry.getRegions() == null || gameMap == null || gameMap.getRegions() == null) {
            return;
        }
        for (int index = 0; index < gameMap.getRegions().size && index < loadedCountry.getRegions().length; index++) {
            if (loadedCountry.getRegions()[index] != null) {
                gameMap.getRegions().get(index).setBackingRegion(loadedCountry.getRegions()[index]);
            }
        }
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
        metricsPanel.setSize(800f, 980f);
        metricsPanel.setPosition(WIDTH / 2f - 400f, HEIGHT / 2f - 490f);
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
        taxPanel.buildLayout();
        taxPanel.setSize(800f, 980f);
        taxPanel.setPosition(WIDTH / 2f - 400f, HEIGHT / 2f - 490f);
        stage.addActor(taxPanel);

        economyPanel = new EconomyPanel(this);
        economyPanel.buildLayout();
        economyPanel.setVisible(false);
        economyPanel.setSize(800f, 980f);
        economyPanel.setPosition(WIDTH / 2f - 400f, HEIGHT / 2f - 490f);
        stage.addActor(economyPanel);

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
        securityPanel.setPosition(WIDTH / 2f - 580f, HEIGHT / 2f - 430f);
        stage.addActor(securityPanel);

        buildingListPanel = new BuildingListPanel(this);
        buildingListPanel.buildLayout();
        buildingListPanel.setSize(560f, 860f);
        buildingListPanel.setPosition(WIDTH / 2f + 80f, HEIGHT / 2f - 430f);
        stage.addActor(buildingListPanel);

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

        socialMediaPanel = new SocialMediaPanel(this);
        socialMediaPanel.buildLayout();
        socialMediaPanel.setSize(720f, 860f);
        socialMediaPanel.setPosition(WIDTH / 2f - 360f, HEIGHT / 2f - 430f);
        stage.addActor(socialMediaPanel);

        popUpEventPanel = new PopUpEventPanel(this);
        popUpEventPanel.buildLayout();
        popUpEventPanel.setSize(720f, 860f);
        popUpEventPanel.setPosition(WIDTH / 2f - 360f, HEIGHT / 2f - 430f);
        stage.addActor(popUpEventPanel);
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

        if (mode == MapModes.DEFAULT) {
            return;
        }

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
        Pixmap pixmap = new Pixmap((int) MAP_WIDTH, (int) MAP_HEIGHT, Pixmap.Format.RGBA8888);

        for (UIRegion region : gameMap.getRegions()) {
            float value = 0f;
            switch (mode) {
                case SECURITY:
                    value = region.getSecurity();
                    break;
                case HAPPINESS:
                    value = region.getHappiness();
                    break;
                case POPULATION:
                    value = region.getPopulation();
                    break;
                case EDUCATION:
                    value = region.getEducation();
                    break;
                case INDUSTRY:
                    value = region.getIndustry();
                    break;
                default:
                    break;
            }

            float normalized = Math.max(0f, Math.min(1f, value / 100f));
            pixmap.setColor(new Color(1f - normalized, normalized, 0.2f, 0.5f));

            float centerX = region.getBounds().x + (region.getBounds().width / 2f);
            float centerY = region.getBounds().y + (region.getBounds().height / 2f);
            pixmap.fillCircle((int) centerX, (int) (MAP_HEIGHT - centerY), 100);
        }

        overlayTexture = new Texture(pixmap);
        mapOverlay.setDrawable(new TextureRegionDrawable(new TextureRegion(overlayTexture)));
        mapOverlay.setVisible(true);
        pixmap.dispose();
    }

    private void buildBottomRightBar() {
        selectedRegionLabel = new Label("", new Label.LabelStyle(localSkin.getFont("default-font"), new Color(0x00E5FFFF)));
        selectedRegionLabel.setFontScale(0.9f);
        selectedRegionLabel.setPosition(WIDTH - 360f, 92f);
        selectedRegionLabel.setVisible(false);
        stage.addActor(selectedRegionLabel);

        Table bar = new Table();
        bar.setBackground(makeBarBg());
        bar.pad(6);

        Table defaultCell = makeMapModeCell(MapModes.DEFAULT);
        defaultCell.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
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
            final MapModes selectedMode = mode;
            cell.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent e, float x, float y) {
                    onMapModeClicked(selectedMode);
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
            Label arrow = new Label("->", new Label.LabelStyle(localSkin.getFont("default-font"), new Color(0x00E5FFFF)));
            arrow.setFontScale(1.1f);
            cell.add(arrow).row();
        }

        Label label = new Label(mode.getLabel(), new Label.LabelStyle(localSkin.getFont("default-font"), new Color(0x00E5FFFF)));
        label.setFontScale(0.45f);
        cell.add(label);
        return cell;
    }

    private void buildRegionLabels() {
        Label.LabelStyle style = new Label.LabelStyle(localSkin.getFont("default-font"), new Color(0x00E5FFFF));
        float[][] positions = {
            {145f, 820f}, {155f, 390f}, {430f, 200f},
            {680f, 490f}, {750f, 870f}, {1300f, 490f}, {1200f, 210f}
        };

        for (int i = 0; i < gameMap.getRegions().size && i < positions.length; i++) {
            UIRegion region = gameMap.getRegions().get(i);
            Label label = new Label(region.getName(), style);
            label.setFontScale(0.7f);
            label.setPosition(positions[i][0], positions[i][1]);
            stage.addActor(label);
        }
    }

    private void buildTopLeftBar() {
        Table bar = new Table();
        bar.setBackground(makeBarBg());
        bar.pad(10);

        String[][] buttons = {
            {"icons/policies.jpeg", "POLICIES"},
            {"icons/budget.jpeg", "BUDGET"},
            {"icons/tax.jpeg", "TAX"},
            {"icons/metric.jpeg", "METRIC"},
            {"icons/economy.jpeg", "ECONOMY"},
            {"icons/freedomofthepress.png", "SOCIAL"},
            {"icons/public happines logo.png", "EVENTS"}
        };

        for (String[] button : buttons) {
            Table cell = makeOnlyIconCell(button[0], button[1]);
            final String actionLabel = button[1];
            cell.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent e, float x, float y) {
                    onTopLeftClicked(actionLabel);
                }
            });
            bar.add(cell).size(100, 100).padRight(10);
        }

        bar.pack();
        bar.setPosition(20f, HEIGHT - bar.getHeight() - 20f);
        stage.addActor(bar);
    }

    private Table makeOnlyIconCell(String iconPath, String fallbackText) {
        Table cell = new Table();
        cell.setBackground(makeBarBg());
        cell.pad(6);
        if (Gdx.files.internal(iconPath).exists()) {
            Image icon = new Image(new Texture(Gdx.files.internal(iconPath)));
            cell.add(icon).size(80, 80).center();
        } else {
            Label placeholder = new Label(
                fallbackText.substring(0, Math.min(3, fallbackText.length())),
                new Label.LabelStyle(localSkin.getFont("default-font"), Color.WHITE)
            );
            placeholder.setFontScale(0.9f);
            cell.add(placeholder).center();
        }
        return cell;
    }

    private void buildTopRightBar() {
        Table bar = new Table();
        bar.setBackground(makeBarBg());
        bar.pad(10);

        String[][] buttons = {
            {"BUILDINGS", "icons/security_logo.png"},
            {"SECURITY", "icons/security_logo.png"},
            {"INDUSTRY", "map modes/industry.png"},
            {"HEALTH", "icons/health_status.png"},
            {"EDUCATION", "icons/education logo.jpeg"},
            {"TRANSPORTATION", "icons/transport_logo.png"},
            {"INFRASTRUCTURE", "icons/infrastructure_logo.png"}
        };

        for (String[] button : buttons) {
            Table cell = makeIconCell(button[0], button[1]);
            final String label = button[0];
            cell.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent e, float x, float y) {
                    onTopRightClicked(label);
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

        Label text = new Label(label, new Label.LabelStyle(localSkin.getFont("default-font"), new Color(0x00E5FFFF)));
        text.setFontScale(0.6f);
        cell.add(text);
        return cell;
    }

    private void onTopLeftClicked(String label) {
        hideAllPanels();

        switch (label) {
            case "POLICIES":
                policiesPanel.show();
                break;
            case "METRIC":
                metricsPanel.loadRegion(selectedRegion);
                metricsPanel.show();
                break;
            case "BUDGET":
                if (selectedRegion == null) {
                    return;
                }
                budgetPanel.loadRegion(selectedRegion);
                budgetPanel.show();
                break;
            case "TAX":
                if (selectedRegion == null) {
                    return;
                }
                taxPanel.loadRegion(selectedRegion);
                taxPanel.show();
                break;
            case "ECONOMY":
                economyPanel.loadRegion(selectedRegion);
                economyPanel.show();
                break;
            case "SOCIAL":
                socialMediaPanel.refreshData();
                socialMediaPanel.show();
                break;
            case "EVENTS":
                popUpEventPanel.refreshData();
                popUpEventPanel.show();
                break;
            default:
                break;
        }
    }

    private void onTopRightClicked(String label) {
        hideAllPanels();

        switch (label) {
            case "EDUCATION":
                if (selectedRegion != null) {
                    educationPanel.setRegion(selectedRegion);
                }
                educationPanel.show();
                break;
            case "INDUSTRY":
                if (selectedRegion != null) {
                    industryPanel.setRegion(selectedRegion);
                }
                industryPanel.show();
                break;
            case "HEALTH":
                if (selectedRegion != null) {
                    healthPanel.setRegion(selectedRegion);
                }
                healthPanel.show();
                break;
            case "SECURITY":
                if (selectedRegion != null) {
                    securityPanel.setRegion(selectedRegion);
                }
                securityPanel.show();
                break;
            case "BUILDINGS":
                if (selectedRegion != null) {
                    buildingListPanel.loadRegion(selectedRegion);
                }
                buildingListPanel.show();
                break;
            case "INFRASTRUCTURE":
                if (selectedRegion != null) {
                    infrastructurePanel.setRegion(selectedRegion);
                }
                infrastructurePanel.show();
                break;
            case "TRANSPORTATION":
                if (selectedRegion != null) {
                    transportationPanel.setRegion(selectedRegion);
                }
                transportationPanel.show();
                break;
            default:
                break;
        }
    }

    private void hideAllPanels() {
        if (policiesPanel != null) policiesPanel.hide();
        if (metricsPanel != null) metricsPanel.hide();
        if (budgetPanel != null) budgetPanel.hide();
        if (taxPanel != null) taxPanel.hide();
        if (economyPanel != null) economyPanel.hide();
        if (educationPanel != null) educationPanel.hide();
        if (industryPanel != null) industryPanel.hide();
        if (healthPanel != null) healthPanel.hide();
        if (securityPanel != null) securityPanel.hide();
        if (buildingListPanel != null) buildingListPanel.hide();
        if (infrastructurePanel != null) infrastructurePanel.hide();
        if (transportationPanel != null) transportationPanel.hide();
        if (socialMediaPanel != null) socialMediaPanel.hide();
        if (popUpEventPanel != null) popUpEventPanel.hide();
    }

    private void buildTimeWidget() {
        Table widget = new Table();
        widget.setBackground(makeBarBg());
        widget.pad(15);

        dateLabel = new Label("01/01/2026", new Label.LabelStyle(localSkin.getFont("default-font"), new Color(0x00E5FFFF)));
        dateLabel.setFontScale(1.5f);
        speedLabel = new Label("1x", new Label.LabelStyle(localSkin.getFont("default-font"), Color.WHITE));
        speedLabel.setFontScale(1.1f);

        AnimatedMenuButton resumeBtn = new AnimatedMenuButton(">", localSkin);
        AnimatedMenuButton pauseBtn = new AnimatedMenuButton("||", localSkin);
        AnimatedMenuButton slowBtn = new AnimatedMenuButton("-", localSkin);
        AnimatedMenuButton speedBtn = new AnimatedMenuButton("+", localSkin);

        widget.add(dateLabel).colspan(4).center().padBottom(6).row();
        widget.add(speedLabel).colspan(4).center().padBottom(12).row();
        widget.add(resumeBtn).size(60, 60).padRight(6);
        widget.add(pauseBtn).size(60, 60).padRight(6);
        widget.add(slowBtn).size(60, 60).padRight(6);
        widget.add(speedBtn).size(60, 60);

        widget.pack();
        widget.setPosition(20f, 20f);
        stage.addActor(widget);

        resumeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameManager.resumeGame();
                updateSpeedLabel();
            }
        });

        pauseBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameManager.pauseGame();
                updateSpeedLabel();
            }
        });

        slowBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameManager.slowDownGame();
                updateSpeedLabel();
            }
        });

        speedBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameManager.speedUpGame();
                updateSpeedLabel();
            }
        });

        updateSpeedLabel();
    }

    @Override
    public void addListeners() {
        mapImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                float regionX = ((x - 60f) / MAP_WIDTH) * 1300f;
                float regionY = (y / MAP_HEIGHT) * 580f;

                UIRegion clicked = gameMap.getRegionAt(regionX, regionY);
                if (clicked != null) {
                    selectedRegion = clicked;
                    updateSelectedRegionLabel();
                    if (metricsPanel.isOpen) metricsPanel.loadRegion(clicked);
                    if (educationPanel != null && educationPanel.isOpen) educationPanel.loadRegion(clicked);
                    if (industryPanel != null && industryPanel.isOpen) industryPanel.loadRegion(clicked);
                    if (taxPanel != null && taxPanel.isOpen()) taxPanel.loadRegion(clicked);
                    if (economyPanel != null && economyPanel.isOpen()) economyPanel.loadRegion(clicked);
                    if (budgetPanel != null && budgetPanel.isOpen()) budgetPanel.loadRegion(clicked);
                    if (buildingListPanel != null && buildingListPanel.isOpen()) buildingListPanel.loadRegion(clicked);
                    if (socialMediaPanel != null && socialMediaPanel.isOpen()) socialMediaPanel.refreshData();
                }
            }
        });
    }

    private TextureRegionDrawable makeBarBg() {
        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(new Color(0.04f, 0.1f, 0.2f, 0.85f));
        pix.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pix)));
        pix.dispose();
        return drawable;
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            clearRegionSelection();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gameManager.pauseGame();
            game.setScreen(new PauseScreen(game, this));
            return;
        }

        gameManager.update(delta);
        if (gameManager.isGameOver() && !endStateHandled) {
            endStateHandled = true;
            if (gameManager.getGameResult() == WinLoseManager.GameResult.WIN) {
                game.setScreen(new VictoryScreen(game, this));
            } else if (gameManager.getGameResult() == WinLoseManager.GameResult.LOSE) {
                game.setScreen(new DefeatScreen(game, this));
            }
            return;
        }
        if (dateLabel != null) {
            dateLabel.setText(String.format(
                "%02d/%02d/%04d",
                gameManager.getTimeManager().getCurrentDay(),
                gameManager.getTimeManager().getCurrentMonth(),
                gameManager.getTimeManager().getCurrentYear()
            ));
        }
        if (economyPanel != null && economyPanel.isOpen()) {
            economyPanel.refreshData();
        }
        if (metricsPanel != null && metricsPanel.isOpen()) {
            metricsPanel.refreshData();
        }
        updateSpeedLabel();
        super.render(delta);
    }

    private void clearRegionSelection() {
        selectedRegion = null;
        if (regionalDataPanel != null) {
            regionalDataPanel.hide();
        }
        updateSelectedRegionLabel();
        if (metricsPanel != null && metricsPanel.isOpen()) {
            metricsPanel.loadRegion(null);
        }
        if (economyPanel != null && economyPanel.isOpen()) {
            economyPanel.loadRegion(null);
        }
    }

    private void updateSelectedRegionLabel() {
        if (selectedRegionLabel == null) {
            return;
        }
        boolean hasSelection = selectedRegion != null;
        selectedRegionLabel.setVisible(hasSelection);
        selectedRegionLabel.setText(hasSelection ? selectedRegion.getName().toUpperCase() : "");
    }

    private void updateSpeedLabel() {
        if (speedLabel == null || gameManager == null || gameManager.getTimeManager() == null) {
            return;
        }

        switch (gameManager.getTimeManager().getCurrentSpeed()) {
            case PAUSED:
                speedLabel.setText("PAUSED");
                break;
            case NORMAL:
                speedLabel.setText("1x");
                break;
            case FAST:
                speedLabel.setText("2x");
                break;
            case VERY_FAST:
                speedLabel.setText("4x");
                break;
            default:
                speedLabel.setText("--");
                break;
        }
    }

    public void resumeGame() {
        gameManager.resumeGame();
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public UIRegion getSelectedRegion() {
        return selectedRegion;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public float getNationalStability() {
        if (gameManager == null || gameManager.getCountry() == null) {
            return 0f;
        }
        return (float) gameManager.getCountry().getMetricValue(MetricType.STABILITY);
    }

    public float getCountryMetric(MetricType type) {
        if (type == null || gameManager == null || gameManager.getCountry() == null) {
            return 0f;
        }
        return (float) gameManager.getCountry().getMetricValue(type);
    }

    public float getTreasury() {
        if (gameManager == null || gameManager.getCountry() == null || !(gameManager.getCountry().getEconomy() instanceof CountryEconomy)) {
            return 0f;
        }
        return (float) ((CountryEconomy) gameManager.getCountry().getEconomy()).getTreasury();
    }

    public float getAverageHappiness() {
        return getRegionAverage(RegionValueType.HAPPINESS);
    }

    public float getAverageSecurity() {
        return getRegionAverage(RegionValueType.SECURITY);
    }

    public float getAverageIndustry() {
        return getRegionAverage(RegionValueType.INDUSTRY);
    }

    private float getRegionAverage(RegionValueType valueType) {
        if (gameMap == null || gameMap.getRegions() == null || gameMap.getRegions().size == 0) {
            return 0f;
        }
        float total = 0f;
        int count = 0;
        for (UIRegion region : gameMap.getRegions()) {
            if (region == null) {
                continue;
            }
            switch (valueType) {
                case HAPPINESS:
                    total += region.getHappiness();
                    break;
                case SECURITY:
                    total += region.getSecurity();
                    break;
                case INDUSTRY:
                    total += region.getIndustry();
                    break;
                default:
                    break;
            }
            count++;
        }
        return count == 0 ? 0f : total / count;
    }

    private enum RegionValueType {
        HAPPINESS,
        SECURITY,
        INDUSTRY
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
        if (socialMediaPanel != null) socialMediaPanel.dispose();
        if (popUpEventPanel != null) popUpEventPanel.dispose();
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(stage);
        game.stopMenuMusic();
    }

    @Override
    public Skin getSkin() {
        return localSkin;
    }
}
