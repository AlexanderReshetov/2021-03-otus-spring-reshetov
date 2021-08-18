package ru.otus.homework8.service.exception;

public class GenreNotExistsException extends RuntimeException {
    public GenreNotExistsException(String message) {
        super(message);
    }
}
