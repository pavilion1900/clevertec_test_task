package ru.clevertec.service;

import org.junit.jupiter.api.*;
import ru.clevertec.input.Input;
import ru.clevertec.input.StubInput;
import ru.clevertec.model.Card;
import ru.clevertec.model.Item;
import ru.clevertec.output.Output;
import ru.clevertec.output.StubOutput;
import ru.clevertec.store.Store;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;
import ru.clevertec.util.StoreUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductServiceTest {
    private static CustomList<String> inputData;
    private static Store<Item> storeOfItems;
    private static Store<Card> storeOfCards;
    private static Output out;
    private static Input in;
    private Service service;

    @BeforeEach
    void createStore() {
        inputData = new CustomArrayList<>();
        storeOfItems = StoreUtil.createItemStore();
        storeOfCards = StoreUtil.createCardStore();
        service = new ProductService();
        out = new StubOutput();
    }

    @AfterEach
    void deleteStore() {
        inputData = null;
        storeOfItems = null;
        storeOfCards = null;
        service = null;
        out = null;
    }

    @Test
    void whenFindAllCards() {
        CustomList<Card> expected = service.findAllCards(out, in, storeOfItems, storeOfCards);
        CustomList<Card> actual = new CustomArrayList<>();
        actual.add(new Card(1, 9));
        actual.add(new Card(1111, 1));
        actual.add(new Card(1234, 5));
        actual.add(new Card(2222, 2));
        assertEquals(expected, actual);
    }

    @Test
    void whenFindAllItems() {
        CustomList<Item> expected = service.findAllItems(out, in, storeOfItems, storeOfCards);
        CustomList<Item> actual = new CustomArrayList<>();
        actual.add(new Item(26, "Cherry",
                new BigDecimal(3.18).setScale(2, RoundingMode.HALF_UP), false, 0));
        actual.add(new Item(28, "Apple",
                new BigDecimal(1.12).setScale(2, RoundingMode.HALF_UP), false, 0));
        actual.add(new Item(30, "Watermelon",
                new BigDecimal(2.45).setScale(2, RoundingMode.HALF_UP), true, 0));
        actual.add(new Item(38, "Молоко",
                new BigDecimal(2.05).setScale(2, RoundingMode.HALF_UP), true, 0));
        assertEquals(expected, actual);
    }

    @Test
    void whenMakeOrder() {
        inputData.add("3-1 2-5 5-1 card-1234");
        in = new StubInput(inputData);
        service.makeOrder(out, in, storeOfItems, storeOfCards);
        assertTrue(out.toString().lines()
                .anyMatch(str -> str.equals("TAXABLE TOT.                      162.65")));
    }
}