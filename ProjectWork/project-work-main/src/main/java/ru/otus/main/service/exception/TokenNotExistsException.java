package ru.otus.main.service.exception;

public class TokenNotExistsException extends RuntimeException {
    public TokenNotExistsException(String message) {
        super(message);
    }
}
