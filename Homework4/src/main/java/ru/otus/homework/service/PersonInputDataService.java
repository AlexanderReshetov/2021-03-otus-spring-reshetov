package ru.otus.homework.service;

import ru.otus.homework.domain.Person;
import ru.otus.homework.service.exception.PersonInputDataException;

public interface PersonInputDataService {
    Person inputData() throws PersonInputDataException;
}
