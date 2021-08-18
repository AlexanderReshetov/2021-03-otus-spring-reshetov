package ru.otus.homework8.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.homework8.service.exception.BookNotExistsException;
import ru.otus.homework8.model.Author;
import ru.otus.homework8.model.Book;
import ru.otus.homework8.model.Genre;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Dao книг должен")
public class BookRepositoryTest {
    private static final Long EXISTING_BOOK_AUTHOR_ID_1 = 1L;
    private static final String EXISTING_BOOK_AUTHOR_NAME_1 = "TestAuthor1";
    private static final Long EXISTING_BOOK_GENRE_ID_1 = 1L;
    private static final String EXISTING_BOOK_GENRE_NAME_1 = "TestGenre1";

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("добавить книгу и получить ее по ИД")
    void shouldAddBookAndGetItById() {
        final Author author = new Author(EXISTING_BOOK_AUTHOR_ID_1, EXISTING_BOOK_AUTHOR_NAME_1);
        final Genre genre = new Genre(EXISTING_BOOK_GENRE_ID_1, EXISTING_BOOK_GENRE_NAME_1);
        final String newBookName = "New Book";
        Book book = new Book(null, newBookName, author, genre);
        book = bookRepository.save(book);
        Long newId = book.getId();
        book = bookRepository.findById(book.getId()).orElseThrow(() -> new BookNotExistsException("There is no book with id = " + newId));

        assertEquals(newBookName, book.getName());
        assertEquals(EXISTING_BOOK_AUTHOR_ID_1, book.getAuthor().getId());
        assertEquals(EXISTING_BOOK_AUTHOR_NAME_1, book.getAuthor().getName());
        assertEquals(EXISTING_BOOK_GENRE_ID_1, book.getGenre().getId());
        assertEquals(EXISTING_BOOK_GENRE_NAME_1, book.getGenre().getName());
    }
}
