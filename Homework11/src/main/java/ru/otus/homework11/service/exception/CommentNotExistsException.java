package ru.otus.homework11.service.exception;

public class CommentNotExistsException extends RuntimeException {
    public CommentNotExistsException(String message) {
        super(message);
    }
}
