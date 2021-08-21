package ru.otus.homework10.service.exception;

public class AuthorNotExistsException extends RuntimeException {
    public AuthorNotExistsException(String message) {
        super(message);
    }
}
