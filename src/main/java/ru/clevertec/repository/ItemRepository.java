package ru.clevertec.repository;

import ru.clevertec.exception.RepositoryException;
import ru.clevertec.util.ConnectionManager;
import ru.clevertec.entity.Item;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

import java.sql.*;
import java.util.Optional;

@org.springframework.stereotype.Repository
public class ItemRepository implements Repository<Item> {

    private static final String SAVE =
            "insert into items (name, price, promotion) values (?, ?, ?)";
    private static final String UPDATE =
            "update items set name = ?, price = ?, promotion = ? where id = ?";
    private static final String DELETE =
            "delete from items where id = ?";
    private static final String FIND_ALL =
            "select id, name, price, promotion from items limit ? offset ?";
    private static final String FIND_BY_ID =
            "select id, name, price, promotion from items where id = ?";

    @Override
    public Item save(Item item) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(
                     SAVE, PreparedStatement.RETURN_GENERATED_KEYS)) {
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
            throw new RepositoryException("Item not saved");
        }
    }

    @Override
    public Item update(Item item) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, item.getName());
            statement.setBigDecimal(2, item.getPrice());
            statement.setBoolean(3, item.isPromotion());
            statement.setInt(4, item.getId());
            statement.execute();
            return item;
        } catch (SQLException e) {
            throw new RepositoryException("Item not updated");
        }
    }

    @Override
    public boolean delete(Integer id) {
        if (findById(id).isPresent()) {
            try (Connection connection = ConnectionManager.get();
                 PreparedStatement statement = connection.prepareStatement(DELETE)) {
                statement.setInt(1, id);
                return statement.executeUpdate() > 0;
            } catch (SQLException e) {
                throw new RepositoryException(
                        String.format("Item with id %d not deleted", id));
            }
        } else {
            return false;
        }
    }

    @Override
    public CustomList<Item> findAll(Integer pageSize, Integer page) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
            statement.setInt(1, pageSize);
            statement.setInt(2, page);
            ResultSet resultSet = statement.executeQuery();
            CustomList<Item> itemList = new CustomArrayList<>();
            while (resultSet.next()) {
                itemList.add(getItem(resultSet));
            }
            return itemList;
        } catch (SQLException e) {
            throw new RepositoryException("Items not founded");
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
            throw new RepositoryException(
                    String.format("Item with id %d not found", id));
        }
    }

    private Item getItem(ResultSet resultSet) throws SQLException {
        return Item.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .price(resultSet.getBigDecimal("price"))
                .promotion(resultSet.getBoolean("promotion"))
                .build();
    }
}
