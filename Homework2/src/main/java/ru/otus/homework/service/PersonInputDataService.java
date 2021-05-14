package ru.otus.homework.service;

import ru.otus.homework.domain.Person;

public interface PersonInputDataService {
    Person inputData() throws PersonInputDataException;
}
