package ru.clevertec.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import ru.clevertec.exception.NoSuchPropertiesException;

import java.io.IOException;
import java.io.InputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PropertiesUtil {

    private static final Yaml YAML = new Yaml(new Constructor(YamlProperties.class));
    private static YamlProperties yamlProperties;

    static {
        loadProperties();
    }

    public static YamlProperties getYamlProperties() {
        return yamlProperties;
    }

    private static void loadProperties() {
        try (InputStream in = PropertiesUtil.class.getClassLoader()
                .getResourceAsStream("application.yaml")) {
            yamlProperties = YAML.load(in);
        } catch (IOException e) {
            throw new NoSuchPropertiesException("Properties file not found");
        }
    }
}
