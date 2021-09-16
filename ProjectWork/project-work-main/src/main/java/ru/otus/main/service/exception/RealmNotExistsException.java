package ru.otus.main.service.exception;

public class RealmNotExistsException extends RuntimeException {
    public RealmNotExistsException(String message) {
        super(message);
    }
}
