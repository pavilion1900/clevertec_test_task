package ru.clevertec.service;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface Service<D, E> {

    D save(E entity);

    List<D> findAll(Pageable pageable);

    D findById(Integer id);

    D update(Integer id, E entity);

    void delete(Integer id);
}
