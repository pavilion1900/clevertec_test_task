package ru.clevertec.task.collection;

public interface CustomIterable<T> {

    CustomIterator<T> getIterator();
}
