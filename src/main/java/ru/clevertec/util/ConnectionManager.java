package ru.clevertec.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.clevertec.exception.NoSuchConnectionException;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConnectionManager {

    private static final int DEFAULT_POOL_SIZE = 10;
    private static BlockingQueue<Connection> pool;
    private static List<Connection> sourceConnections;

    static {
        loadDriver();
        initConnectionPool();
    }

    private static void initConnectionPool() {
        String poolSize = PropertiesUtil.getYamlProperties().getJdbc().getPoolSize();
        int size = poolSize == null ? DEFAULT_POOL_SIZE : Integer.parseInt(poolSize);
        pool = new ArrayBlockingQueue<>(size);
        sourceConnections = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Connection connection = open();
            Connection proxyConnection =
                    (Connection) Proxy.newProxyInstance(ConnectionManager.class.getClassLoader(),
                            new Class[]{Connection.class},
                            (proxy, method, args) -> method.getName().equals("close")
                                    ? pool.add((Connection) proxy)
                                    : method.invoke(connection, args));
            pool.add(proxyConnection);
            sourceConnections.add(connection);
        }
    }

    public static Connection get() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new NoSuchConnectionException("Pool connection is empty");
        }
    }

    private static Connection open() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.getYamlProperties().getJdbc().getUrl(),
                    PropertiesUtil.getYamlProperties().getJdbc().getUsername(),
                    PropertiesUtil.getYamlProperties().getJdbc().getPassword()
            );
        } catch (SQLException e) {
            throw new NoSuchConnectionException("Connection not found");
        }
    }

    private static void loadDriver() {
        try {
            Class.forName(PropertiesUtil.getYamlProperties().getJdbc().getDriver());
        } catch (ClassNotFoundException e) {
            throw new NoSuchConnectionException("Connection driver not found");
        }
    }

    public static void closePool() {
        try {
            for (Connection connection : sourceConnections) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new NoSuchConnectionException("Connection pool not closed");
        }
    }
}
