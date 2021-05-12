package ru.otus.homework.service;

import ru.otus.homework.domain.Person;

public interface OutputDataService {
    public void outputData(Person person, int countCorrectAnswers, int questionCreditCount);
}
