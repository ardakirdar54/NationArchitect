package io.github.NationArchitect.modules;

public enum MapModes {
    DEFAULT(   "MAP MODE",   false, "map modes/map modes.jpeg",                        null),
    TERRAIN(   "TERRAIN",    false, "map modes/mapmodeterrain.png",      "map modes/terrain_map.jpeg"),
    SECURITY(  "SECURITY",   true,  "map modes/Security.png",     null),
    INDUSTRY(  "INDUSTRY",   true,  "map modes/industry.png",     null),
    POPULATION("POPULATION", true,  "map modes/mapmodepopulation.png",   null),
    EDUCATION( "EDUCATION",  true,  "map modes/mapmodeeducation.png",    null),
    HAPPINESS( "HAPPINESS",  true,  "map modes/happinesmapmode.png",    null);

    private final String label;
    private final boolean isDynamic;
    private final String iconPath;
    private final String texturePath;

    MapModes(String label, boolean isDynamic, String iconPath, String texturePath) {
        this.label       = label;
        this.isDynamic   = isDynamic;
        this.iconPath    = iconPath;
        this.texturePath = texturePath;
    }

    public String getLabel()       { return label; }
    public boolean isDynamic()     { return isDynamic; }
    public String getIconPath()    { return iconPath; }
    public String getTexturePath() { return texturePath; }
}
