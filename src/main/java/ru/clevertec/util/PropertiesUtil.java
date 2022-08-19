package ru.clevertec.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.yaml.snakeyaml.Yaml;
import ru.clevertec.exception.NoSuchPropertiesException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PropertiesUtil {

    private static final Yaml YAML = new Yaml();
    private static Map<String, String> map;

    static {
        loadProperties();
    }

    public static String get(String key) {
        return map.get(key);
    }

    private static void loadProperties() {
        try (InputStream in = PropertiesUtil.class.getClassLoader()
                .getResourceAsStream("application.yaml")) {
            map = YAML.load(in);
        } catch (IOException e) {
            throw new NoSuchPropertiesException("Properties file not found");
        }
    }
}
