package ru.otus.homework7.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework7.repository.exception.AuthorNotExistsException;
import ru.otus.homework7.model.Author;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Import(AuthorDaoJpaImpl.class)
@DataJpaTest
@DisplayName("Dao авторов должен")
public class AuthorDaoJpaImplTest {
    private static final Long EXISTING_AUTHOR_ID_1 = 1L;
    private static final String EXISTING_AUTHOR_NAME_1 = "TestAuthor1";
    private static final Long EXISTING_AUTHOR_ID_2 = 2L;
    private static final String EXISTING_AUTHOR_NAME_2 = "TestAuthor2";
    private static final Long NON_EXISTING_AUTHOR_ID = 0L;

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private AuthorDaoJpa authorDaoJpa;

    @Test
    @DisplayName("получить автора по ИД")
    void shouldGetAuthorById() {
        Author entityManagerAuthor = testEntityManager.find(Author.class, EXISTING_AUTHOR_ID_1);
        Author author = authorDaoJpa.findById(EXISTING_AUTHOR_ID_1);

        assertEquals(entityManagerAuthor.getId(), author.getId());
        assertEquals(entityManagerAuthor.getName(), author.getName());
    }

    @Test
    @DisplayName("вызвать исключение при получении автора по неверному ИД")
    void shouldThrowExceptionWhenWrongId() {
        assertThrows(AuthorNotExistsException.class, () -> authorDaoJpa.findById(NON_EXISTING_AUTHOR_ID));
    }

    @Test
    @DisplayName("получить полный список авторов")
    void shouldGetAuthorList() {
        List<Author> authorList = authorDaoJpa.findAll();

        assertEquals(2, authorList.size());
        assertEquals(EXISTING_AUTHOR_ID_1, authorList.get(0).getId());
        assertEquals(EXISTING_AUTHOR_NAME_1, authorList.get(0).getName());
        assertEquals(EXISTING_AUTHOR_ID_2, authorList.get(1).getId());
        assertEquals(EXISTING_AUTHOR_NAME_2, authorList.get(1).getName());
    }
}
