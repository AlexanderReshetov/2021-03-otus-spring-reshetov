package ru.otus.homework.dao;

public class QuestionReadException extends RuntimeException{
    public QuestionReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
