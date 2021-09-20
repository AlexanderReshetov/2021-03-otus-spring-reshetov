package ru.otus.main.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Dto игрового мира должен")
public class ResponseRealmDtoTest {
    @Test
    @DisplayName("корректно создаться конструктором")
    void shouldHaveCorrectConstructor() {
        final ResponseRealmDto responseRealmDto = new ResponseRealmDto(1L, "Goldrinn", "Голдринн");

        assertEquals(1L, responseRealmDto.getId());
        assertEquals("Goldrinn", responseRealmDto.getEnName());
        assertEquals("Голдринн", responseRealmDto.getRuName());
    }
}
