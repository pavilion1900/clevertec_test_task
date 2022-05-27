package ru.clevertec.util;

import ru.clevertec.model.Card;
import ru.clevertec.model.Item;
import ru.clevertec.store.MemCardsStore;
import ru.clevertec.store.MemItemsStore;
import ru.clevertec.store.Store;

public class StoreUtil {
    public static Store<Item> createItemStore() {
        Store<Item> itemStore = new MemItemsStore();
        itemStore.loadDataFromFile("src/main/resources/rightItemDataUtil.txt");
        return itemStore;
    }

    public static Store<Card> createCardStore() {
        Store<Card> cardStore = new MemCardsStore();
        cardStore.loadDataFromFile("src/main/resources/rightCardDataUtil.txt");
        return cardStore;
    }
}
