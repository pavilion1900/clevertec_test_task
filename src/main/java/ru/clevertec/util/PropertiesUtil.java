package ru.clevertec.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.clevertec.exception.NoSuchPropertiesException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PropertiesUtil {

    private static final Properties PROPERTIES = new Properties();

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
