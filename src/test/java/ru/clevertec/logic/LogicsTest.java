package ru.clevertec.logic;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.clevertec.model.Card;
import ru.clevertec.model.Item;
import ru.clevertec.output.Output;
import ru.clevertec.output.StubOutput;
import ru.clevertec.parse.ParseOrderArray;
import ru.clevertec.store.MemCardsStore;
import ru.clevertec.store.MemItemsStore;
import ru.clevertec.store.Store;
import ru.clevertec.task.collection.CustomList;
import ru.clevertec.util.StoreUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LogicsTest {

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
    void getValueWithoutPromotion() {
        String[] args = {"28-1", "30-5", "38-1", "card-1234"};
        Map<Integer, Item> mapItems = storeOfItems.getMap();
        Map<Integer, Card> mapCars = storeOfCards.getMap();
        ParseOrderArray orderArray = new ParseOrderArray(args, mapItems, mapCars);
        CustomList<Item> listOfItems = orderArray.getList();
        int discount = orderArray.getDiscount();
        Output output = new StubOutput();
        Logics logics = new Logics(listOfItems, discount, output);
        assertEquals(logics.getValue(), new BigDecimal(14.66).setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void getValueWitPromotion() {
        String[] args = {"28-1", "30-8", "38-7", "card-1234"};
        Map<Integer, Item> mapItems = storeOfItems.getMap();
        Map<Integer, Card> mapCars = storeOfCards.getMap();
        ParseOrderArray orderArray = new ParseOrderArray(args, mapItems, mapCars);
        CustomList<Item> listOfItems = orderArray.getList();
        int discount = orderArray.getDiscount();
        Output output = new StubOutput();
        Logics logics = new Logics(listOfItems, discount, output);
        assertEquals(logics.getValue(), new BigDecimal(31.69).setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void printConsoleFixedSettings() {
        String[] args = {"28-1", "30-8", "38-7", "card-1234"};
        Map<Integer, Item> mapItems = storeOfItems.getMap();
        Map<Integer, Card> mapCars = storeOfCards.getMap();
        ParseOrderArray orderArray = new ParseOrderArray(args, mapItems, mapCars);
        CustomList<Item> listOfItems = orderArray.getList();
        int discount = orderArray.getDiscount();
        Output out = new StubOutput();
        Logics logics = new Logics(listOfItems, discount, out);
        logics.printConsoleFixedSettings();
        assertTrue(out.toString().lines()
                .anyMatch(str -> str.equals("TAXABLE TOT.                       31.69")));
    }
}