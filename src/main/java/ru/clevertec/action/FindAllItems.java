package ru.clevertec.action;

import ru.clevertec.input.Input;
import ru.clevertec.model.Item;
import ru.clevertec.output.Output;
import ru.clevertec.store.Store;
import ru.clevertec.task.collection.CustomList;

import java.util.Comparator;
import java.util.Optional;

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
        Optional<CustomList<Item>> optionalOfItems = itemStore.findAll();
        if (optionalOfItems.isPresent()) {
            optionalOfItems.get().stream()
                    .sorted(Comparator.comparing(Item::getId))
                    .forEach(out::println);
        }
        return true;
    }
}
