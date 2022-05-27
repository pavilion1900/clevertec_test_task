package ru.clevertec.store;

import ru.clevertec.task.collection.CustomList;
import java.util.Map;
import java.util.Optional;

public interface Store<T> {
    Map<Integer, T> getMap();

    void loadDataFromFile(String path);

    Optional<CustomList<T>> findAll();
}
