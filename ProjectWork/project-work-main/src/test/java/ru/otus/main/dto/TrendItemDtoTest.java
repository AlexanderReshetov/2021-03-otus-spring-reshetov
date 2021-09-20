package ru.otus.main.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Dto тренда должен")
public class TrendItemDtoTest {
    @Test
    @DisplayName("корректно создаться конструктором")
    void shouldHaveCorrectConstructor() {
        final LocalDateTime localDateTime = LocalDateTime.now();
        final Long price = 4L;
        final TrendItemDto trendItemDto = new TrendItemDto(localDateTime, price);

        assertEquals(localDateTime,trendItemDto.getLocalDateTime());
        assertEquals(price, trendItemDto.getPrice());
    }
}