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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис тестирования TestServiceImpl должен")
public class TestServiceImplTest {
    @Mock
    private QuestionDao questionDao;
    @Mock
    private IOService ioService;
    @InjectMocks
    private TestServiceImpl testService;

    private static final int QUESTION_CREDIT_COUNT = 1;
    private static final String TEST_ANSWER = "TEST_ANSWER";

    @Test
    @DisplayName("получить результаты теста")
    void shouldGetTestResult() throws IOServiceException, TestServiceException {
        Person person = person();
        List<Question> questionList = questionList();
        given(questionDao.findAll()).willReturn(questionList);
        given(ioService.readLine()).willReturn(TEST_ANSWER);

        TestResult testResult = testService.test(person, QUESTION_CREDIT_COUNT);

        assertEquals(testResult.getPerson(), person);
        assertEquals(testResult.getCountCorrectAnswers(), 1);
        assertEquals(testResult.getQuestionCreditCount(), QUESTION_CREDIT_COUNT);
    }

    @Test
    @DisplayName("выбросить TestServiceException при ошибке")
    void shouldThrowExceptionWhenError() throws IOServiceException, TestServiceException {
        Person person = person();
        List<Question> questionList = questionList();
        given(questionDao.findAll()).willReturn(questionList);
        given(ioService.readLine()).willThrow(IOServiceException.class);

        assertThrows(TestServiceException.class, () -> testService.test(person, QUESTION_CREDIT_COUNT));
    }

    private Person person() {
        return new Person("TestSurname", "TestName");
    }

    private Question question() {
        return new Question("Text", TEST_ANSWER);
    }

    private List<Question> questionList() {
        List<Question> questionList = new ArrayList<Question>();
        questionList.add(question());
        return questionList;
    }
}
