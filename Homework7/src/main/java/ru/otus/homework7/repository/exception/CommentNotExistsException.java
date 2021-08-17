package ru.otus.homework7.repository.exception;

public class CommentNotExistsException extends RuntimeException {
    public CommentNotExistsException(String message) {
        super(message);
    }
}
