package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.domain.Person;
import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.TestResult;
import ru.otus.homework.service.exception.MessageSourceIOServiceException;
import ru.otus.homework.service.exception.TestServiceException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис тестирования TestServiceImpl должен")
public class TestServiceImplTest {
    @Mock
    private QuestionDao questionDao;
    @Mock
    private MessageSourceIOService messageSourceIOService;
    @Mock
    private IOService ioService;
    @InjectMocks
    private TestServiceImpl testService;

    private static final int QUESTION_CREDIT_COUNT = 1;
    private static final String TEST_ANSWER = "TEST_ANSWER";

    @Test
    @DisplayName("получить результаты теста")
    void shouldGetTestResult() throws MessageSourceIOServiceException, TestServiceException {
        final Person person = person();
        final List<Question> questionList = questionList();
        given(questionDao.findAll()).willReturn(questionList);
        given(messageSourceIOService.readLine()).willReturn(TEST_ANSWER);

        TestResult testResult = testService.test(person, QUESTION_CREDIT_COUNT);

        assertEquals(testResult.getPerson(), person);
        assertEquals(testResult.getCountCorrectAnswers(), 1);
        assertEquals(testResult.getQuestionCreditCount(), QUESTION_CREDIT_COUNT);
    }

    @Test
    @DisplayName("выбросить TestServiceException при ошибке")
    void shouldThrowExceptionWhenError() throws MessageSourceIOServiceException {
        final Person person = person();
        final List<Question> questionList = questionList();
        given(questionDao.findAll()).willReturn(questionList);
        given(messageSourceIOService.readLine()).willThrow(MessageSourceIOServiceException.class);

        assertThrows(TestServiceException.class, () -> testService.test(person, QUESTION_CREDIT_COUNT));
    }

    @Test
    @DisplayName("получить список вопросов")
    void shouldGetQuestionList() {
        final List<Question> questionList = questionList();
        given(questionDao.findAll()).willReturn(questionList);

        final List<Question> receivedQuestionList = testService.getQuestionList();

        assertEquals(questionList.size(), receivedQuestionList.size());
        assertEquals(questionList.get(0).getText(), receivedQuestionList.get(0).getText());
        assertEquals(questionList.get(0).getAnswer(), receivedQuestionList.get(0).getAnswer());
    }

    @Test
    @DisplayName("задать следующий вопрос")
    void shouldAskNextQuestion() {
        final List<Question> questionList = questionList();

        testService.answerNextQuestion(questionList);

        verify(messageSourceIOService).print("message.output.question.caption");
        verify(ioService).println(questionList.get(0).getText());
    }

    @Test
    @DisplayName("получить ответ на следующий вопрос")
    void shouldGetAnswerForNextQuestion() throws MessageSourceIOServiceException {
        final List<Question> questionList = questionList();

        testService.answerNextQuestion(questionList);

        verify(messageSourceIOService).print("message.output.answer.caption");
        verify(messageSourceIOService).readLine();
    }

    @Test
    @DisplayName("вывести сообщение при ошибке получения ответа на следующий вопрос")
    void shouldPrintMessageWhenErrorWhileAnsweringNextQuestion() throws MessageSourceIOServiceException {
        final List<Question> questionList = questionList();
        given(messageSourceIOService.readLine()).willThrow(MessageSourceIOServiceException.class);

        testService.answerNextQuestion(questionList);

        verify(messageSourceIOService).println("message.output.error.answer");
    }

    @Test
    @DisplayName("вывести сообщение при отсутствии неотвеченных вопросов")
    void shouldPrintMessageWhenThereAreNoUnansweredQuestions() {
        final List<Question> questionList = questionList();
        questionList.get(0).setUserAnswer("UserAnswer");

        testService.answerNextQuestion(questionList);

        verify(messageSourceIOService).println("message.output.question.next");
    }

    @Test
    @DisplayName("задать вопрос по номеру")
    void shouldAskQuestionByNumber() {
        final List<Question> questionList = questionList();

        testService.answerQuestionByNumber(questionList, 0);

        verify(messageSourceIOService).print("message.output.question.caption");
        verify(ioService).println(questionList.get(0).getText());
    }

    @Test
    @DisplayName("получить ответ на вопрос по номеру")
    void shouldGetAnswerForQuestionByNumber() throws MessageSourceIOServiceException {
        final List<Question> questionList = questionList();

        testService.answerQuestionByNumber(questionList, 0);

        verify(messageSourceIOService).print("message.output.answer.caption");
        verify(messageSourceIOService).readLine();
    }

    @Test
    @DisplayName("вывести сообщение при ошибке получения ответа на вопрос по номеру")
    void shouldPrintMessageWhenErrorWhileAnsweringQuestionByNumber() throws MessageSourceIOServiceException {
        final List<Question> questionList = questionList();
        given(messageSourceIOService.readLine()).willThrow(MessageSourceIOServiceException.class);

        testService.answerQuestionByNumber(questionList, 0);

        verify(messageSourceIOService).println("message.output.error.answer");
    }

    @Test
    @DisplayName("вывести сообщение при отсутствии вопроса с указанным номером")
    void shouldPrintMessageWhenThereIsNoQuestionWithThisNumber() {
        final List<Question> questionList = questionList();

        testService.answerQuestionByNumber(questionList, 1);

        verify(messageSourceIOService).println("message.output.error.question.number");
    }

    @Test
    @DisplayName("получить результаты теста по листу вопросов с ответами")
    void shouldGetTestResultByQuestionList() {
        final Person person = person();
        final List<Question> questionList = questionList();

        TestResult testResult = testService.testFromList(person, questionList, QUESTION_CREDIT_COUNT);

        assertEquals(person, testResult.getPerson());
        assertEquals(QUESTION_CREDIT_COUNT, testResult.getQuestionCreditCount());
        assertEquals(0, testResult.getCountCorrectAnswers());
        assertEquals(1, testResult.getCountQuestions());
    }

    private Person person() {
        return new Person("TestSurname", "TestName");
    }

    private Question question() {
        return new Question("Text", TEST_ANSWER);
    }

    private List<Question> questionList() {
        return Collections.singletonList(question());
    }
}
