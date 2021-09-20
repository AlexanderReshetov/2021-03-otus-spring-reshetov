package ru.otus.loader.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Blizzard API dto наименования должен")
public class BlizzardNameDtoTest {
    @Test
    @DisplayName("корректно создаться конструктором")
    void shouldHaveCorrectConstructor() {
        final BlizzardNameDto blizzardNameDto = new BlizzardNameDto("item", "предмет");

        assertEquals("item", blizzardNameDto.getEn_US());
        assertEquals("предмет", blizzardNameDto.getRu_RU());
    }
}
