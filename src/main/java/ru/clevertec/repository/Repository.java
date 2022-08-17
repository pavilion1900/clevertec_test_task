package ru.clevertec.repository;

import ru.clevertec.task.collection.CustomList;

import java.util.Optional;

public interface Repository<T> {

    T save(T entity);

    CustomList<T> findAll(Integer pageSize, Integer page);

    Optional<T> findById(Integer id);

    T update(T entity);

    boolean delete(Integer id);
}
