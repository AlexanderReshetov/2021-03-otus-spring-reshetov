package ru.otus.loader.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Blizzard API dto списка серверов должен")
public class BlizzardRealmsDtoTest {
    @Test
    @DisplayName("корректно создаться конструктором")
    void shouldHaveCorrectConstructor() {
        final BlizzardRealmsDto blizzardRealmsDto = new BlizzardRealmsDto(Collections.emptyList());

        assertEquals(0, blizzardRealmsDto.getBlizzardRealmDtoList().size());
    }
}