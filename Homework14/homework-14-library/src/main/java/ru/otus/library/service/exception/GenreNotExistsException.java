package ru.otus.library.service.exception;

public class GenreNotExistsException extends RuntimeException {
    public GenreNotExistsException(String message) {
        super(message);
    }
}
