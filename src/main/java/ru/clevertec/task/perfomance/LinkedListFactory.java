package ru.clevertec.task.perfomance;

import java.util.*;

public class LinkedListFactory<T> implements Factory {
    @Override
    public List<T> createCollection() {
        return new LinkedList<>();
    }

    @Override
    public String getName() {
        return "LinkedList";
    }
}
