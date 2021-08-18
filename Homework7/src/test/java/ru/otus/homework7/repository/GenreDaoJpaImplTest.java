package ru.otus.homework7.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework7.repository.exception.GenreNotExistsException;
import ru.otus.homework7.model.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Import(GenreDaoJpaImpl.class)
@DataJpaTest
@DisplayName("Dao жанров должен")
public class GenreDaoJpaImplTest {
    private static final Long EXISTING_GENRE_ID_1 = 1L;
    private static final String EXISTING_GENRE_NAME_1 = "TestGenre1";
    private static final Long EXISTING_GENRE_ID_2 = 2L;
    private static final String EXISTING_GENRE_NAME_2 = "TestGenre2";
    private static final Long EXISTING_GENRE_ID_3 = 3L;
    private static final String EXISTING_GENRE_NAME_3 = "TestGenre3";
    private static final Long NON_EXISTING_GENRE_ID = 0L;

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private GenreDaoJpa genreDaoJpa;

    @Test
    @DisplayName("получить жанр по ИД")
    void shouldGetGenreById() {
        Genre entityManagerGenre = testEntityManager.find(Genre.class, EXISTING_GENRE_ID_1);
        Genre genre = genreDaoJpa.findById(EXISTING_GENRE_ID_1);

        assertEquals(entityManagerGenre.getId(), genre.getId());
        assertEquals(entityManagerGenre.getName(), genre.getName());
    }

    @Test
    @DisplayName("вызвать исключение при получении жанра по неверному ИД")
    void shouldThrowExceptionWhenWrongId() {
        assertThrows(GenreNotExistsException.class, () -> genreDaoJpa.findById(NON_EXISTING_GENRE_ID));
    }

    @Test
    @DisplayName("получить полный список жанров")
    void shouldGetGenreList() {
        List<Genre> genreList = genreDaoJpa.findAll();

        assertEquals(3, genreList.size());
        assertEquals(EXISTING_GENRE_ID_1, genreList.get(0).getId());
        assertEquals(EXISTING_GENRE_NAME_1, genreList.get(0).getName());
        assertEquals(EXISTING_GENRE_ID_2, genreList.get(1).getId());
        assertEquals(EXISTING_GENRE_NAME_2, genreList.get(1).getName());
        assertEquals(EXISTING_GENRE_ID_3, genreList.get(2).getId());
        assertEquals(EXISTING_GENRE_NAME_3, genreList.get(2).getName());
    }
}
