package ru.clevertec.action;

import ru.clevertec.input.Input;
import ru.clevertec.output.Output;
import ru.clevertec.service.Manager;
import ru.clevertec.service.ProductManager;
import ru.clevertec.store.Store;

public class MakeOrder implements UserAction {
    private final Output out;
    private Manager manager = new ProductManager();

    public MakeOrder(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Make order";
    }

    @Override
    public boolean execute(Input input, Store itemStore, Store cardStore) {
        manager.makeOrder(out, input, itemStore, cardStore);
        return true;
    }
}
