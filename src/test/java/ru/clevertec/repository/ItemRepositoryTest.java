package ru.clevertec.repository;

import org.junit.jupiter.api.*;
import ru.clevertec.entity.Item;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

public class ItemRepositoryTest {

    private static final Integer PAGE_SIZE_DEFAULT = 20;
    private static final Integer PAGE_DEFAULT = 0;
    private static ItemRepository itemRepository = ItemRepository.getInstance();

    @Test
    void whenAddItem() {
        Item milk = itemRepository.add(new Item("milk",
                new BigDecimal(10.12).setScale(2, RoundingMode.HALF_UP), false));
        assertThat(itemRepository.findById(milk.getId()).get()).isEqualTo(milk);
    }

    @Test
    void whenUpdateItem() {
        Item milk = itemRepository.add(new Item("milk",
                new BigDecimal(10.12).setScale(2, RoundingMode.HALF_UP), false));
        milk.setName("newMilk");
        Item updatedItem = itemRepository.update(milk);
        assertThat(itemRepository.findById(milk.getId()).get()).isEqualTo(updatedItem);
    }

    @Test
    void whenDeleteItem() {
        Item milk = itemRepository.add(new Item("milk",
                new BigDecimal(10.12).setScale(2, RoundingMode.HALF_UP), false));
        assertTrue(itemRepository.delete(milk.getId()));
    }

//    @Test
//    void whenFindAllItems() {
//        Item milk = itemRepository.add(new Item("milk",
//                new BigDecimal(10.12).setScale(2, RoundingMode.HALF_UP), false));
//        Item bread = itemRepository.add(new Item("bread",
//                new BigDecimal(20.58).setScale(2, RoundingMode.HALF_UP), false));
//        CustomList<Item> actualListOfItems = new CustomArrayList<>();
//        CustomList<Item> expectedListOfItems = new CustomArrayList<>();
//        expectedListOfItems.add(milk);
//        expectedListOfItems.add(bread);
//        assertThat(actualListOfItems).isEqualTo(expectedListOfItems);
//    }
}
