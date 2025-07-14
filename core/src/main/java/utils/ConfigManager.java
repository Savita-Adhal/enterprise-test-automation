package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    
    private static Properties properties;
    private static final String CONFIG_FILE = "config.properties";
    
    static {
        loadProperties();
    }
    
    private static void loadProperties() {
        properties = new Properties();
        try {
            // Try to load from classpath first
            java.io.InputStream is = ConfigManager.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
            if (is != null) {
                properties.load(is);
                is.close();
            } else {
                // Fallback to file system path
                FileInputStream fis = new FileInputStream("src/main/resources/" + CONFIG_FILE);
                properties.load(fis);
                fis.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file: " + e.getMessage());
        }
    }
    
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    public static int getIntProperty(String key) {
        return Integer.parseInt(getProperty(key));
    }
    
    public static int getIntProperty(String key, int defaultValue) {
        try {
            return Integer.parseInt(getProperty(key));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    public static boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(getProperty(key));
    }
    
    // Convenience methods for common properties
    public static String getBaseUrl() {
        return getProperty("app.base.url");
    }
    
    public static String getUsername() {
        return getProperty("test.username");
    }
    
    public static String getPassword() {
        return getProperty("test.password");
    }
    
    public static String getDefaultBrowser() {
        return getProperty("browser.default", "chrome");
    }
} 