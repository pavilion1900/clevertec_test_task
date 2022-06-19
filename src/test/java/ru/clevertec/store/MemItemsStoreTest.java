package ru.clevertec.store;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.clevertec.model.Item;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;
import ru.clevertec.util.StoreUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.*;

class MemItemsStoreTest {

    private static Store<Item> storeOfItems = new MemItemsStore();

    @BeforeAll
    static void createStoreOfItems() {
        storeOfItems = StoreUtil.createItemStore();
    }

    @AfterAll
    static void deleteStoreOfItems() {
        storeOfItems = null;
    }

    @Test
    void getMap() {
        Map<Integer, Item> actual = storeOfItems.getMap();
        Map<Integer, Item> expected = Map.ofEntries(
                entry(28, new Item(28, "Apple",
                        new BigDecimal(1.12).setScale(2, RoundingMode.HALF_UP), false)),
                entry(30, new Item(30, "Watermelon",
                        new BigDecimal(2.45).setScale(2, RoundingMode.HALF_UP), true)),
                entry(38, new Item(38, "Молоко",
                        new BigDecimal(2.05).setScale(2, RoundingMode.HALF_UP), true)),
                entry(26, new Item(26, "Cherry",
                        new BigDecimal(3.18).setScale(2, RoundingMode.HALF_UP), false))
        );
        assertEquals(actual, expected);
    }

    @Test
    void findAll() {
        Optional<CustomList<Item>> optionalOfItems = storeOfItems.findAll();
        if (optionalOfItems.isPresent()) {
            CustomList<Item> actual = optionalOfItems.get();
            CustomList<Item> expected = new CustomArrayList<>();
            expected.add(new Item(28, "Apple",
                    new BigDecimal(1.12).setScale(2, RoundingMode.HALF_UP), false));
            expected.add(new Item(30, "Watermelon",
                    new BigDecimal(2.45).setScale(2, RoundingMode.HALF_UP), true));
            expected.add(new Item(38, "Молоко",
                    new BigDecimal(2.05).setScale(2, RoundingMode.HALF_UP), true));
            expected.add(new Item(26, "Cherry",
                    new BigDecimal(3.18).setScale(2, RoundingMode.HALF_UP), false));
            assertEquals(actual, expected);
        }
    }
}