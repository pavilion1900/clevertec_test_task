package ru.clevertec.action;

import ru.clevertec.input.Input;
import ru.clevertec.logic.Logics;
import ru.clevertec.output.Output;
import ru.clevertec.parse.*;
import ru.clevertec.store.Store;
import ru.clevertec.validator.*;

public class MakeOrder implements UserAction {
    private final Output out;

    public MakeOrder(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Make order";
    }

    @Override
    public boolean execute(Input input, Store itemStore, Store cardStore) {
        new ValidateItemWithRegEx().validate();
        new ValidateCardWithRegEx().validate();
        itemStore.loadDataFromFile("src/main/resources/rightItemData.txt");
        cardStore.loadDataFromFile("src/main/resources/rightCardData.txt");
        String order = input.askStr("Enter your order ");
        String[] array = order.split(" ");
        ParseOrder parseOrder = new ParseOrderArray(array, itemStore.getMap(), cardStore.getMap());
        Logics logics = new Logics(parseOrder.getList(), parseOrder.getDiscount(), out);
        logics.printTxt();
        logics.printConsole();
        return true;
    }
}
