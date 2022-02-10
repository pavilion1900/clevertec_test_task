package ru.clevertec.actions;

import ru.clevertec.input.Input;
import ru.clevertec.output.Output;
import ru.clevertec.store.Store;

public class FindAllItems implements UserAction {
    private final Output out;

    public FindAllItems(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Find all items";
    }

    @Override
    public boolean execute(Input input, Store itemStore, Store cardStore) {
        itemStore.findAll().forEach(out::println);
        return true;
    }
}
