package ru.clevertec.input;

import java.util.List;

public class StubInput implements Input {
    private List<String> list;
    private int position = 0;

    public StubInput(List<String> list) {
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
