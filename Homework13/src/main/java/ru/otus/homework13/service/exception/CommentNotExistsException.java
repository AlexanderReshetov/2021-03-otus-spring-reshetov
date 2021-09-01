package ru.otus.homework13.service.exception;

public class CommentNotExistsException extends RuntimeException {
    public CommentNotExistsException(String message) {
        super(message);
    }
}
