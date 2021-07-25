package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис-раннер приложения ApplicationRunner должен")
public class ApplicationRunnerImplTest {
    @Mock
    private TestRunnerService testRunnerService;
    @InjectMocks
    TestApplicationRunner applicationRunnerService;

    @DisplayName("запустить сервис запуска тестирования TestRunnerService")
    @Test
    public void shouldExecuteTestRunnerService() {
        applicationRunnerService.run(null);

        verify(testRunnerService).run();
    }
}
