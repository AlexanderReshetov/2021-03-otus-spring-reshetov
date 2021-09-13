package ru.otus.loader.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Blizzard API dto аукционного лота должен")
public class BlizzardAuctionDtoTest {
    @Test
    @DisplayName("корректно создаться конструктором")
    void shouldHaveCorrectConstructor() {
        final BlizzardAuctionDto blizzardAuctionDto = new BlizzardAuctionDto(1L, null, 2L, 3L);

        assertEquals(1L, blizzardAuctionDto.getId());
        assertNull(blizzardAuctionDto.getBlizzardItemDto());
        assertEquals(2L, blizzardAuctionDto.getItemPrice());
        assertEquals(3L, blizzardAuctionDto.getCount());
    }
}
