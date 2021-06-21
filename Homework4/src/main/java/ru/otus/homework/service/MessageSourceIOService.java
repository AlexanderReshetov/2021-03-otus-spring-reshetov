package ru.otus.homework.service;

import ru.otus.homework.service.exception.IOServiceException;
import ru.otus.homework.service.exception.MessageSourceIOServiceException;

public interface MessageSourceIOService {
    void print(String name, Object...varArgs);
    void println(String name, Object...varArgs);
    String readLine() throws MessageSourceIOServiceException;
}
