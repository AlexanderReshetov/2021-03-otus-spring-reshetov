package ru.otus.main.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
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
