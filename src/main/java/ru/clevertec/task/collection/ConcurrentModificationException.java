package ru.clevertec.task.collection;

public class ConcurrentModificationException extends RuntimeException {
    public ConcurrentModificationException() {
    }

    public ConcurrentModificationException(String message) {
        super(message);
    }
}
