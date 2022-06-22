package ru.clevertec.action;

import ru.clevertec.input.Input;
import ru.clevertec.output.Output;
import ru.clevertec.service.Manager;
import ru.clevertec.service.ProductManager;
import ru.clevertec.store.Store;

public class FindAllItems implements UserAction {
    private final Output out;
    private Manager manager = new ProductManager();

    public FindAllItems(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Find all items";
    }

    @Override
    public boolean execute(Input input, Store itemStore, Store cardStore) {
        manager = manager.getProxyManger();
        manager.findAllItems(out, input, itemStore, cardStore);
        return true;
    }
}
