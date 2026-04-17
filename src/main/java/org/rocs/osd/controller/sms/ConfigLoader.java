package org.rocs.osd.controller.sms;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class responsible for loading and
 * providing access to application configuration.
 * This class reads key-value pairs from a
 * config.properties file. It auto scans
 * where the config.properties file is
 */
public final class ConfigLoader {
    /**
     * Logger instance of this class.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(ConfigLoader.class);
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
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info("Config loaded from: {}",
                            path.toAbsolutePath());
                }
            } catch (IOException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error("Failed to load config from: {}",
                            path.toAbsolutePath());
                }
            }
        }  else {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("Config found at: {}",
                        path.toAbsolutePath());
            }
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
