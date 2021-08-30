package ru.otus.homework11.service.exception;

public class UserNotExistsException extends RuntimeException {
    public UserNotExistsException(String message) {
        super(message);
    }
}
