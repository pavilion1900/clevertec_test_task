package ru.clevertec.service;

import java.util.List;

public interface Service<D, E> {

    D save(E entity);

    List<D> findAll(String pageSizeStr, String pageStr);

    D findById(Integer id);

    D update(Integer id, E entity);

    void delete(Integer id);
}
