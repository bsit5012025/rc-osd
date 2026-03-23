package org.rocs.osd.controller.sms;

import java.io.FileInputStream;
import java.util.Properties;
/**
 * Utility class responsible for loading and
 * providing access to application configuration.
 * <p>
 * This class reads key-value pairs from a
 * config.properties file. It attempts to
 * load hardcoded file path. If WIX installer
 * is implemented, it will load according to the
 * directory on where the .exe was installed.
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
    /*
      Static initializer block that loads the properties file into memory.
      */
    static {
        try {
            String path = "C:\\Users\\Admin\\Desktop\\osd\\rc-osda\\"
                    + "config.properties";
            PROPERTIES.load(new FileInputStream(path));
            System.out.println("Loaded config from absolute path");

        } catch (Exception e) {
            try {

                String localPath = System.getProperty("user.dir")
                        + "\\config.properties";
                PROPERTIES.load(new FileInputStream(localPath));
                System.out.println("Loaded config from app directory");

            } catch (Exception ex) {
                ex.printStackTrace();
                System.err.println("FAILED TO LOAD CONFIG");
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
