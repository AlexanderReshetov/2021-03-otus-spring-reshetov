package ru.otus.genre.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.genre.domain.Genre;

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

    @Test
    @DisplayName("корректно создаться из доменного объекта")
    void shouldHaveCorrectToDtoMethod() {
        final ResponseGenreDto responseGenreDto = ResponseGenreDto.toDto(new Genre(1L, "TestGenre"));

        assertEquals(1L, responseGenreDto.getId());
        assertEquals("TestGenre", responseGenreDto.getName());
    }
}
