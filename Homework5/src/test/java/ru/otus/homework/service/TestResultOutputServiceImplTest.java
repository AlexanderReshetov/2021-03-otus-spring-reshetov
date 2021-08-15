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
    private MessageSourceIOService messageSourceIOService;
    @InjectMocks
    private TestResultOutputServiceImpl testResultOutputService;

    @Test
    @DisplayName("вывести информацию об успешном прохождении теста")
    void shouldOutputSuccessTestResult () {
        final String congratulationName = "message.output.congratulation";
        final Person person = person();
        final TestResult testResult = new TestResult(person, 5, 5, 5);

        testResultOutputService.output(testResult);

        verify(messageSourceIOService).println(congratulationName);
    }

    @Test
    @DisplayName("вывести информацию о неудачном прохождении теста")
    void shouldOutputFailureTestResult () {
        final String comfortName = "message.output.comfort";
        final Person person = person();
        final TestResult testResult = new TestResult(person, 5, 4, 5);

        testResultOutputService.output(testResult);

        verify(messageSourceIOService).println(comfortName);
    }

    private Person person() {
        return new Person("TestSurname", "TestName");
    }

}
