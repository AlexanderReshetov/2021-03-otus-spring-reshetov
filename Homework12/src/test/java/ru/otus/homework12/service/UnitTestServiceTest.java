package ru.otus.homework12.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework12.dto.UnitDto;
import ru.otus.homework12.dto.UnitTestResultDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис юнит-тестирования должен")
public class UnitTestServiceTest {
    @Mock
    private PrintService printService;
    @InjectMocks
    private UnitTestService unitTestService;

    private static final String UNIT_NAME = "unitName";
    private static final String UNIT_TEXT = "unitText";

    @Test
    @DisplayName("вывести на экран юнит")
    void shouldShowUnit() {
        unitTestService.test(new UnitDto(UNIT_NAME, UNIT_TEXT));

        verify(printService).println("Now unit is on unit testing...");
        verify(printService).println("Name - " + UNIT_NAME);
        verify(printService).println("Text - " + UNIT_TEXT);
    }

    @Test
    @DisplayName("сформировать результаты юнит-тестирования")
    void shouldShowIntegrationTestingResults() {
        UnitTestResultDto unitTestResultDto = unitTestService.test(new UnitDto(UNIT_NAME, UNIT_TEXT));

        assertEquals(UNIT_NAME, unitTestResultDto.getUnit().getName());
        assertEquals(UNIT_TEXT, unitTestResultDto.getUnit().getText());
        assertNotNull(unitTestResultDto.getResult());
    }
}
