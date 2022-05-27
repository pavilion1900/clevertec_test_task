package ru.clevertec.store;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.clevertec.model.Card;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;
import ru.clevertec.util.StoreUtil;

import java.util.Map;
import java.util.Optional;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.*;

class MemCardsStoreTest {
    private static Store<Card> storeOfCards = new MemCardsStore();

    @BeforeAll
    static void createStoreOfCards() {
        storeOfCards = StoreUtil.createCardStore();
    }

    @AfterAll
    static void deleteStoreOfCards() {
        storeOfCards = null;
    }

    @Test
    void getMap() {
        Map<Integer, Card> actual = storeOfCards.getMap();
        Map<Integer, Card> expected = Map.ofEntries(
                entry(1234, new Card(1234, 5)),
                entry(1111, new Card(1111, 1)),
                entry(2222, new Card(2222, 2)),
                entry(0001, new Card(0001, 9))
        );
        assertEquals(actual, expected);
    }

    @Test
    void findAll() {
        Optional<CustomList<Card>> optionalOfCards = storeOfCards.findAll();
        if (optionalOfCards.isPresent()) {
            CustomList<Card> actual = optionalOfCards.get();
            CustomList<Card> expected = new CustomArrayList<>();
            expected.add(new Card(1234, 5));
            expected.add(new Card(1111, 1));
            expected.add(new Card(2222, 2));
            expected.add(new Card(0001, 9));
            assertEquals(actual, expected);
        }
    }
}