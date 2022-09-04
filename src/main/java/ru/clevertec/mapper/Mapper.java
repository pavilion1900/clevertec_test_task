package ru.clevertec.mapper;

public interface Mapper<D, E> {

    D map(E entity);
}
