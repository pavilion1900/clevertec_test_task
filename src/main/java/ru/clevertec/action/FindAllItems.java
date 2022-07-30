package ru.clevertec.action;

import ru.clevertec.input.Input;
import ru.clevertec.entity.Card;
import ru.clevertec.entity.Item;
import ru.clevertec.output.Output;
import ru.clevertec.service.Service;
import ru.clevertec.store.Store;

public class FindAllItems implements UserAction {
    private final Output out;
    private Service service;

    public FindAllItems(Output out, Service service) {
        this.out = out;
        this.service = service;
    }

    @Override
    public String name() {
        return "Find all items";
    }

    @Override
    public boolean execute(Input input, Store<Item> itemStore, Store<Card> cardStore) {
        service = service.getProxyService();
        service.findAllItems(out, input, itemStore, cardStore)
                .stream().forEach(out::println);
        return true;
    }
}
