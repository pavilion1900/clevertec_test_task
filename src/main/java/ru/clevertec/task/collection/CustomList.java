package ru.clevertec.task.collection;

import java.util.stream.Stream;

public interface CustomList<E> extends CustomIterable<E> {
    void setMaxSize(int maxSize);

    void add(E value);

    void add(int index, E value);

    void addAll(CustomList<? extends E> list);

    void addAll(E[] array);

    E set(int index, E newValue);

    E remove(int index);

    void clear();

    int find(E value);

    E get(int index);

    <T> T[] toArray(T[] array);

    int size();

    void trim();

    boolean equals(Object o);

    int hashCode();

    Stream<E> stream();
}