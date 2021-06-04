package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.domain.Person;
import ru.otus.homework.domain.TestResult;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис вывода информации о результатах тестирования TestResultOutputServiceImpl должен")
public class TestResultOutputServiceImplTest {
    @Mock
    private IOService ioService;
    @InjectMocks
    private TestResultOutputServiceImpl testResultOutputService;

    @Test
    @DisplayName("вывести информацию об успешном прохождении теста")
    void shouldOutputSuccessTestResult () {
        Person person = person();
        TestResult testResult = new TestResult(person, 5, 5);

        testResultOutputService.output(testResult);

        verify(ioService).println("Congratulations! You get a credit!");
    }

    @Test
    @DisplayName("вывести информацию о неудачном прохождении теста")
    void shouldOutputFailureTestResult () {
        Person person = person();
        TestResult testResult = new TestResult(person, 5, 4);

        testResultOutputService.output(testResult);

        verify(ioService).println("Don't be upset, but you didn't get a credit :(");
    }

    Person person() {
        return new Person("TestSurname", "TestName");
    }

}
