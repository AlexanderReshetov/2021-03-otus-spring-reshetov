package ru.otus.homework8.service.exception;

public class AuthorNotExistsException extends RuntimeException {
    public AuthorNotExistsException(String message) {
        super(message);
    }
}
