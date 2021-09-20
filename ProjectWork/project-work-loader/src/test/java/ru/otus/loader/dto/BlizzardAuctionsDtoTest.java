package ru.otus.loader.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Blizzard API dto аукциона должен")
public class BlizzardAuctionsDtoTest {
    @Test
    @DisplayName("корректно создаться конструктором")
    void shouldHaveCorrectConstructor() {
        final BlizzardAuctionsDto blizzardAuctionsDto = new BlizzardAuctionsDto(Collections.emptyList());

        assertEquals(0, blizzardAuctionsDto.getBlizzardAuctionDtoList().size());
    }
}
