package ru.clevertec.repository;

import ru.clevertec.task.collection.CustomList;

import java.util.Optional;

public interface Repository<T> {

    T add(T value);

    T update(T value);

    boolean delete(Integer id);

    CustomList<T> findAll(Integer pageSize, Integer page);

    Optional<T> findById(Integer id);
}
