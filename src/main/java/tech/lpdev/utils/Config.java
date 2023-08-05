package tech.lpdev.utils;

import com.sun.jdi.InvalidTypeException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Config {

    private String path;
    private Map<String, Object> values;
    public Config(String path){
        this.path = path;
        this.values = new HashMap<>();
        try {
//            BufferedReader file = new BufferedReader(new InputStreamReader(Config.class.getResourceAsStream(path)));
            BufferedReader file = new BufferedReader(new FileReader(FileUtils.getFileFromResource(path)));
            while (true) {
                String line = file.readLine();
                if (line != null) {
                    String[] parts = new String[2];
                    if (line.startsWith("#") || line.equals("")) continue;
                    if (line.contains(": ")) parts = line.split(": ");
                    else if (line.contains(":")) parts = line.split(":");
                    else {
                        Logger.warning("Invalid config line: \"" + line + "\"");
                    }
                    this.values.put(parts[0], parts[1]);
                } else break;
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading config file " + path);
        }
    }

    public String getAsString(String key) {
        if (values.isEmpty() || !values.containsKey(key)) throw new NoSuchElementException("Option " + key + " not found in " + path);
        try {
            return String.valueOf(values.get(key));
        } catch (Exception e) {
            throw e;
        }
    }

    public int getAsInteger(String key) {
        if (values.isEmpty() || !values.containsKey(key)) throw new NoSuchElementException("Option " + key + " not found in " + path);
        try {
            return Integer.parseInt(String.valueOf(values.get(key)));
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean getAsBoolean(String key) {
        if (values.isEmpty() || !values.containsKey(key)) throw new NoSuchElementException("Option " + key + " not found in " + path);
        try {
            return Boolean.parseBoolean(String.valueOf(values.get(key)));
        } catch (Exception e) {
            throw e;
        }
    }
}
