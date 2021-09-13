package ru.otus.loader.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Blizzard API dto предмета должен")
public class BlizzardItemDtoTest {
    @Test
    @DisplayName("корректно создаться конструктором")
    void shouldHaveCorrectConstructor() {
        final BlizzardItemDto blizzardItemDto = new BlizzardItemDto(1L, null);

        assertEquals(1L, blizzardItemDto.getId());
        assertNull(blizzardItemDto.getBlizzardNameDto());
    }
}
