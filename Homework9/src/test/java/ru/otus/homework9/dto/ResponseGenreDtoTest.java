package ru.otus.homework9.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@DisplayName("Dto жанра должен")
public class ResponseGenreDtoTest {
    @Test
    @DisplayName("корректно создаться конструктором")
    void shouldHaveCorrectConstructor() {
        final ResponseGenreDto responseGenreDto = new ResponseGenreDto(1L, "TestGenre");

        assertEquals(1L, responseGenreDto.getId());
        assertEquals("TestGenre", responseGenreDto.getName());
    }
}
