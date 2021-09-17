package ru.otus.loader.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Blizzard API dto токена должен")
public class BlizzardTokenDtoTest {
    @Test
    @DisplayName("корректно создаться конструктором")
    void shouldHaveCorrectConstructor() {
        final BlizzardTokenDto blizzardTokenDto = new BlizzardTokenDto("token", 1000L);

        assertEquals("token", blizzardTokenDto.getToken());
        assertEquals(1000L, blizzardTokenDto.getExpiresIn());
    }
}
