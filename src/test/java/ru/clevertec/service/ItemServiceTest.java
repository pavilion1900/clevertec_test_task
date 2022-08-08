package ru.clevertec.service;

import org.junit.jupiter.api.Test;
import ru.clevertec.entity.Item;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class ItemServiceTest {

    private static final Integer PAGE_SIZE_DEFAULT = 20;
    private static final Integer PAGE_DEFAULT = 0;
    private static Service<Item> itemService = ItemService.getInstance();

    @Test
    void whenAddItem() {
        Item milk = itemService.add(new Item("milk",
                new BigDecimal(10.12).setScale(2, RoundingMode.HALF_UP), false));
        assertThat(itemService.findById(milk.getId())).isEqualTo(milk);
    }

    @Test
    void whenUpdateItem() {
        Item item = new Item("milk",
                new BigDecimal(10.12).setScale(2, RoundingMode.HALF_UP), false);
        Item itemWithId = itemService.add(item);
        itemWithId.setName("newMilk");
        Item updatedItem = itemService.update(itemWithId.getId(), item);
        assertThat(itemService.findById(item.getId())).isEqualTo(updatedItem);
    }

    @Test
    void whenDeleteItem() {
        Item milk = itemService.add(new Item("milk",
                new BigDecimal(10.12).setScale(2, RoundingMode.HALF_UP), false));
        itemService.delete(milk.getId());
//        assertNull(itemService.findById(milk.getId()));
    }

//    @Test
//    void whenFindAllItems() {
//        Item milk = itemService.add(new Item("milk",
//                new BigDecimal(10.12).setScale(2, RoundingMode.HALF_UP), false));
//        Item bread = itemService.add(new Item("bread",
//                new BigDecimal(20.58).setScale(2, RoundingMode.HALF_UP), false));
//        CustomList<Item> actualListOfItems = itemService.findAll(
//                String.valueOf(PAGE_SIZE_DEFAULT),
//                String.valueOf(PAGE_DEFAULT));
//        CustomList<Item> expectedListOfItems = new CustomArrayList<>();
//        expectedListOfItems.add(milk);
//        expectedListOfItems.add(bread);
//        assertThat(actualListOfItems).isEqualTo(expectedListOfItems);
//    }
}