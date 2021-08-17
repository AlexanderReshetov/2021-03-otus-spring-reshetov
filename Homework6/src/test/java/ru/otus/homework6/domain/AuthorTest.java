package ru.otus.homework6.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@DisplayName("Dto автора должен")
public class AuthorTest {
    @Test
    @DisplayName("корректно создаться конструктором")
    void shouldHaveCorrectConstructor() {
        final Author author = new Author(1L, "TestAuthor");

        assertEquals(1L, author.getId());
        assertEquals("TestAuthor", author.getName());
    }
}
