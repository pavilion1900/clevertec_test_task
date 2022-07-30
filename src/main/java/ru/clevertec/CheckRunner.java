package ru.clevertec;

import ru.clevertec.action.*;
import ru.clevertec.connection.ConnectionManager;
import ru.clevertec.input.*;
import ru.clevertec.entity.Card;
import ru.clevertec.entity.Item;
import ru.clevertec.output.*;
import ru.clevertec.service.ProductService;
import ru.clevertec.service.Service;
import ru.clevertec.store.*;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

public class CheckRunner {
    private final Output out;

    public CheckRunner(Output out) {
        this.out = out;
    }

    public void init(Input input, Store<Item> itemStore, Store<Card> cardStore,
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
        Input inputOrder = new ValidateInput(out, new ConsoleInput());
        String order = inputOrder.askStr("Make order ");
        CustomList<String> steps = new CustomArrayList<>();
        steps.add("0");
        steps.add(order);
        steps.add("1");
        steps.add("2");
        steps.add("3");
        Input input = new StubInput(steps);
        Service service = new ProductService();
        try (SqlItemStore itemStore = new SqlItemStore(ConnectionManager.get());
             SqlCardStore cardStore = new SqlCardStore(ConnectionManager.get())) {
            CustomList<UserAction> actions = new CustomArrayList<>();
            actions.add(new MakeOrder(out));
            actions.add(new FindAllItems(out, service));
            actions.add(new FindAllCards(out, service));
            actions.add(new ExitProgram());
            new CheckRunner(out).init(input, itemStore, cardStore, actions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}