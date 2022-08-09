package ru.clevertec.repository;

import ru.clevertec.util.ConnectionManager;
import ru.clevertec.entity.Item;
import ru.clevertec.exception.ItemNotFoundException;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

import java.sql.*;
import java.util.Optional;

public class ItemRepository implements Repository<Item> {

    private static final ItemRepository INSTANCE = new ItemRepository();
    private static final String ADD_ITEM =
            "insert into items (name, price, promotion) values (?, ?, ?)";
    private static final String UPDATE_ITEM =
            "update items set name = ?, price = ?, promotion = ? where id = ?";
    private static final String DELETE_ITEM =
            "delete from items where id = ?";
    private static final String FIND_ALL_ITEMS =
            "select id, name, price, promotion from items limit ? offset ?";
    private static final String FIND_BY_ID =
            "select id, name, price, promotion from items where id = ?";

    private ItemRepository() {
    }

    public static ItemRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public Item add(Item item) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(
                     ADD_ITEM, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            statement.setBigDecimal(2, item.getPrice());
            statement.setBoolean(3, item.isPromotion());
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                item.setId(generatedKeys.getInt("id"));
            }
            return item;
        } catch (SQLException e) {
            throw new ItemNotFoundException("Item not added");
        }
    }

    @Override
    public Item update(Item item) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ITEM)) {
            statement.setString(1, item.getName());
            statement.setBigDecimal(2, item.getPrice());
            statement.setBoolean(3, item.isPromotion());
            statement.setInt(4, item.getId());
            statement.execute();
            return item;
        } catch (SQLException e) {
            throw new ItemNotFoundException("Item not updated");
        }
    }

    @Override
    public boolean delete(Integer id) {
        if (findById(id).isPresent()) {
            try (Connection connection = ConnectionManager.get();
                 PreparedStatement statement = connection.prepareStatement(DELETE_ITEM)) {
                statement.setInt(1, id);
                return statement.executeUpdate() > 0;
            } catch (SQLException e) {
                throw new ItemNotFoundException(
                        String.format("Item with id %d not deleted", id));
            }
        } else {
            return false;
        }
    }

    @Override
    public CustomList<Item> findAll(Integer pageSize, Integer page) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_ITEMS)) {
            statement.setInt(1, pageSize);
            statement.setInt(2, page);
            ResultSet resultSet = statement.executeQuery();
            CustomList<Item> itemList = new CustomArrayList<>();
            while (resultSet.next()) {
                itemList.add(getItem(resultSet));
            }
            return itemList;
        } catch (SQLException e) {
            throw new ItemNotFoundException("Items not founded");
        }
    }

    @Override
    public Optional<Item> findById(Integer id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Item item = null;
            if (resultSet.next()) {
                item = getItem(resultSet);
            }
            return Optional.ofNullable(item);
        } catch (SQLException e) {
            throw new ItemNotFoundException(
                    String.format("Item with id %d not found", id));
        }
    }

    private Item getItem(ResultSet resultSet) throws SQLException {
        return new Item(resultSet.getInt("id"), resultSet.getString("name"),
                resultSet.getBigDecimal("price"), resultSet.getBoolean("promotion"));
    }
}
