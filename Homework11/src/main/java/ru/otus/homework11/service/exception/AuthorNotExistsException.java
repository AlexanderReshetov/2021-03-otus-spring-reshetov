package ru.otus.homework11.service.exception;

public class AuthorNotExistsException extends RuntimeException {
    public AuthorNotExistsException(String message) {
        super(message);
    }
}
