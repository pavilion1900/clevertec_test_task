package ru.clevertec.store;

import org.junit.jupiter.api.*;
import ru.clevertec.connection.ConnectionManager;
import ru.clevertec.entity.Card;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SqlCardStoreTest {
    private static Connection connection;

    @BeforeAll
    static void init() {
        connection = ConnectionManager.get();
    }

    @AfterAll
    static void closeConnection() throws SQLException {
        connection.close();
    }

    @BeforeEach
    public void wipeTableBefore() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("delete from cards;")) {
            statement.execute();
        }
    }

    @AfterEach
    public void wipeTableAfter() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("delete from cards;")) {
            statement.execute();
        }
    }

    @Test
    void whenAddCard() {
        SqlCardStore storeOfCards = new SqlCardStore(connection);
        Card first = storeOfCards.add(new Card(1234, 5));
        Card second = storeOfCards.add(new Card(1111, 1));
        assertEquals(storeOfCards.findById(first.getId()), first);
    }

    @Test
    void whenUpdateCard() {
        SqlCardStore storeOfCards = new SqlCardStore(connection);
        Card card = storeOfCards.add(new Card(1234, 5));
        Card newCard = storeOfCards.add(new Card(1111, 1));
        storeOfCards.update(card.getId(), newCard);
        newCard.setId(card.getId());
        assertEquals(storeOfCards.findById(card.getId()), newCard);
    }

    @Test
    void whenDeleteCard() {
        SqlCardStore storeOfCards = new SqlCardStore(connection);
        Card card = storeOfCards.add(new Card(1234, 5));
        storeOfCards.delete(card.getId());
        assertNull(storeOfCards.findById(card.getId()));
    }

    @Test
    void whenFindAllCards() {
        SqlCardStore storeOfCards = new SqlCardStore(connection);
        Card first = storeOfCards.add(new Card(1234, 5));
        Card second = storeOfCards.add(new Card(1111, 1));
        CustomList<Card> actualListOfCards = storeOfCards.findAll();
        CustomList<Card> expectedListOfCards = new CustomArrayList<>();
        expectedListOfCards.add(first);
        expectedListOfCards.add(second);
        assertEquals(actualListOfCards, expectedListOfCards);
    }
}
