package ru.otus.homework13.service.exception;

public class AuthorNotExistsException extends RuntimeException {
    public AuthorNotExistsException(String message) {
        super(message);
    }
}
