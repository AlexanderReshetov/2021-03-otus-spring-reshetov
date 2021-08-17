package ru.otus.homework6.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@DisplayName("Dto книги должен")
public class BookTest {
    @Test
    @DisplayName("корректно создаться конструктором")
    void shouldHaveCorrectConstructor() {
        final Author author = new Author(4L, "TestAuthor");
        final Genre genre = new Genre(5L, "TestGenre");
        final Book book = new Book(3L, "TestBook", author, genre);

        assertEquals(3L, book.getId());
        assertEquals("TestBook", book.getName());
        assertEquals(author, book.getAuthor());
        assertEquals(genre, book.getGenre());
    }
}
