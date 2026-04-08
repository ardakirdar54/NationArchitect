package io.github.NationArchitect.controller.savemanager;

import java.io.File;

/**
 * Centralizes local save locations used by the project.
 */
public final class SavePaths {

    public static final String PROJECT_SAVE_DIR = resolveProjectSaveDir();

    private SavePaths() {
    }

    private static String resolveProjectSaveDir() {
        File currentDirectory = new File(System.getProperty("user.dir")).getAbsoluteFile();
        File projectRoot = findProjectRoot(currentDirectory);
        return new File(projectRoot, "saves").getAbsolutePath();
    }

    private static File findProjectRoot(File startDirectory) {
        File cursor = startDirectory;
        while (cursor != null) {
            if (looksLikeProjectRoot(cursor)) {
                return cursor;
            }
            cursor = cursor.getParentFile();
        }
        return startDirectory;
    }

    private static boolean looksLikeProjectRoot(File directory) {
        return new File(directory, "settings.gradle").isFile()
            && new File(directory, "core").isDirectory()
            && new File(directory, "lwjgl3").isDirectory();
    }
}
