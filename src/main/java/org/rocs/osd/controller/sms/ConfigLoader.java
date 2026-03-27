package org.rocs.osd.controller.sms;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
/**
 * Utility class responsible for loading and
 * providing access to application configuration.
 * This class reads key-value pairs from a
 * config.properties file. It auto scans
 * where the config.properties file is
 */
public final class ConfigLoader {
    /**
     * Private constructor to prevent instantiation.
     * */
    private ConfigLoader() {

    }
    /**
     * Internal storage for the loaded configuration properties.
     */
    private static final Properties PROPERTIES = new Properties();
    /**
     * The default name of the configuration file to be loaded.
     * */
    private static final String FILE_NAME = "config.properties";
    /*
      Static initializer block that loads the properties file into memory.
      */
    static {
        loadConfig();
    }

    private static void loadConfig() {
        String customPath = System.getProperty("config.path");
        Path path;

        if (customPath != null && !customPath.isEmpty()) {
            path = Paths.get(customPath);
        } else {
            path = Paths.get(System.getProperty("user.dir"), FILE_NAME);
        }

        if (Files.exists(path)) {
            try (InputStream fIs = Files.newInputStream(path)) {
                PROPERTIES.load(fIs);
                System.out.println("Config loaded from: "
                        + path.toAbsolutePath());
            } catch (IOException e) {
                System.err.println("Failed to load config from: "
                        + path.toAbsolutePath());
            }
        }  else {
            System.err.println("Config found at: " + path.toAbsolutePath());
        }
    }
    /**
     * Retrieves a configuration value associated with the specified key.
     *
     * @param key The property key to look up (e.g., "sms.username").
     * @return The value associated with the key,
     * or null if the key is not found.
     */
    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }
}
