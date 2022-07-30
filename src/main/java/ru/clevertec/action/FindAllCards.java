package ru.clevertec.action;

import ru.clevertec.input.Input;
import ru.clevertec.entity.Card;
import ru.clevertec.entity.Item;
import ru.clevertec.output.Output;
import ru.clevertec.service.Service;
import ru.clevertec.store.Store;

public class FindAllCards implements UserAction {
    private final Output out;
    private Service service;

    public FindAllCards(Output out, Service service) {
        this.out = out;
        this.service = service;
    }

    @Override
    public String name() {
        return "Find all cards";
    }

    @Override
    public boolean execute(Input input, Store<Item> itemStore, Store<Card> cardStore) {
        service = service.getProxyService();
        service.findAllCards(out, input, itemStore, cardStore)
                .stream().forEach(out::println);
        return true;
    }
}
