package ru.clevertec.input;

import org.junit.Test;
import ru.clevertec.output.*;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ValidateInputTest {

    @Test
    public void whenInvalidInput() {
        Output out = new StubOutput();
        CustomList<String> list = new CustomArrayList<>();
        list.add("one");
        list.add("1");
        Input in = new StubInput(list);
        ValidateInput input = new ValidateInput(out, in);
        int selected = input.askInt("Enter menu:");
        assertThat(selected, is(1));
    }

    @Test
    public void whenValidInput() {
        Output out = new StubOutput();
        CustomList<String> list = new CustomArrayList<>();
        list.add("1");
        Input in = new StubInput(list);
        ValidateInput input = new ValidateInput(out, in);
        int selected = input.askInt("Enter menu:");
        assertThat(selected, is(1));
    }

    @Test
    public void checkOutput() {
        Output out = new StubOutput();
        CustomList<String> list = new CustomArrayList<>();
        list.add("one");
        list.add("1");
        Input in = new StubInput(list);
        ValidateInput input = new ValidateInput(out, in);
        int selected = input.askInt("Enter menu:");
        assertThat(out.toString(), is(String.format(
                "Enter validate data%n"
        )));
    }
}