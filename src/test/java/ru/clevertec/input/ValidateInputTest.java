package ru.clevertec.input;

import org.junit.jupiter.api.Test;
import ru.clevertec.output.*;
import ru.clevertec.task.collection.CustomArrayList;
import ru.clevertec.task.collection.CustomList;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(selected, 1);
    }

    @Test
    public void whenValidInput() {
        Output out = new StubOutput();
        CustomList<String> list = new CustomArrayList<>();
        list.add("1");
        Input in = new StubInput(list);
        ValidateInput input = new ValidateInput(out, in);
        int selected = input.askInt("Enter menu:");
        assertEquals(selected, 1);
    }

    @Test
    public void checkOutput() {
        Output out = new StubOutput();
        CustomList<String> list = new CustomArrayList<>();
        list.add("one");
        list.add("1");
        Input in = new StubInput(list);
        ValidateInput input = new ValidateInput(out, in);
        input.askInt("Enter menu:");
        assertEquals(out.toString(), String.format(
                "Enter validate data%n"
        ));
    }

    @Test
    public void whenValidInput2() {
        Output out = new StubOutput();
        CustomList<String> list = new CustomArrayList<>();
        list.add("test message");
        Input in = new StubInput(list);
        ValidateInput input = new ValidateInput(out, in);
        String selected = input.askStr("Enter String:");
        assertEquals(selected, "test message");
    }
}