package ru.clevertec;

import ru.clevertec.action.*;
import ru.clevertec.input.*;
import ru.clevertec.model.Card;
import ru.clevertec.model.Item;
import ru.clevertec.output.*;
import ru.clevertec.store.*;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

public class CheckRunner {
    private final Output out;

    public CheckRunner(Output out) {
        this.out = out;
    }

    public void init(Input input, Store itemStore, Store cardStore,
                     CustomList<UserAction> actions) {
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

    private void showMenu(CustomList<UserAction> actions) {
        out.println("Menu:");
        for (int i = 0; i < actions.size(); i++) {
            out.println(i + ". " + actions.get(i).name());
        }
    }

    public static void main(String[] args) {
        Output out = new ConsoleOutput();
        CheckRunner checkRunner = new CheckRunner(out);
        Input input = new ValidateInput(out, new ConsoleInput());
        Store<Item> itemStore = new MemItemsStore();
        Store<Card> cardStore = new MemCardsStore();
        CustomList<UserAction> actions = new CustomArrayList<>();
        actions.add(new MakeOrderFixedSettings(out));
        actions.add(new MakeOrder(out));
        actions.add(new FindAllItems(out));
        actions.add(new FindAllCards(out));
        actions.add(new ExitProgram());
        checkRunner.init(input, itemStore, cardStore, actions);
    }
}