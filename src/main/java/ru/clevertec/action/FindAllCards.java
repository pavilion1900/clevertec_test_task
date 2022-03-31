package ru.clevertec.action;

import ru.clevertec.input.Input;
import ru.clevertec.model.Card;
import ru.clevertec.output.Output;
import ru.clevertec.store.Store;
import ru.clevertec.task.collection.CustomList;

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
        CustomList<Card> list = cardStore.findAll();
        for (int i = 0; i < list.size(); i++) {
            out.println(list.get(i));
        }
        return true;
    }
}
