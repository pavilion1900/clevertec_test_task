package ru.clevertec.actions;

import ru.clevertec.logics.*;
import ru.clevertec.input.Input;
import ru.clevertec.models.*;
import ru.clevertec.output.Output;
import ru.clevertec.parse.*;
import ru.clevertec.store.Store;

import java.math.*;
import java.util.Map;

import static java.util.Map.entry;

public class MakeOrderFixedSettings implements UserAction {
    private final Output out;

    public MakeOrderFixedSettings(Output out) {
        this.out = out;
    }

    @Override
    public String name() {
        return "Make order fixed settings";
    }

    @Override
    public boolean execute(Input input, Store itemStore, Store cardStore) {
        String order = input.askStr("Enter your order ");
        String[] array = order.split(" ");
        Map<Integer, Item> mapItems = Map.ofEntries(
                entry(1, new Item(1, "milk",
                        new BigDecimal(10.12).setScale(2, RoundingMode.HALF_UP), false)),
                entry(2, new Item(2, "bread",
                        new BigDecimal(20.58).setScale(2, RoundingMode.HALF_UP), false)),
                entry(3, new Item(3, "sugar",
                        new BigDecimal(50.36).setScale(2, RoundingMode.HALF_UP), true)),
                entry(4, new Item(4, "tomato",
                        new BigDecimal(12.59).setScale(2, RoundingMode.HALF_UP), false)),
                entry(5, new Item(5, "banana",
                        new BigDecimal(17.96).setScale(2, RoundingMode.HALF_UP), true))
        );
        Map<Integer, Card> mapCards = Map.ofEntries(
                entry(1234, new Card(1234, 5)),
                entry(1111, new Card(1111, 1)),
                entry(2222, new Card(2222, 2)),
                entry(3333, new Card(3333, 3))
        );
        ParseOrder parseOrder = new ParseOrderArray(array, mapItems, mapCards);
        new Logics(parseOrder.getList(), parseOrder.getDiscount(), out).printConsoleFixedSettings();
        return true;
    }
}
