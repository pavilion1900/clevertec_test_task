package ru.clevertec.store;

import java.util.*;

public interface Store<T> {
    Map<Integer, T> getMap();

    void loadDataFromFile(String path);

    List<T> findAll();
}
