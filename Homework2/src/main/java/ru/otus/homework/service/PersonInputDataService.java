package ru.otus.homework.service;

import ru.otus.homework.domain.Person;

public interface PersonInputDataService {
    public Person inputData() throws PersonInputDataException;
}
