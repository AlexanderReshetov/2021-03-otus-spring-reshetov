package ru.otus.homework11.service.exception;

public class GenreNotExistsException extends RuntimeException {
    public GenreNotExistsException(String message) {
        super(message);
    }
}
