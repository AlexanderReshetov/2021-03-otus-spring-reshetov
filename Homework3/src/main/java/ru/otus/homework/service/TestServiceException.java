package ru.otus.homework.service;

public class TestServiceException extends Exception{
    public TestServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
