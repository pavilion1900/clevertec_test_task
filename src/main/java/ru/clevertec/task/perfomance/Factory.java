package ru.clevertec.task.perfomance;

import java.util.List;

public interface Factory<T> {
    List<T> createCollection();

    String getName();
}
