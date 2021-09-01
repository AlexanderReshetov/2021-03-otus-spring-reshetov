package ru.otus.library.service.exception;

public class CommentNotExistsException extends RuntimeException {
    public CommentNotExistsException(String message) {
        super(message);
    }
}
