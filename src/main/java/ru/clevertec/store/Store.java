package ru.clevertec.store;

import ru.clevertec.task.collection.CustomList;
import java.util.Map;

public interface Store<T> {
    Map<Integer, T> getMap();

    void loadDataFromFile(String path);

    CustomList<T> findAll();
}
