package ru.otus.loader.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Собственный dto токена должен")
public class ResponseTokenDtoTest {
    @Test
    @DisplayName("корректно создаться конструктором")
    void shouldHaveCorrectConstructor() {
        final LocalDateTime localDateTime = LocalDateTime.now();
        final ResponseTokenDto responseTokenDto = new ResponseTokenDto("token", localDateTime);

        assertEquals("token", responseTokenDto.getToken());
        assertEquals(localDateTime, responseTokenDto.getExpirationDt());
    }
}
