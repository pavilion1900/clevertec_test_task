package ru.clevertec.actions;

import ru.clevertec.input.Input;
import ru.clevertec.store.Store;

public interface UserAction {
    String name();

    boolean execute(Input input, Store itemStore, Store cardStore);
}
