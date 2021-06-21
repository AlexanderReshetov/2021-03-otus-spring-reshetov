package ru.otus.homework.service;

import ru.otus.homework.domain.Person;
import ru.otus.homework.domain.TestResult;
import ru.otus.homework.service.exception.TestServiceException;

public interface TestService {
    TestResult test(Person person, int questionCreditCount) throws TestServiceException;
}
