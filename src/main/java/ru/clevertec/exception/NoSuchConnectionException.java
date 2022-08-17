package ru.clevertec.exception;

public class NoSuchConnectionException extends RuntimeException {

    public NoSuchConnectionException(String message) {
        super(message);
    }
}
