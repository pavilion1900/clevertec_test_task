package ru.clevertec.store;

import ru.clevertec.task.collection.CustomList;

public interface Store<T> {
    T add(T value);

    boolean update(int id, T value);

    boolean delete(int id);

    CustomList<T> findAll();

    T findById(int id);
}
