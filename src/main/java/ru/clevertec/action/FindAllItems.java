package ru.clevertec.action;

import ru.clevertec.input.Input;
import ru.clevertec.model.Card;
import ru.clevertec.model.Item;
import ru.clevertec.output.Output;
import ru.clevertec.service.Service;
import ru.clevertec.service.ProductService;
import ru.clevertec.store.Store;

public class FindAllItems implements UserAction {
    private final Output out;
    private Service service = new ProductService();

    public FindAllItems(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Find all items";
    }

    @Override
    public boolean execute(Input input, Store<Item> itemStore, Store<Card> cardStore) {
        service = service.getProxyService();
        service.findAllItems(out, input, itemStore, cardStore);
        return true;
    }
}
