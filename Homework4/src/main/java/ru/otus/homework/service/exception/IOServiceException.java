package ru.otus.homework.service.exception;

public class IOServiceException extends RuntimeException {
    public IOServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
