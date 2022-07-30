package ru.clevertec.store;

import ru.clevertec.entity.Card;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

import java.sql.*;

public class SqlCardStore implements Store<Card>, AutoCloseable {
    private static final String ADD_CARD =
            "insert into cards (number, discount) values (?, ?)";
    private static final String UPDATE_CARD =
            "update cards set number = ?, discount = ? where id = ?";
    private static final String DELETE_CARD = "delete from cards where id = ?";
    private static final String FIND_ALL_CARDS = "select * from cards";
    private static final String FIND_BY_ID = "select * from cards where id = ?";
    private Connection connection;

    public SqlCardStore() {
    }

    public SqlCardStore(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Card add(Card card) {
        try (PreparedStatement statement = connection.prepareStatement(ADD_CARD,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, card.getNumber());
            statement.setInt(2, card.getDiscount());
            statement.execute();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    card.setId(generatedKeys.getInt(1));
                }
            }
            return card;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(int id, Card card) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CARD)) {
            statement.setInt(1, card.getNumber());
            statement.setInt(2, card.getDiscount());
            statement.setInt(3, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int id) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CARD)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CustomList<Card> findAll() {
        CustomList<Card> cardList = new CustomArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_CARDS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    cardList.add(getCard(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cardList;
    }

    @Override
    public Card findById(int id) {
        Card card = null;
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    card = getCard(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return card;
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    private Card getCard(ResultSet resultSet) {
        Card card = null;
        try {
            card = new Card(resultSet.getInt("id"), resultSet.getInt("number"),
                    resultSet.getInt("discount"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return card;
    }
}
