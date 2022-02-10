package ru.clevertec;

import ru.clevertec.actions.*;
import ru.clevertec.input.*;
import ru.clevertec.models.Card;
import ru.clevertec.output.*;
import ru.clevertec.store.*;

import java.util.*;

public class CheckRunner {
    private final Output out;

    public CheckRunner(Output out) {
        this.out = out;
    }

    public void init(Input input, Store itemStore, Store cardStore, List<UserAction> actions) {
        boolean run = true;
        while (run) {
            showMenu(actions);
            int select = input.askInt("Select ");
            if (select < 0 || select >= actions.size()) {
                out.println("Wrong menu number, try again");
                continue;
            }
            UserAction action = actions.get(select);
            run = action.execute(input, itemStore, cardStore);
        }
    }

    private void showMenu(List<UserAction> actions) {
        out.println("Menu:");
        for (int i = 0; i < actions.size(); i++) {
            out.println(i + ". " + actions.get(i).name());
        }
    }

    public static void main(String[] args) {
        Output out = new ConsoleOutput();
        CheckRunner checkRunner = new CheckRunner(out);
        Input input = new ValidateInput(out, new ConsoleInput());
        Store itemStore = new MemItemsStore();
        Store<Card> cardStore = new MemCardsStore();
        List<UserAction> actions = List.of(
                new MakeOrderFixedSettings(out),
                new MakeOrder(out),
                new FindAllItems(out),
                new FindAllCards(out),
                new ExitProgram()
        );
        checkRunner.init(input, itemStore, cardStore, actions);
    }
}