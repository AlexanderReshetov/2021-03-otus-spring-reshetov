package ru.otus.homework.service.exception;

public class NoUnansweredQuestionException extends RuntimeException {
    public NoUnansweredQuestionException(String message) {
        super(message);
    }
}
