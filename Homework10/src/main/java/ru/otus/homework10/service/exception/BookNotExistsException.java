package ru.otus.homework10.service.exception;

public class BookNotExistsException extends RuntimeException {
    public BookNotExistsException(String message) {
        super(message);
    }
}
