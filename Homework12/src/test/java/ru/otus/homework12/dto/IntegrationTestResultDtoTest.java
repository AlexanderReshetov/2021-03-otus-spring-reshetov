package ru.otus.homework12.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Dto результатов интеграционного теста должен")
public class IntegrationTestResultDtoTest {
    @Test
    @DisplayName("корректно создаться конструктором")
    void shouldHaveCorrectConstructor() {
        IntegrationTestResultDto integrationTestResultDto = new IntegrationTestResultDto(Collections.emptyList(), true);

        assertEquals(0, integrationTestResultDto.getUnitTestResultList().size());
        assertTrue(integrationTestResultDto.getResult());
    }
}
