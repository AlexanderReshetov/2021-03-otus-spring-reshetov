package ru.otus.library.service.exception;

public class AuthorNotExistsException extends RuntimeException {
    public AuthorNotExistsException(String message) {
        super(message);
    }
}
