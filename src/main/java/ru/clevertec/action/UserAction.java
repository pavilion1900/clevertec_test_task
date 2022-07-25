package ru.clevertec.action;

import ru.clevertec.input.Input;
import ru.clevertec.model.Card;
import ru.clevertec.model.Item;
import ru.clevertec.store.Store;

public interface UserAction {
    String name();

    boolean execute(Input input, Store<Item> itemStore, Store<Card> cardStore);
}
