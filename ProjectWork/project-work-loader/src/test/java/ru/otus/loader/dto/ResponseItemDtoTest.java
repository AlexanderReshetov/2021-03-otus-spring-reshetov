package ru.otus.loader.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Собственный dto предмета должен")
public class ResponseItemDtoTest {
    @Test
    @DisplayName("корректно создаться конструктором")
    void shouldHaveCorrectConstructor() {
        final ResponseItemDto responseItemDto = new ResponseItemDto(1L, "item", "предмет");

        assertEquals(1L, responseItemDto.getId());
        assertEquals("item", responseItemDto.getEnName());
        assertEquals("предмет", responseItemDto.getRuName());
    }

    @Test
    @DisplayName("преобразовать BlizzardItemDto")
    void shouldTransformBlizzardItemDto() {
        final ResponseItemDto responseItemDto = ResponseItemDto.toDto(new BlizzardItemDto(1L, new BlizzardNameDto("item", "предмет")));

        assertEquals(1L, responseItemDto.getId());
        assertEquals("item", responseItemDto.getEnName());
        assertEquals("предмет", responseItemDto.getRuName());
    }
}
