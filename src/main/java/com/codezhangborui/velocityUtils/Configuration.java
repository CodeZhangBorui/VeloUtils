package com.codezhangborui.velocityUtils;

import org.bspfsystems.yamlconfiguration.file.FileConfiguration;
import org.bspfsystems.yamlconfiguration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.codezhangborui.velocityUtils.Utils.loggerName;

public class Configuration {
    private static FileConfiguration config;
    private static File configFile;
    private static Path dataDirectory;
    private static final Logger logger = Logger.getLogger(loggerName);

    public static void init(Path proxyDataDirectory) throws IOException {
        dataDirectory = proxyDataDirectory;
        configFile = new File(dataDirectory.toString(), "config.yml");
        if (!dataDirectory.toFile().exists()) {
            Files.createDirectories(dataDirectory);
        }
        if (!configFile.exists()) {
            Files.copy(Configuration.class.getResourceAsStream("/config.yml"), configFile.toPath());
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public static void reload() {
        if (configFile == null) {
            configFile = new File(dataDirectory.toString(), "config.yml");
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public static void save() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            logger.severe("Could not save config to " + configFile);
            e.printStackTrace();
        }
    }

public static void setDefault(String path, Object value, String... comment) {
    if (!config.contains(path)) {
        config.set(path, value);
        if (comment.length > 0) {
            config.setComments(path, Arrays.asList(comment));
        }
        save();
    }
}

    public static String getString(String path) {
        return config.getString(path);
    }

    public static int getInt(String path) {
        return config.getInt(path);
    }

    public static boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    public static double getDouble(String path) {
        return config.getDouble(path);
    }

    public static String[] getStringList(String path) {
        return config.getStringList(path).toArray(new String[0]);
    }

    public static List<Map<String, Object>> getMapList(String path) {
    List<Map<String, Object>> mapList = new ArrayList<>();
    List<?> list = config.getList(path);
    if (list != null) {
        for (Object item : list) {
            if (item instanceof Map) {
                mapList.add((Map<String, Object>) item);
            }
        }
    }
    return mapList;
}

    public static void set(String path, Object value) {
        config.set(path, value);
        save();
    }
}