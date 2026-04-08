package com.nationarchitect.game.modules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class GameMap {
    protected String mapName;
    protected Texture previewTexture;
    protected Texture backgroundTexture;
    protected boolean isBlurred;
    protected Texture lastSnapshot;
    protected Camera mapCamera;
    protected Array<Region> regions;

    public GameMap(String mapName) {
        this.mapName = mapName;
        this.isBlurred = false;
        this.regions = new Array<>();
        loadMapData();
        initRegions();
    }
    private void initRegions() {
    regions.add(new Region(0, "Marmara",             0,   300, 220, 280));
    regions.add(new Region(1, "Aegean",              0,    50, 220, 250));
    regions.add(new Region(2, "Mediterranean",     150,     0, 450, 200));
    regions.add(new Region(3, "Central Anatolia",  220,   150, 380, 280));
    regions.add(new Region(4, "Black Sea",         240, 430, 600, 150));
    regions.add(new Region(5, "Eastern Anatolia",  600, 300, 450, 280));
    regions.add(new Region(6, "Southeastern Anatolia", 550, 50, 350, 180));
}

    public Region getRegionAt(float x, float y) {
        for (Region r : regions) {
            if (r.contains(x, y)) return r;
        }
        return null;
    }

    public Array<Region> getRegions() { return regions; }

    public void prewatch() {
        if (previewTexture == null) {
            String path = "maps/previews/" + mapName.toLowerCase() + "_preview.png";
            if (Gdx.files.internal(path).exists()) {
                previewTexture = new Texture(Gdx.files.internal(path));
            }
        }
    }

    public void loadMapData() {
    if (Gdx.files.internal("map/turkey.png").exists()) {
        backgroundTexture = new Texture(Gdx.files.internal("map/turkey.png"));
    }
}

    public void renderMap(SpriteBatch batch) {
        if (backgroundTexture != null) {
            batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }

    public void setBlur(boolean status)      { this.isBlurred = status; }
    public Texture captureSnapshot()         { return lastSnapshot; }
    public void onRegionClick(int id)        { System.out.println("Region " + id + " clicked"); }
    public void addMapListeners()            {}
    public void saveCurrentState()           { System.out.println("Saving: " + mapName); }
    public String getMapName()               { return mapName; }
    public String getName()                  { return mapName; }
    public Texture getPreviewTexture()       { return previewTexture; }
    public Texture getBackgroundTexture()    { return backgroundTexture; }
}