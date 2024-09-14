package com.codezhangborui.velocityUtils;

import org.bspfsystems.yamlconfiguration.file.FileConfiguration;
import org.bspfsystems.yamlconfiguration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.logging.Logger;

import static com.codezhangborui.velocityUtils.Utils.loggerName;

public class Messages {
    private static FileConfiguration messages;
    private static File messagesFile;
    private static Path dataDirectory;
    private static final Logger logger = Logger.getLogger(loggerName);

    public static void init(Path proxyDataDirectory) throws IOException {
        dataDirectory = proxyDataDirectory;
        messagesFile = new File(dataDirectory.toString(), "messages.yml");
        if (!dataDirectory.toFile().exists()) {
            Files.createDirectories(dataDirectory);
        }
        if (!messagesFile.exists()) {
            Files.copy(Configuration.class.getResourceAsStream("/messages.yml"), messagesFile.toPath());
        }
        messages = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public static void reload() {
        if (messagesFile == null) {
            messagesFile = new File(dataDirectory.toString(), "messages.yml");
        }
        messages = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public static void save() {
        try {
            messages.save(messagesFile);
        } catch (IOException e) {
            logger.severe("Could not save messages to " + messagesFile);
            e.printStackTrace();
        }
    }

public static void setDefault(String path, Object value, String... comment) {
    if (!messages.contains(path)) {
        messages.set(path, value);
        if (comment.length > 0) {
            messages.setComments(path, Arrays.asList(comment));
        }
        save();
    }
}

    public static String getString(String path) {
        return messages.getString(path);
    }

    public static String[] getStringList(String path) {
        return messages.getStringList(path).toArray(new String[0]);
    }

    public static void set(String path, Object value) {
        messages.set(path, value);
        save();
    }
}