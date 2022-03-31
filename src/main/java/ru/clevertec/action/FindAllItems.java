package ru.clevertec.action;

import ru.clevertec.input.Input;
import ru.clevertec.model.Item;
import ru.clevertec.output.Output;
import ru.clevertec.store.Store;
import ru.clevertec.task.collection.CustomList;

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
        CustomList<Item> list = itemStore.findAll();
        for (int i = 0; i < list.size(); i++) {
            out.println(list.get(i));
        }
        return true;
    }
}
