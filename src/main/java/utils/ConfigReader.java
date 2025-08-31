package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("browser.properties")) {
            if (input == null) {
                throw new RuntimeException("browser.properties not found in resources!");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load browser.properties", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }
}