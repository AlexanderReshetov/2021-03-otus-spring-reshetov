package ru.otus.homework12.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework12.dto.IntegrationTestResultDto;
import ru.otus.homework12.dto.UnitDto;
import ru.otus.homework12.dto.UnitTestResultDto;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис интеграционного тестирования должен")
public class IntegrationTestServiceTest {
    @Mock
    private PrintService printService;
    @InjectMocks
    private IntegrationTestService integrationTestService;

    private static final String UNIT_NAME = "unitName";
    private static final String UNIT_TEXT = "unitText";
    private static final Boolean UNIT_RESULT = true;

    @Test
    @DisplayName("вывести на экран результаты юнит-тестирования")
    void shouldShowUnitTestingResults() {
        integrationTestService.test(Collections.singletonList(new UnitTestResultDto(new UnitDto(UNIT_NAME, UNIT_TEXT), UNIT_RESULT)));

        verify(printService).println("Now code is on integration testing...");
        verify(printService).println("Name - " + UNIT_NAME);
        verify(printService).println("Text - " + UNIT_TEXT);
        verify(printService).println("Result - OK");
    }

    @Test
    @DisplayName("сформировать результаты интеграционного тестирования")
    void shouldShowIntegrationTestingResults() {
        IntegrationTestResultDto integrationTestResultDto = integrationTestService.test(Collections.singletonList(new UnitTestResultDto(new UnitDto(UNIT_NAME, UNIT_TEXT), UNIT_RESULT)));

        assertEquals(1, integrationTestResultDto.getUnitTestResultList().size());
        assertEquals(UNIT_NAME, integrationTestResultDto.getUnitTestResultList().get(0).getUnit().getName());
        assertEquals(UNIT_TEXT, integrationTestResultDto.getUnitTestResultList().get(0).getUnit().getText());
        assertEquals(UNIT_RESULT, integrationTestResultDto.getUnitTestResultList().get(0).getResult());
        assertNotNull(integrationTestResultDto.getResult());
    }
}
