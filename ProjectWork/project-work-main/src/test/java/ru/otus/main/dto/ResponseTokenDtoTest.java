package ru.otus.main.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Dto токена должен")
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
