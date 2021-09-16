package ru.otus.main.service.exception;

public class ItemNotExistsException extends RuntimeException {
    public ItemNotExistsException(String message) {
        super(message);
    }
}
