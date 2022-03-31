package ru.clevertec.input;

import ru.clevertec.task.collection.CustomList;

public class StubInput implements Input {
    private CustomList<String> list;
    private int position = 0;

    public StubInput(CustomList<String> list) {
        this.list = list;
    }

    @Override
    public String askStr(String question) {
        return list.get(position++);
    }

    @Override
    public int askInt(String question) {
        return Integer.parseInt(askStr(question));
    }
}
