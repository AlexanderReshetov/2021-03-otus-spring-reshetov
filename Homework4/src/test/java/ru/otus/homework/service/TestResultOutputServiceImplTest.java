package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.domain.Person;
import ru.otus.homework.domain.TestResult;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис вывода информации о результатах тестирования TestResultOutputServiceImpl должен")
public class TestResultOutputServiceImplTest {
    @Mock
    private IOService ioService;
    @Mock
    private MessageSourceService messageSourceService;
    @InjectMocks
    private TestResultOutputServiceImpl testResultOutputService;

    @Test
    @DisplayName("вывести информацию об успешном прохождении теста")
    void shouldOutputSuccessTestResult () {
        final String congratulationName = "message.output.congratulation";
        final String congratulationText = "Congratulations! You get a credit!";
        final Person person = person();
        final TestResult testResult = new TestResult(person, 5, 5, 5);
        given(messageSourceService.getMessage(not(eq(congratulationName)), any())).willReturn("");
        given(messageSourceService.getMessage(congratulationName, null)).willReturn(congratulationText);

        testResultOutputService.output(testResult);

        verify(ioService).println(congratulationText);
    }

    @Test
    @DisplayName("вывести информацию о неудачном прохождении теста")
    void shouldOutputFailureTestResult () {
        final String comfortName = "message.output.comfort";
        final String comfortText = "Don't be upset, but you didn't get a credit :(";
        final Person person = person();
        final TestResult testResult = new TestResult(person, 5, 4, 5);
        given(messageSourceService.getMessage(not(eq(comfortName)), any())).willReturn("");
        given(messageSourceService.getMessage(comfortName, null)).willReturn(comfortText);

        testResultOutputService.output(testResult);

        verify(ioService).println(comfortText);
    }

    private Person person() {
        return new Person("TestSurname", "TestName");
    }

}
