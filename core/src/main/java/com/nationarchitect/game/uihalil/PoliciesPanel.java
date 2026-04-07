package com.nationarchitect.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.nationarchitect.game.screens.BaseScreen;

import java.util.HashMap;

public class PoliciesPanel extends UIPanel {

    private Table infoTooltip;
    private HashMap<String, Boolean> policyStates;
    private Texture frameTex;
    private TextureRegionDrawable cellBg;

    public PoliciesPanel(BaseScreen rootScreen) {
        super(rootScreen, "Policies");
        policyStates = new HashMap<>();
        policyStates.put("Free Transportation", false);
        policyStates.put("Government Transparency", false);
        policyStates.put("Freedom of the Press", false);
        policyStates.put("Labor Unions", false);
        
        cellBg = makeDarkBg(0.02f, 0.08f, 0.15f, 0.6f);
    }

    @Override
    public void buildLayout() {
        Skin skin = rootScreen.getSkin();
        this.clear();

        // 1. Arka Plan Çerçevesi
        if (Gdx.files.internal("backgrounds/pause frame.png").exists()) {
            frameTex = new Texture(Gdx.files.internal("backgrounds/pause frame.png"));
            this.setBackground(new TextureRegionDrawable(new TextureRegion(frameTex)));
        }

        Table content = new Table();
        content.top().pad(30); 

        // --- BAŞLIK BÖLÜMÜ ---
        Table header = new Table();
        Label titleLbl = new Label("POLICIES", skin);
        titleLbl.setFontScale(1.6f); 
        titleLbl.setColor(new Color(0x00E5FFFF));

        AnimatedMenuButton closeBtn = new AnimatedMenuButton("X", skin);
        closeBtn.addListener(new ClickListener() {
            @Override public void clicked(InputEvent e, float x, float y) { hide(); }
        });

        header.add(titleLbl).expandX().left();
        header.add(closeBtn).size(45); 
        content.add(header).fillX().padBottom(35).row(); 

        // --- POLİTİKALAR LİSTESİ ---
        // YAPI: addPolicyRow(parent, isim, ikon_yolu, etki_listesi, skin)
        
        /* İkonları şu klasörlere ekle:
           1. Politika İkonları (Yazılı olanlar): assets/icons/policies/ klasörüne
           2. Tooltip İkonları (Artı/Eksi): assets/icons/ui/ klasörüne (plus_icon.png ve minus_icon.png olarak)
        */

        addPolicyRow(content, "Free Transportation", "icons/freetransportation.png", 
            new String[][]{
                {"POSITIVE", "Public Happiness (+15%)"},
                {"POSITIVE", "Economy Movement (+5%)"},
                {"NEGATIVE", "Monthly Budget (-2000$)"}
            }, skin);
            
        addPolicyRow(content, "Government Transparency", "icons/governmenttransparency.png", 
            new String[][]{
                {"POSITIVE", "Corruption (-25%)"},
                {"NEGATIVE", "State Security (-10%)"}
            }, skin);
            
        addPolicyRow(content, "Freedom of the Press", "icons/freedomofthepress.png", 
            new String[][]{
                {"POSITIVE", "Education Level (+10%)"},
                {"NEGATIVE", "Government Control (-20%)"}
            }, skin);
            
        addPolicyRow(content, "Labor Unions", "icons/laborunions.png", 
            new String[][]{
                {"POSITIVE", "Worker Happiness (+20%)"},
                {"NEGATIVE", "Industrial Production (-10%)"}
            }, skin);

        this.add(content).fill().expand();

        // --- TOOLTIP PANELİ (İkonlu İçerik İçin Hazır) ---
        infoTooltip = new Table();
        infoTooltip.setBackground(makeDarkBg(0.0f, 0.02f, 0.05f, 0.95f)); 
        infoTooltip.pad(25); 
        infoTooltip.setVisible(false);
        
        this.addActor(infoTooltip);
    }

    private void addPolicyRow(Table parent, final String name, String iconPath, final String[][] effectData, final Skin skin) {
        Table row = new Table();
        row.setBackground(cellBg);
        row.pad(15); 

        Table iconCell = new Table();
        if (Gdx.files.internal(iconPath).exists()) {
            Image icon = new Image(new Texture(Gdx.files.internal(iconPath)));
            iconCell.add(icon).size(280, 70); 
        } else {
            iconCell.add().size(280, 70); // Path hatalıysa hizayı bozma
        }
        row.add(iconCell).expandX().left().padRight(30);

        final TextButton actionBtn = new TextButton(policyStates.get(name) ? "Cancel" : "Apply", skin);
        updateButtonStyle(actionBtn, policyStates.get(name));
        
        actionBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boolean active = !policyStates.get(name);
                policyStates.put(name, active);
                actionBtn.setText(active ? "Cancel" : "Apply");
                updateButtonStyle(actionBtn, active);
                applyPolicyEffects(name, active);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                showIconicTooltip(effectData, event.getStageX(), event.getStageY(), skin);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                infoTooltip.setVisible(false);
            }
        });

        row.add(actionBtn).size(130, 50).right(); 
        parent.add(row).fillX().padBottom(15).row(); 
    }

    private void showIconicTooltip(String[][] effectData, float stageX, float stageY, Skin skin) {
        infoTooltip.clear(); 
        
        for (String[] effect : effectData) {
            String type = effect[0]; 
            String desc = effect[1]; 
            
            // BURASI ÖNEMLİ: İkonları bu isimlerle assets/icons/ui altına at
            String iconPath = type.equals("POSITIVE") ? "icons/plus sign.png" : "icons/minus sign.png";
            Color textColor = type.equals("POSITIVE") ? Color.GREEN : Color.RED;

            Table effectRow = new Table();
            if (Gdx.files.internal(iconPath).exists()) {
                Image icon = new Image(new Texture(Gdx.files.internal(iconPath)));
                effectRow.add(icon).size(28, 28).padRight(12); // İkon boyutu büyütüldü
            }
            
            Label lbl = new Label(desc, skin);
            lbl.setFontScale(1.2f); // Yazı boyutu büyütüldü
            lbl.setColor(textColor);
            effectRow.add(lbl).left();
            
            infoTooltip.add(effectRow).left().padBottom(8).row();
        }

        infoTooltip.pack();
        
        float localX = stageX - this.getX() + 45f;
        float localY = stageY - this.getY() - 30f;
        
        if (localX + infoTooltip.getWidth() > this.getWidth()) {
            localX = stageX - this.getX() - infoTooltip.getWidth() - 45f; 
        }
        
        infoTooltip.setPosition(localX, localY);
        infoTooltip.setVisible(true);
        infoTooltip.toFront(); 
    }

    private void updateButtonStyle(TextButton btn, boolean isActive) {
        if (isActive) {
            btn.setColor(Color.RED); 
        } else {
            btn.setColor(Color.GREEN); 
        }
    }

    private void applyPolicyEffects(String name, boolean active) {
        Gdx.app.log("Policy", name + " is now " + (active ? "Active" : "Inactive"));
    }

    private TextureRegionDrawable makeDarkBg(float r, float g, float b, float a) {
        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(r, g, b, a);
        pix.fill();
        TextureRegionDrawable d = new TextureRegionDrawable(new TextureRegion(new Texture(pix)));
        pix.dispose();
        return d;
    }

    @Override
    public void refreshData() { }

    @Override
    public void dispose() { 
        if (frameTex != null) frameTex.dispose(); 
    }
}