package ru.clevertec;

import org.junit.jupiter.api.Test;
import ru.clevertec.action.*;
import ru.clevertec.input.*;
import ru.clevertec.model.Card;
import ru.clevertec.model.Item;
import ru.clevertec.output.*;
import ru.clevertec.store.*;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CheckRunnerTest {

    @Test
    public void whenExit() {
        Output out = new StubOutput();
        CustomList<String> list = new CustomArrayList<>();
        list.add("0");
        Input in = new StubInput(list);
        Store<Item> itemStore = new MemItemsStore();
        Store<Card> cardStore = new MemCardsStore();
        CustomList<UserAction> actions = new CustomArrayList<>();
        actions.add(new ExitProgram());
        new CheckRunner(out).init(in, itemStore, cardStore, actions);
        String ln = System.lineSeparator();
        assertEquals(out.toString(),
                "Menu:" + ln
                        + "0. Exit program" + ln
        );
    }

    @Test
    public void whenInvalidExit() {
        Output out = new StubOutput();
        CustomList<String> list = new CustomArrayList<>();
        list.add("1");
        list.add("0");
        Input in = new StubInput(list);
        Store<Item> itemStore = new MemItemsStore();
        Store<Card> cardStore = new MemCardsStore();
        CustomList<UserAction> actions = new CustomArrayList<>();
        actions.add(new ExitProgram());
        new CheckRunner(out).init(in, itemStore, cardStore, actions);
        String ln = System.lineSeparator();
        assertEquals(out.toString(),
                "Menu:" + ln
                        + "0. Exit program" + ln
                        + "Wrong menu number, try again" + ln
                        + "Menu:" + ln
                        + "0. Exit program" + ln
        );
    }

    @Test
    public void whenOrderIsEmpty() {
        Output out = new StubOutput();
        CustomList<String> list = new CustomArrayList<>();
        list.add("0");
        list.add("");
        list.add("1");
        Input in = new StubInput(list);
        Store<Item> itemStore = new MemItemsStore();
        Store<Card> cardStore = new MemCardsStore();
        CustomList<UserAction> actions = new CustomArrayList<>();
        actions.add(new MakeOrderFixedSettings(out));
        actions.add(new ExitProgram());
        assertThrows(IllegalArgumentException.class,
                () -> new CheckRunner(out).init(in, itemStore, cardStore, actions));
    }

    @Test
    public void whenEnterNotExistItem() {
        Output out = new StubOutput();
        CustomList<String> list = new CustomArrayList<>();
        list.add("0");
        list.add("3-8 2-5 50-7 card-1234");
        list.add("1");
        Input in = new StubInput(list);
        Store<Item> itemStore = new MemItemsStore();
        Store<Card> cardStore = new MemCardsStore();
        CustomList<UserAction> actions = new CustomArrayList<>();
        actions.add(new MakeOrderFixedSettings(out));
        actions.add(new ExitProgram());
        assertThrows(IllegalArgumentException.class,
                () -> new CheckRunner(out).init(in, itemStore, cardStore, actions));
    }
}