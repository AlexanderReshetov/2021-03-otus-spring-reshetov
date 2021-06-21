package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.domain.Person;
import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.TestResult;
import ru.otus.homework.service.exception.MessageSourceIOServiceException;
import ru.otus.homework.service.exception.TestServiceException;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    private final QuestionDao questionDao;
    private final MessageSourceIOService messageSourceIOService;
    private final IOService ioService;

    @Autowired
    public TestServiceImpl(QuestionDao questionDao, MessageSourceIOService messageSourceIOService, IOService ioService) {
        this.questionDao = questionDao;
        this.messageSourceIOService = messageSourceIOService;
        this.ioService = ioService;
    }

    public TestResult test(Person person, int questionCreditCount) throws TestServiceException {
        int countCorrectAnswers = 0;
        int countQuestions = 0;
        final List<Question> questionList = questionDao.findAll();
        for (Question question: questionList) {
            countQuestions++;
            messageSourceIOService.print("message.output.question.caption", null);
            ioService.println(question.getText());
            messageSourceIOService.print("message.output.answer.caption", null);
            try {
                if (messageSourceIOService.readLine().equals(question.getAnswer())) {
                    countCorrectAnswers++;
                }
            }
            catch(MessageSourceIOServiceException e) {
                throw new TestServiceException("Answering error!", e);
            }

        }
        final TestResult testResult = new TestResult(person, questionCreditCount, countCorrectAnswers, countQuestions);
        return testResult;
    }
}
