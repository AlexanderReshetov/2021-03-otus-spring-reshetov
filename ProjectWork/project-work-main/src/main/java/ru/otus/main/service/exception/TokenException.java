package ru.otus.main.service.exception;

public class TokenException extends RuntimeException {
    public TokenException(String message) {
        super(message);
    }
}
