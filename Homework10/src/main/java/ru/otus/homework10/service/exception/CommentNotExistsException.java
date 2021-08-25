package ru.otus.homework10.service.exception;

public class CommentNotExistsException extends RuntimeException {
    public CommentNotExistsException(String message) {
        super(message);
    }
}
