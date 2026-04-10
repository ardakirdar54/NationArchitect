package io.github.NationArchitect.controller.savemanager;

/**
 * Lightweight preview returned for save-slot listings.
 */
public class PreviewData {

    private String mapType;
    private double playTime;
    private SaveDateTime saveTimestamp;
    private String snapshotPath;

    public PreviewData() {
    }

    public PreviewData(String mapType, double playTime, SaveDateTime saveTimestamp, String snapshotPath) {
        this.mapType = mapType;
        this.playTime = playTime;
        this.saveTimestamp = saveTimestamp;
        this.snapshotPath = snapshotPath;
    }

    public String getMapType() {
        return mapType;
    }

    public double getPlayTime() {
        return playTime;
    }

    public SaveDateTime getSaveTimestamp() {
        return saveTimestamp;
    }

    public String getSnapshotPath() {
        return snapshotPath;
    }
}
