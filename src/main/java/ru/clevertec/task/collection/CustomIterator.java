package ru.clevertec.task.collection;

public interface CustomIterator<E> {
    boolean hasNext();

    E next();

    void remove();

    void addBefore(E value);

    void addAfter(E value);
}
