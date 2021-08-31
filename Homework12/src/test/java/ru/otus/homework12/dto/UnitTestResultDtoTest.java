package ru.otus.homework12.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dto результатов юнит-теста должен")
public class UnitTestResultDtoTest {
    @Test
    @DisplayName("корректно создаться конструктором")
    void shouldHaveCorrectConstructor() {
        UnitTestResultDto unitTestResultDto = new UnitTestResultDto(null, true);

        assertNull(unitTestResultDto.getUnit());
        assertTrue(unitTestResultDto.getResult());
    }
}
