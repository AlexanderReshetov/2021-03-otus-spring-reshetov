package ru.otus.homework6.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework6.dao.exception.AuthorNotExistsException;
import ru.otus.homework6.domain.Author;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Import(AuthorDaoJdbc.class)
@JdbcTest
@DisplayName("Dao авторов должно")
public class AuthorDaoJdbcTest {
    private static final Long EXISTING_AUTHOR_ID_1 = 1L;
    private static final String EXISTING_AUTHOR_NAME_1 = "TestAuthor1";
    private static final Long EXISTING_AUTHOR_ID_2 = 2L;
    private static final String EXISTING_AUTHOR_NAME_2 = "TestAuthor2";
    private static final Long NON_EXISTING_AUTHOR_ID = 0L;

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @Test
    @DisplayName("получить автора по ИД")
    void shouldGetAuthorById() {
        Author author = authorDaoJdbc.findById(EXISTING_AUTHOR_ID_1);

        assertEquals(EXISTING_AUTHOR_ID_1, author.getId());
        assertEquals(EXISTING_AUTHOR_NAME_1, author.getName());
    }

    @Test
    @DisplayName("вызвать исключение при получении автора по неверному ИД")
    void shouldThrowExceptionWhenWrongId() {
        assertThrows(AuthorNotExistsException.class, () -> authorDaoJdbc.findById(NON_EXISTING_AUTHOR_ID));
    }

    @Test
    @DisplayName("получить полный список авторов")
    void shouldGetAuthorList() {
        List<Author> authorList = authorDaoJdbc.findAll();

        assertEquals(2, authorList.size());
        assertEquals(EXISTING_AUTHOR_ID_1, authorList.get(0).getId());
        assertEquals(EXISTING_AUTHOR_NAME_1, authorList.get(0).getName());
        assertEquals(EXISTING_AUTHOR_ID_2, authorList.get(1).getId());
        assertEquals(EXISTING_AUTHOR_NAME_2, authorList.get(1).getName());
    }
}
