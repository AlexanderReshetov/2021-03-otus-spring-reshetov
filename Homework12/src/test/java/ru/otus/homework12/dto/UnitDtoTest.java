package ru.otus.homework12.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dto юнита должен")
public class UnitDtoTest {
    @Test
    @DisplayName("корректно создаться конструктором")
    void shouldHaveCorrectConstructor() {
        UnitDto unitDto = new UnitDto("name", "text");

        assertEquals("name", unitDto.getName());
        assertEquals("text", unitDto.getText());
    }
}
