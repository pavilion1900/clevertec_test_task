package ru.clevertec.service;

import ru.clevertec.task.collection.CustomList;

public interface Service<D, E> {

    D save(E entity);

    CustomList<D> findAll(String pageSizeStr, String pageStr);

    D findById(Integer id);

    D update(Integer id, E entity);

    void delete(Integer id);
}
