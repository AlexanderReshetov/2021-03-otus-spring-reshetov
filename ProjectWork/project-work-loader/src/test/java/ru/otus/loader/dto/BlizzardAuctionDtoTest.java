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
        final BlizzardAuctionDto blizzardAuctionDto = new BlizzardAuctionDto(1L, null, 2L, 3L, 4L);

        assertEquals(1L, blizzardAuctionDto.getId());
        assertNull(blizzardAuctionDto.getBlizzardAuctionItemDto());
        assertEquals(2L, blizzardAuctionDto.getBuyout());
        assertEquals(3L, blizzardAuctionDto.getUnitPrice());
        assertEquals(4L, blizzardAuctionDto.getCount());
    }
}
