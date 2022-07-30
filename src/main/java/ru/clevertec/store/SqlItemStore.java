package ru.clevertec.store;

import ru.clevertec.entity.Item;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

import java.sql.*;

public class SqlItemStore implements Store<Item>, AutoCloseable {
    private static final String ADD_ITEM =
            "insert into items (name, price, promotion) values (?, ?, ?)";
    private static final String UPDATE_ITEM =
            "update items set name = ?, price = ?, promotion = ? where id = ?";
    private static final String DELETE_ITEM = "delete from items where id = ?";
    private static final String FIND_ALL_ITEMS = "select * from items";
    private static final String FIND_BY_ID = "select * from items where id = ?";
    private Connection connection;

    public SqlItemStore() {
    }

    public SqlItemStore(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Item add(Item item) {
        try (PreparedStatement statement = connection.prepareStatement(ADD_ITEM,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            statement.setBigDecimal(2, item.getPrice());
            statement.setBoolean(3, item.isPromotion());
            statement.execute();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.setId(generatedKeys.getInt(1));
                }
            }
            return item;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(int id, Item item) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ITEM)) {
            statement.setString(1, item.getName());
            statement.setBigDecimal(2, item.getPrice());
            statement.setBoolean(3, item.isPromotion());
            statement.setInt(4, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int id) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ITEM)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CustomList<Item> findAll() {
        CustomList<Item> itemList = new CustomArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_ITEMS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    itemList.add(getItem(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemList;
    }

    @Override
    public Item findById(int id) {
        Item item = null;
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    item = getItem(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return item;
    }

    private Item getItem(ResultSet resultSet) {
        Item item = null;
        try {
            item = new Item(resultSet.getInt("id"), resultSet.getString("name"),
                    resultSet.getBigDecimal("price"), resultSet.getBoolean("promotion"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return item;
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}
