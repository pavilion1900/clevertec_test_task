package ru.clevertec.util;

import ru.clevertec.exception.NoSuchPropertiesException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesUtil {

    private static final Properties PROPERTIES = new Properties();

    private PropertiesUtil() {
    }

    static {
        loadProperties();
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void loadProperties() {
        try (InputStream in = PropertiesUtil.class.getClassLoader()
                .getResourceAsStream("app.properties")) {
            PROPERTIES.load(in);
        } catch (IOException e) {
            throw new NoSuchPropertiesException("Properties file not found");
        }
    }
}
