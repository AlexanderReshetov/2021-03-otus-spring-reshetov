package ru.otus.homework11.service.exception;

public class BookNotExistsException extends RuntimeException {
    public BookNotExistsException(String message) {
        super(message);
    }
}
