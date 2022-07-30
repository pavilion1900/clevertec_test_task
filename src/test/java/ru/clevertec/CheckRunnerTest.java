package ru.clevertec;

import org.junit.jupiter.api.Test;
import ru.clevertec.action.*;
import ru.clevertec.input.*;
import ru.clevertec.entity.Card;
import ru.clevertec.entity.Item;
import ru.clevertec.output.*;
import ru.clevertec.store.*;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckRunnerTest {

    @Test
    public void whenExit() {
        Output out = new StubOutput();
        CustomList<String> list = new CustomArrayList<>();
        list.add("0");
        Input in = new StubInput(list);
        Store<Item> itemStore = new SqlItemStore();
        Store<Card> cardStore = new SqlCardStore();
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
        Store<Item> itemStore = new SqlItemStore();
        Store<Card> cardStore = new SqlCardStore();
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
}