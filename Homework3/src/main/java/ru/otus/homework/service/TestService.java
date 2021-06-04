package ru.otus.homework.service;

import ru.otus.homework.domain.Person;
import ru.otus.homework.domain.TestResult;

public interface TestService {
    TestResult test(Person person, int questionCreditCount) throws TestServiceException;
}
