package ru.otus.homework13.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.homework13.service.exception.AuthorNotExistsException;
import ru.otus.homework13.domain.Author;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DisplayName("Dao авторов должен")
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("добавить автора и получить его по ИД")
    void shouldAddAuthorAndGetHimById() {
        final String newAuthorName = "New Author";
        Author author = new Author(null, newAuthorName);
        author = authorRepository.save(author);
        Long newId = author.getId();
        author = authorRepository.findById(author.getId()).orElseThrow(() -> new AuthorNotExistsException("There is no author with id = " + newId));

        assertEquals(newAuthorName, author.getName());
    }
}
