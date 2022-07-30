package ru.clevertec.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class ConnectionManager {
    private static final Properties PROPERTIES;
    private static final String PROPERTIES_FILE = "app.properties";
    private static final String DRIVER = "jdbc.driver";
    private static final String URL = "jdbc.url";
    private static final String USER_NAME = "jdbc.username";
    private static final String PASSWORD = "jdbc.password";

    private ConnectionManager() {
    }

    static {
        PROPERTIES = new Properties();
        try {
            InputStream in = ConnectionManager.class.getClassLoader()
                    .getResourceAsStream(PROPERTIES_FILE);
            if (in == null) {
                throw new RuntimeException("Properties file not found");
            }
            PROPERTIES.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection get() {
        try {
            Class.forName(PROPERTIES.getProperty(DRIVER));
            return DriverManager.getConnection(
                    PROPERTIES.getProperty(URL),
                    PROPERTIES.getProperty(USER_NAME),
                    PROPERTIES.getProperty(PASSWORD)
            );
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
