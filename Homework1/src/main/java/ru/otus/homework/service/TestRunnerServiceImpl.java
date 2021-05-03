package ru.otus.homework.service;

import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.dao.QuestionReadException;
import ru.otus.homework.domain.Question;

import java.util.List;

public class TestRunnerServiceImpl implements TestRunnerService{
    private final QuestionDao questionDao;

    public TestRunnerServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public void run() throws QuestionReadException {
        List<Question> questionList = questionDao.findAll();
        for (Question question: questionList
        ) {
            System.out.println(question.getText());
        }

    }
}
