package ru.clevertec.service;

import ru.clevertec.task.collection.CustomList;

public interface Service<T> {

    T save(T value);

    CustomList<T> findAll(String pageSize, String page);

    T findById(Integer id);

    T update(Integer id, T value);

    void delete(Integer id);
}
