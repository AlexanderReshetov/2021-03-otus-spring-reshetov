package ru.otus.main.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@DisplayName("Dto предмета должен")
public class ResponseItemDtoTest {
    @Test
    @DisplayName("корректно создаться конструктором")
    void shouldHaveCorrectConstructor() {
        final ResponseItemDto responseItemDto = new ResponseItemDto(1L, "item", "предмет");

        assertEquals(1L, responseItemDto.getId());
        assertEquals("item", responseItemDto.getEnName());
        assertEquals("предмет", responseItemDto.getRuName());
    }
}
