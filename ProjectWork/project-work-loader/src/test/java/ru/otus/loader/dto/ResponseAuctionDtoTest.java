package ru.otus.loader.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Собственный dto аукциона должен")
public class ResponseAuctionDtoTest {
    @Test
    @DisplayName("корректно создаться конструктором")
    void shouldHaveCorrectConstructor() {
        final ResponseAuctionDto responseAuctionDto = new ResponseAuctionDto(1L, 2L, 3L, 4L);

        assertEquals(1L, responseAuctionDto.getId());
        assertEquals(2L, responseAuctionDto.getItemId());
        assertEquals(3L, responseAuctionDto.getItemPrice());
        assertEquals(4L, responseAuctionDto.getCount());
    }

    @Test
    @DisplayName("преобразовать BlizzardAuctionDto")
    void shouldTransformBlizzardAuctionDto() {
        final ResponseAuctionDto responseAuctionDto = ResponseAuctionDto.toDto(new BlizzardAuctionDto(1L, new BlizzardAuctionItemDto(2L), 3L, 4L));

        assertEquals(1L, responseAuctionDto.getId());
        assertEquals(2L, responseAuctionDto.getItemId());
        assertEquals(3L, responseAuctionDto.getItemPrice());
        assertEquals(4L, responseAuctionDto.getCount());
    }
}
