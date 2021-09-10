package ru.otus.genre.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.genre.domain.Genre;
import ru.otus.genre.service.exception.GenreNotExistsException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DisplayName("Dao жанров должен")
public class GenreRepositoryTest {
    @Autowired
    private GenreRepository genreRepository;

    @Test
    @DisplayName("добавить жанр и получить его по ИД")
    void shouldAddGenreAndGetHimById() {
        final String newGenreName = "New Genre";
        Genre genre = new Genre(null, newGenreName);
        genre = genreRepository.save(genre);
        Long newId = genre.getId();
        genre = genreRepository.findById(genre.getId()).orElseThrow(() -> new GenreNotExistsException("There is no genre with id = " + newId));

        assertEquals(newGenreName, genre.getName());
    }
}
