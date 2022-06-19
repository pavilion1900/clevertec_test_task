package ru.clevertec.parse;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.clevertec.model.Card;
import ru.clevertec.model.Item;
import ru.clevertec.store.MemCardsStore;
import ru.clevertec.store.MemItemsStore;
import ru.clevertec.store.Store;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;
import ru.clevertec.util.StoreUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ParseOrderArrayTest {

    private static Store<Item> storeOfItems = new MemItemsStore();
    private static Store<Card> storeOfCards = new MemCardsStore();

    @BeforeAll
    static void createStoreOfItemsAndCards() {
        storeOfItems = StoreUtil.createItemStore();
        storeOfCards = StoreUtil.createCardStore();
    }

    @AfterAll
    static void deleteStoreOfItemsAndCards() {
        storeOfItems = null;
        storeOfCards = null;
    }

    @Test
    void getListWithCorrectData() {
        String[] args = {"28-1", "30-5", "38-1", "card-1234"};
        Map<Integer, Item> mapItems = storeOfItems.getMap();
        Map<Integer, Card> mapCars = storeOfCards.getMap();
        ParseOrderArray orderArray = new ParseOrderArray(args, mapItems, mapCars);
        CustomList<Item> actual = orderArray.getList();
        CustomList<Item> expected = new CustomArrayList<>();
        expected.add(new Item(28, "Apple",
                new BigDecimal(1.12).setScale(2, RoundingMode.HALF_UP), false, 1));
        expected.add(new Item(30, "Watermelon",
                new BigDecimal(2.45).setScale(2, RoundingMode.HALF_UP), true, 5));
        expected.add(new Item(38, "Молоко",
                new BigDecimal(2.05).setScale(2, RoundingMode.HALF_UP), true, 1));
        assertEquals(actual, expected);
    }

    @Test
    void getListWithEmptyOrder() {
        String[] args = {};
        Map<Integer, Item> mapItems = storeOfItems.getMap();
        Map<Integer, Card> mapCars = storeOfCards.getMap();
        ParseOrderArray orderArray = new ParseOrderArray(args, mapItems, mapCars);
        assertThrows(IllegalArgumentException.class, () -> orderArray.getList());
    }

    @Test
    void getListWithNotCorrectData() {
        String[] args = {"28", "30-5", "38-1", "card-1234"};
        Map<Integer, Item> mapItems = storeOfItems.getMap();
        Map<Integer, Card> mapCars = storeOfCards.getMap();
        ParseOrderArray orderArray = new ParseOrderArray(args, mapItems, mapCars);
        assertThrows(IllegalArgumentException.class, () -> orderArray.getList());
    }

    @Test
    void getDiscountWithCorrectData() {
        String[] args = {"28-1", "30-5", "38-1", "card-1234"};
        Map<Integer, Item> mapItems = storeOfItems.getMap();
        Map<Integer, Card> mapCars = storeOfCards.getMap();
        ParseOrderArray orderArray = new ParseOrderArray(args, mapItems, mapCars);
        CustomList<Item> listOfItems = orderArray.getList();
        assertEquals(orderArray.getDiscount(), 5);
    }
}