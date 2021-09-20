package ru.otus.loader.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Blizzard API dto мира должен")
public class BlizzardRealmDtoTest {
    @Test
    @DisplayName("корректно создаться конструктором")
    void shouldHaveCorrectConstructor() {
        final BlizzardRealmDto blizzardRealmDto = new BlizzardRealmDto(1L, null);

        assertEquals(1L, blizzardRealmDto.getId());
        assertNull(blizzardRealmDto.getBlizzardNameDto());
    }
}
