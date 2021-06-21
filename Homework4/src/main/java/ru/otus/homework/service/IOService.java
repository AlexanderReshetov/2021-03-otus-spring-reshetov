package ru.otus.homework.service;

import ru.otus.homework.service.exception.IOServiceException;

public interface IOService {
    void print(String message);
    void println(String message);
    String readLine() throws IOServiceException;
}
