package ru.otus.homework.service;

import ru.otus.homework.domain.Person;
import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.TestResult;
import ru.otus.homework.service.exception.TestServiceException;

import java.util.List;

public interface TestService {
    TestResult test(Person person, int questionCreditCount) throws TestServiceException;
    List<Question> getQuestionList();
    void answerNextQuestion(List<Question> questionList);
    void answerQuestionByNumber(List<Question> questionList, int number);
    TestResult testFromList(Person person, List<Question> questionList, int questionCreditCount);
}
