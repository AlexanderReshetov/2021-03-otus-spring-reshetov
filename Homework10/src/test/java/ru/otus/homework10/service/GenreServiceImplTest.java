package ru.otus.homework10.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework10.domain.Genre;
import ru.otus.homework10.dto.ResponseGenreDto;
import ru.otus.homework10.repository.GenreRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис операций с жанрами должен")
public class GenreServiceImplTest {
    @Mock
    private GenreRepository genreRepository;
    @InjectMocks
    private GenreServiceImpl genreService;

    @Test
    @DisplayName("получить список всех жанров")
    void shouldGetAllGenres() {
        genreService.getAll();

        verify(genreRepository).findAll();
    }

    @Test
    @DisplayName("получить информацию об жанре по ИД")
    void shouldGetGenreById() {
        final Genre genre = new Genre(1L, "TestGenre1");
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));

        final ResponseGenreDto newResponseGenreDto = genreService.getById(1L);

        assertEquals(genre.getId(), newResponseGenreDto.getId());
        assertEquals(genre.getName(), newResponseGenreDto.getName());
    }
}