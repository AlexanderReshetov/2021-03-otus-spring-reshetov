package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.domain.Person;
import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.TestResult;
import ru.otus.homework.service.exception.MessageSourceIOServiceException;
import ru.otus.homework.service.exception.NoUnansweredQuestionException;
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
            messageSourceIOService.print("message.output.question.caption");
            ioService.println(question.getText());
            messageSourceIOService.print("message.output.answer.caption");
            try {
                if (messageSourceIOService.readLine().equals(question.getAnswer())) {
                    countCorrectAnswers++;
                }
            }
            catch(MessageSourceIOServiceException e) {
                throw new TestServiceException("Answering error!", e);
            }

        }
        return new TestResult(person, questionCreditCount, countCorrectAnswers, countQuestions);
    }

    public List<Question> getQuestionList() {
        return questionDao.findAll();
    }

    public void answerNextQuestion(List<Question> questionList) {
        try {
            Question question = questionList.stream().filter(q -> q.getUserAnswer() == null).findFirst()
                    .orElseThrow(() -> new NoUnansweredQuestionException("There are no unanswered questions!"));
            try {
                question.setUserAnswer(testQuestion(question));
            } catch (TestServiceException e) {
                messageSourceIOService.println("message.output.error.answer");
            }
        } catch (NoUnansweredQuestionException e) {
            messageSourceIOService.println("message.output.question.next");
        }
    }

    public void answerQuestionByNumber(List<Question> questionList, int number) {
        if ((number >= 0) && (number < questionList.size())) {
            Question question = questionList.get(number);
            try {
                question.setUserAnswer(testQuestion(question));
            } catch (TestServiceException e) {
                messageSourceIOService.println("message.output.error.answer");
            }
        } else {
            messageSourceIOService.println("message.output.error.question.number");
        }
    }

    public TestResult testFromList(Person person, List<Question> questionList, int questionCreditCount) {
        int countCorrectAnswers = 0;
        int countQuestions = 0;
        for (Question question: questionList) {
            countQuestions++;
            if (question.getUserAnswer().equals(question.getAnswer())) {
                countCorrectAnswers++;
            }
        }
        return new TestResult(person, questionCreditCount, countCorrectAnswers, countQuestions);
    }

    private String testQuestion(Question question) throws TestServiceException {
        messageSourceIOService.print("message.output.question.caption");
        ioService.println(question.getText());
        if (question.getUserAnswer() != null) {
            messageSourceIOService.print("message.output.answer.caption.previous");
            ioService.println("(" + question.getUserAnswer() + ")");
        }
        messageSourceIOService.print("message.output.answer.caption");
        try {
            return messageSourceIOService.readLine();
        }
        catch(MessageSourceIOServiceException e) {
            throw new TestServiceException("Answering error!", e);
        }
    }
}
