package ru.clevertec.service;

import ru.clevertec.input.Input;
import ru.clevertec.model.Card;
import ru.clevertec.model.Item;
import ru.clevertec.output.Output;
import ru.clevertec.store.Store;
import ru.clevertec.task.collection.CustomList;

public interface Manager {
    CustomList<Card> findAllCards(Output out, Input in, Store itemStore, Store cardStore);

    CustomList<Item> findAllItems(Output out, Input in, Store itemStore, Store cardStore);

    void makeOrder(Output out, Input in, Store itemStore, Store cardStore);

    Manager getProxyManger();
}
