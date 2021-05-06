package ru.otus.homework.service;

import ru.otus.homework.dao.TestRunnerIOException;
import ru.otus.homework.dao.QuestionReadException;

public interface TestRunnerService {
    public void run() throws QuestionReadException, TestRunnerIOException;
}
