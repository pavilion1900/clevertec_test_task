package ru.clevertec.service;

import ru.clevertec.task.collection.CustomList;

public interface Service<T> {

    T add(T value);

    T update(Integer id, T value);

    void delete(Integer id);

    CustomList<T> findAll(String pageSize, String page);

    T findById(Integer id);
}
