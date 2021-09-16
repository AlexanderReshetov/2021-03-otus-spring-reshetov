package ru.otus.main.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
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
