package ru.otus.homework6.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework6.dao.exception.GenreNotExistsException;
import ru.otus.homework6.domain.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Import(GenreDaoJdbc.class)
@JdbcTest
@DisplayName("Dao жанров должно")
public class GenreDaoJdbcTest {
    private static final Long EXISTING_GENRE_ID_1 = 1L;
    private static final String EXISTING_GENRE_NAME_1 = "TestGenre1";
    private static final Long EXISTING_GENRE_ID_2 = 2L;
    private static final String EXISTING_GENRE_NAME_2 = "TestGenre2";
    private static final Long EXISTING_GENRE_ID_3 = 3L;
    private static final String EXISTING_GENRE_NAME_3 = "TestGenre3";
    private static final Long NON_EXISTING_GENRE_ID = 0L;

    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    @Test
    @DisplayName("получить жанр по ИД")
    void shouldGetGenreById() {
        Genre genre = genreDaoJdbc.findById(EXISTING_GENRE_ID_1);

        assertEquals(EXISTING_GENRE_ID_1, genre.getId());
        assertEquals(EXISTING_GENRE_NAME_1, genre.getName());
    }

    @Test
    @DisplayName("вызвать исключение при получении жанра по неверному ИД")
    void shouldThrowExceptionWhenWrongId() {
        assertThrows(GenreNotExistsException.class, () -> genreDaoJdbc.findById(NON_EXISTING_GENRE_ID));
    }

    @Test
    @DisplayName("получить полный список жанров")
    void shouldGetGenreList() {
        List<Genre> genreList = genreDaoJdbc.findAll();

        assertEquals(3, genreList.size());
        assertEquals(EXISTING_GENRE_ID_1, genreList.get(0).getId());
        assertEquals(EXISTING_GENRE_NAME_1, genreList.get(0).getName());
        assertEquals(EXISTING_GENRE_ID_2, genreList.get(1).getId());
        assertEquals(EXISTING_GENRE_NAME_2, genreList.get(1).getName());
        assertEquals(EXISTING_GENRE_ID_3, genreList.get(2).getId());
        assertEquals(EXISTING_GENRE_NAME_3, genreList.get(2).getName());
    }
}
