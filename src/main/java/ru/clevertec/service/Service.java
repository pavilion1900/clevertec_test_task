package ru.clevertec.service;

import ru.clevertec.input.Input;
import ru.clevertec.model.Card;
import ru.clevertec.model.Item;
import ru.clevertec.output.Output;
import ru.clevertec.store.Store;
import ru.clevertec.task.collection.CustomList;

public interface Service {
    CustomList<Card> findAllCards(Output out, Input in,
                                  Store<Item> itemStore, Store<Card> cardStore);

    CustomList<Item> findAllItems(Output out, Input in,
                                  Store<Item> itemStore, Store<Card> cardStore);

    void makeOrder(Output out, Input in, Store<Item> itemStore, Store<Card> cardStore);

    Service getProxyService();
}
