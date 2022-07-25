package ru.clevertec.action;

import ru.clevertec.input.Input;
import ru.clevertec.model.Card;
import ru.clevertec.model.Item;
import ru.clevertec.store.Store;

public class ExitProgram implements UserAction {
    @Override
    public String name() {
        return "Exit program";
    }

    @Override
    public boolean execute(Input input, Store<Item> itemStore, Store<Card> cardStore) {
        return false;
    }
}
