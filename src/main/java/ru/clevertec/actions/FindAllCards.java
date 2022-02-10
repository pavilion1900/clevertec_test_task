package ru.clevertec.actions;

import ru.clevertec.input.Input;
import ru.clevertec.output.Output;
import ru.clevertec.store.Store;

public class FindAllCards implements UserAction {
    private final Output out;

    public FindAllCards(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Find all cards";
    }

    @Override
    public boolean execute(Input input, Store itemStore, Store cardStore) {
        cardStore.findAll().forEach(out::println);
        return true;
    }
}
