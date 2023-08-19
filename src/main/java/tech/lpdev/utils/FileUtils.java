package tech.lpdev.utils;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class FileUtils {


    @Getter
    private static final String jarPath = FileUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("OfficeGenerator-1.0.jar", "");

    public static File getFileFromResource(String fileName) {
        File file;
        if (jarPath.contains("classes")) file = new File(jarPath + fileName);
        else file = new File(jarPath + "OfficeGenerator/" + fileName);
        return file;
    }

    public static void addFolder(String folderName) {
        File file = new File(jarPath + folderName);
        if (!file.exists()) file.mkdir();
    }
}
