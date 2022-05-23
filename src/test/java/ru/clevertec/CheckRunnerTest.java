package ru.clevertec;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import ru.clevertec.action.*;
import ru.clevertec.input.*;
import ru.clevertec.output.*;
import ru.clevertec.store.*;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CheckRunnerTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void whenExit() {
        Output out = new StubOutput();
        CustomList<String> list = new CustomArrayList<>();
        list.add("0");
        Input in = new StubInput(list);
        Store itemStore = new MemItemsStore();
        Store cardStore = new MemCardsStore();
        CustomList<UserAction> actions = new CustomArrayList<>();
        actions.add(new ExitProgram());
        new CheckRunner(out).init(in, itemStore, cardStore, actions);
        String ln = System.lineSeparator();
        assertThat(out.toString(), is(
                "Menu:" + ln
                        + "0. Exit program" + ln
        ));
    }

    @Test
    public void whenInvalidExit() {
        Output out = new StubOutput();
        CustomList<String> list = new CustomArrayList<>();
        list.add("1");
        list.add("0");
        Input in = new StubInput(list);
        Store itemStore = new MemItemsStore();
        Store cardStore = new MemCardsStore();
        CustomList<UserAction> actions = new CustomArrayList<>();
        actions.add(new ExitProgram());
        new CheckRunner(out).init(in, itemStore, cardStore, actions);
        String ln = System.lineSeparator();
        assertThat(out.toString(), is(
                "Menu:" + ln
                        + "0. Exit program" + ln
                        + "Wrong menu number, try again" + ln
                        + "Menu:" + ln
                        + "0. Exit program" + ln
        ));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenOrderIsEmpty() {
        Output out = new StubOutput();
        CustomList<String> list = new CustomArrayList<>();
        list.add("0");
        list.add("");
        list.add("1");
        Input in = new StubInput(list);
        Store itemStore = new MemItemsStore();
        Store cardStore = new MemCardsStore();
        CustomList<UserAction> actions = new CustomArrayList<>();
        actions.add(new MakeOrderFixedSettings(out));
        actions.add(new ExitProgram());
        new CheckRunner(out).init(in, itemStore, cardStore, actions);
        String ln = System.lineSeparator();
        assertThat(out.toString(), is(
                "Menu:" + ln
                        + "0. Make order fixed settings" + ln
        ));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenEnterNotExistItem() {
        Output out = new StubOutput();
        CustomList<String> list = new CustomArrayList<>();
        list.add("0");
        list.add("3-8 2-5 50-7 card-1234");
        list.add("1");
        Input in = new StubInput(list);
        Store itemStore = new MemItemsStore();
        Store cardStore = new MemCardsStore();
        CustomList<UserAction> actions = new CustomArrayList<>();
        actions.add(new MakeOrderFixedSettings(out));
        actions.add(new ExitProgram());
        new CheckRunner(out).init(in, itemStore, cardStore, actions);
        String ln = System.lineSeparator();
        assertThat(out.toString(), is(
                "Menu:" + ln
                        + "0. Make order fixed settings" + ln
        ));
    }
}