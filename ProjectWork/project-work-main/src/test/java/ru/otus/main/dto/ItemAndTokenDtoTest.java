package ru.otus.main.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Dto предмета-токена должен")
public class ItemAndTokenDtoTest {
    @Test
    @DisplayName("корректно создаться конструктором")
    void shouldHaveCorrectConstructor() {
        final ItemAndTokenDto itemAndTokenDto = new ItemAndTokenDto(null, null);

        assertNull(itemAndTokenDto.getItem());
        assertNull(itemAndTokenDto.getToken());
    }
}
