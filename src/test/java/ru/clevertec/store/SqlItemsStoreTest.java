package ru.clevertec.store;

import org.junit.jupiter.api.*;
import ru.clevertec.connection.ConnectionManager;
import ru.clevertec.entity.Item;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class SqlItemsStoreTest {
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
        try (PreparedStatement statement = connection.prepareStatement("delete from items;")) {
            statement.execute();
        }
    }

    @AfterEach
    public void wipeTableAfter() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("delete from items;")) {
            statement.execute();
        }
    }

    @Test
    void whenAddItem() {
        SqlItemStore storeOfItems = new SqlItemStore(connection);
        Item milk = storeOfItems.add(new Item("milk",
                new BigDecimal(10.12).setScale(2, RoundingMode.HALF_UP), false));
        Item bread = storeOfItems.add(new Item("bread",
                new BigDecimal(20.58).setScale(2, RoundingMode.HALF_UP), false));
        assertEquals(storeOfItems.findById(milk.getId()), milk);
    }

    @Test
    void whenUpdateItem() {
        SqlItemStore storeOfItems = new SqlItemStore(connection);
        Item milk = storeOfItems.add(new Item("milk",
                new BigDecimal(10.12).setScale(2, RoundingMode.HALF_UP), false));
        Item newMilk = new Item("newMilk",
                new BigDecimal(10.12).setScale(2, RoundingMode.HALF_UP), false);
        storeOfItems.update(milk.getId(), newMilk);
        newMilk.setId(milk.getId());
        assertEquals(storeOfItems.findById(milk.getId()), newMilk);
    }

    @Test
    void whenDeleteItem() {
        SqlItemStore storeOfItems = new SqlItemStore(connection);
        Item milk = storeOfItems.add(new Item("milk",
                new BigDecimal(10.12).setScale(2, RoundingMode.HALF_UP), false));
        storeOfItems.delete(milk.getId());
        assertNull(storeOfItems.findById(milk.getId()));
    }

    @Test
    void whenFindAllItems() {
        SqlItemStore storeOfItems = new SqlItemStore(connection);
        Item milk = storeOfItems.add(new Item("milk",
                new BigDecimal(10.12).setScale(2, RoundingMode.HALF_UP), false));
        Item bread = storeOfItems.add(new Item("bread",
                new BigDecimal(20.58).setScale(2, RoundingMode.HALF_UP), false));
        CustomList<Item> actualListOfItems = storeOfItems.findAll();
        CustomList<Item> expectedListOfItems = new CustomArrayList<>();
        expectedListOfItems.add(milk);
        expectedListOfItems.add(bread);
        assertEquals(actualListOfItems, expectedListOfItems);
    }
}
