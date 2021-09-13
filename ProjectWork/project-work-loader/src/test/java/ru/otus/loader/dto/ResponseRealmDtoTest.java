package ru.otus.loader.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Собственный dto игрового мира должен")
public class ResponseRealmDtoTest {
    @Test
    @DisplayName("корректно создаться конструктором")
    void shouldHaveCorrectConstructor() {
        final ResponseRealmDto responseRealmDto = new ResponseRealmDto(1L, "Goldrinn", "Голдринн");

        assertEquals(1L, responseRealmDto.getId());
        assertEquals("Goldrinn", responseRealmDto.getEnName());
        assertEquals("Голдринн", responseRealmDto.getRuName());
    }

    @Test
    @DisplayName("преобразовать BlizzardRealmDto")
    void shouldTransformBlizzardRealmDto() {
        final ResponseRealmDto responseRealmDto = ResponseRealmDto.toDto(new BlizzardRealmDto(1L, new BlizzardNameDto("Goldrinn", "Голдринн")));

        assertEquals(1L, responseRealmDto.getId());
        assertEquals("Goldrinn", responseRealmDto.getEnName());
        assertEquals("Голдринн", responseRealmDto.getRuName());
    }
}
