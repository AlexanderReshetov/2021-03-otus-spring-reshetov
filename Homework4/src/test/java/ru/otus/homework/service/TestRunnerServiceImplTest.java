package ru.otus.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.ApplicationRunner;
import ru.otus.homework.config.QuestionConfig;
import ru.otus.homework.domain.Person;
import ru.otus.homework.domain.TestResult;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис запуска тестирования TestRunnerServiceImpl при запуске приложения должен")
public class TestRunnerServiceImplTest {
    @Mock
    private PersonInputDataService personInputDataService;
    @Mock
    private TestResultOutputService testResultOutputService;
    @Mock
    private TestService testService;
    @Mock
    private QuestionConfig questionConfig;
    @Mock
    private QuestionConfig.Credit credit;

    private ApplicationRunner applicationRunner;

    private static final int QUESTION_CREDIT_COUNT = 5;

    @BeforeEach
    void setUp() {
        given(questionConfig.getCredit()).willReturn(credit);
        given(credit.getCount()).willReturn(QUESTION_CREDIT_COUNT);
        applicationRunner = new TestRunnerServiceImpl(questionConfig, personInputDataService, testResultOutputService, testService);
    }

    @Test
    @DisplayName("получить данные пользователя")
    void shouldInputPersonData() throws Exception {
        applicationRunner.run(null);

        verify(personInputDataService).inputData();
    }

    @Test
    @DisplayName("провести тестирование")
    void shouldExecuteTest() throws Exception {
        final Person person = person();
        given(personInputDataService.inputData()).willReturn(person);

        applicationRunner.run(null);

        verify(testService).test(person, QUESTION_CREDIT_COUNT);
    }

    @Test
    @DisplayName("вывести результаты тестирования")
    void shouldOutputTestResults() throws Exception {
        final Person person = person();
        final TestResult testResult = new TestResult(person, QUESTION_CREDIT_COUNT, 5, 5);
        given(personInputDataService.inputData()).willReturn(person);
        given(testService.test(person, QUESTION_CREDIT_COUNT)).willReturn(testResult);

        applicationRunner.run(null);

        verify(testResultOutputService).output(testResult);
    }

    @Test
    @DisplayName("выбросить TestRunnerException при ошибке")
    void shouldThrowExceptionWhenError() throws Exception {
        given(personInputDataService.inputData()).willThrow(PersonInputDataException.class);

        assertThrows(TestRunnerException.class, () -> applicationRunner.run(null));
    }

    private Person person() {
        return new Person("TestSurname", "TestName");
    }
}
