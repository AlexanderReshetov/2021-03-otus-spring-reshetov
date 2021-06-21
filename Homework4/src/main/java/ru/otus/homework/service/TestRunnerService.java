package ru.otus.homework.service;


import ru.otus.homework.service.exception.TestRunnerException;

public interface TestRunnerService {
    void run() throws TestRunnerException;
}
