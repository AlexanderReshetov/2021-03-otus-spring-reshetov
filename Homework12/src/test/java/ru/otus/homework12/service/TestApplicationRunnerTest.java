package ru.otus.homework12.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework12.gateway.UnitTestGateway;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис-раннер приложения должен")
public class TestApplicationRunnerTest {
    @Mock
    private UnitTestGateway unitTestGateway;
    @InjectMocks
    TestApplicationRunner testApplicationRunner;

    @DisplayName("передать сообщение в шлюз юнит-тестирования")
    @Test
    public void shouldSendMessageToUnitTestingGateway() {
        testApplicationRunner.run(null);

        verify(unitTestGateway).process(any());
    }
}
