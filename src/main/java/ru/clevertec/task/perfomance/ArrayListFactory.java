package ru.clevertec.task.perfomance;

import java.util.*;

public class ArrayListFactory<T> implements Factory {
    @Override
    public List<T> createCollection() {
        return new ArrayList<>();
    }

    @Override
    public String getName() {
        return "ArrayList";
    }
}
