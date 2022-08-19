package ru.clevertec.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.clevertec.entity.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.*;

class ItemServiceTest {

//    private static final Integer PAGE_SIZE_DEFAULT = 20;
//    private static final Integer PAGE_DEFAULT = 0;
//    @Autowired
//    @Qualifier("itemService")
//    private static Service<Item> itemService;
//
//    @Test
//    void whenAddItem() {
//        Item item = itemService.save(Item.builder()
//                .name("milk")
//                .price(new BigDecimal(10.12).setScale(2, RoundingMode.HALF_UP))
//                .promotion(false)
//                .build());
//        assertThat(itemService.findById(item.getId())).isEqualTo(item);
//    }
//
//    @Test
//    void whenUpdateItem() {
//        Item item = itemService.save(Item.builder()
//                .name("milk")
//                .price(new BigDecimal(10.12).setScale(2, RoundingMode.HALF_UP))
//                .promotion(false)
//                .build());
//        Item itemWithId = itemService.save(item);
//        itemWithId.setName("newMilk");
//        Item updatedItem = itemService.update(itemWithId.getId(), item);
//        assertThat(itemService.findById(item.getId())).isEqualTo(updatedItem);
//    }
//
//    @Test
//    void whenDeleteItem() {
//        Item item = itemService.save(Item.builder()
//                .name("milk")
//                .price(new BigDecimal(10.12).setScale(2, RoundingMode.HALF_UP))
//                .promotion(false)
//                .build());
//        itemService.delete(item.getId());
////        assertNull(itemService.findById(milk.getId()));
//    }

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