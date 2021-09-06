package ru.otus.author.service.exception;

public class GenreNotExistsException extends RuntimeException {
    public GenreNotExistsException(String message) {
        super(message);
    }
}
