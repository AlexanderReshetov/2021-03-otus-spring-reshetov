package ru.otus.loader.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Blizzard API dto предмета аукционного лота должен")
public class BlizzardAuctionItemDtoTest {
    @Test
    @DisplayName("корректно создаться конструктором")
    void shouldHaveCorrectConstructor() {
        final BlizzardAuctionItemDto blizzardAuctionItemDto = new BlizzardAuctionItemDto(1L);

        assertEquals(1L, blizzardAuctionItemDto.getId());
    }
}
