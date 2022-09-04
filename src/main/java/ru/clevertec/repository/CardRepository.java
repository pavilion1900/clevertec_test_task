package ru.clevertec.repository;

import ru.clevertec.exception.RepositoryException;
import ru.clevertec.util.ConnectionManager;
import ru.clevertec.entity.Card;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

import java.sql.*;
import java.util.Optional;

@org.springframework.stereotype.Repository
public class CardRepository implements Repository<Card> {

    private static final String SAVE =
            "insert into cards (number, discount) values (?, ?)";
    private static final String UPDATE =
            "update cards set number = ?, discount = ? where id = ?";
    private static final String DELETE =
            "delete from cards where id = ?";
    private static final String FIND_ALL =
            "select id, number, discount from cards limit ? offset ?";
    private static final String FIND_BY_ID =
            "select id, number, discount from cards where id = ?";
    private static final String FIND_BY_NUMBER =
            "select id, number, discount from cards where number = ? limit 1";

    @Override
    public Card save(Card card) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(
                     SAVE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, card.getNumber());
            statement.setInt(2, card.getDiscount());
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                card.setId(generatedKeys.getInt("id"));
            }
            return card;
        } catch (SQLException e) {
            throw new RepositoryException("Card not saved");
        }
    }

    @Override
    public Card update(Card card) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setInt(1, card.getNumber());
            statement.setInt(2, card.getDiscount());
            statement.setInt(3, card.getId());
            statement.execute();
            return card;
        } catch (SQLException e) {
            throw new RepositoryException("Card not updated");
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
                        String.format("Card with id %d not deleted", id));
            }
        } else {
            return false;
        }
    }

    @Override
    public CustomList<Card> findAll(Integer pageSize, Integer page) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
            statement.setInt(1, pageSize);
            statement.setInt(2, page);
            ResultSet resultSet = statement.executeQuery();
            CustomList<Card> cardList = new CustomArrayList<>();
            while (resultSet.next()) {
                cardList.add(getCard(resultSet));
            }
            return cardList;
        } catch (SQLException e) {
            throw new RepositoryException("Cards not founded");
        }
    }

    @Override
    public Optional<Card> findById(Integer id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Card card = null;
            if (resultSet.next()) {
                card = getCard(resultSet);
            }
            return Optional.ofNullable(card);
        } catch (SQLException e) {
            throw new RepositoryException(
                    String.format("Card with id %d not found", id));
        }
    }

    public Optional<Card> findByNumber(Integer number) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_NUMBER)) {
            statement.setInt(1, number);
            ResultSet resultSet = statement.executeQuery();
            Card card = null;
            if (resultSet.next()) {
                card = getCard(resultSet);
            }
            return Optional.ofNullable(card);
        } catch (SQLException e) {
            throw new RepositoryException(
                    String.format("Card with number %d not found", number));
        }
    }

    private Card getCard(ResultSet resultSet) throws SQLException {
        return Card.builder().
                id(resultSet.getInt("id"))
                .number(resultSet.getInt("number"))
                .discount(resultSet.getInt("discount"))
                .build();
    }
}
