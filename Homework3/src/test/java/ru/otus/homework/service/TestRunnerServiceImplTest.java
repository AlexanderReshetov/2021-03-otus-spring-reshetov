package ru.otus.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.domain.Person;
import ru.otus.homework.domain.TestResult;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис запуска тестирования TestRunnerServiceImpl должен")
public class TestRunnerServiceImplTest {
    @Mock
    private PersonInputDataService personInputDataService;
    @Mock
    private TestResultOutputService testResultOutputService;
    @Mock
    private TestService testService;

    private TestRunnerService testRunnerService;

    private static final int QUESTION_CREDIT_COUNT = 5;

    @BeforeEach
    void setUp() {
        testRunnerService = new TestRunnerServiceImpl(QUESTION_CREDIT_COUNT, personInputDataService, testResultOutputService, testService);
    }

    @Test
    @DisplayName("получить данные пользователя")
    void shouldInputPersonData() throws PersonInputDataException, TestRunnerException {
        testRunnerService.run();

        verify(personInputDataService).inputData();
    }

    @Test
    @DisplayName("провести тестирование")
    void shouldExecuteTest() throws PersonInputDataException, TestRunnerException, TestServiceException {
        Person person = person();
        given(personInputDataService.inputData()).willReturn(person);

        testRunnerService.run();

        verify(testService).test(person, QUESTION_CREDIT_COUNT);
    }

    @Test
    @DisplayName("вывести результаты тестирования")
    void shouldOutputTestResults() throws PersonInputDataException, TestRunnerException, TestServiceException {
        Person person = person();
        TestResult testResult = new TestResult(person, QUESTION_CREDIT_COUNT, 5);
        given(personInputDataService.inputData()).willReturn(person);
        given(testService.test(person, QUESTION_CREDIT_COUNT)).willReturn(testResult);

        testRunnerService.run();

        verify(testResultOutputService).output(testResult);
    }

    @Test
    @DisplayName("выбросить TestRunnerException при ошибке")
    void shouldThrowExceptionWhenError() throws PersonInputDataException, TestRunnerException {
        given(personInputDataService.inputData()).willThrow(PersonInputDataException.class);

        assertThrows(TestRunnerException.class, () -> testRunnerService.run());
    }

    Person person() {
        return new Person("TestSurname", "TestName");
    }
}
