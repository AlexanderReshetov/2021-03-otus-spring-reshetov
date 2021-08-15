package ru.otus.homework6.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework6.dao.exception.BookNotExistsException;
import ru.otus.homework6.domain.Author;
import ru.otus.homework6.domain.Book;
import ru.otus.homework6.domain.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Import(BookDaoJdbc.class)
@JdbcTest
@DisplayName("Dao книг должно")
public class BookDaoJdbcTest {
    private static final Long EXISTING_BOOK_ID_1 = 1L;
    private static final String EXISTING_BOOK_NAME_1 = "TestBook1";
    private static final Long EXISTING_BOOK_AUTHOR_ID_1 = 1L;
    private static final String EXISTING_BOOK_AUTHOR_NAME_1 = "TestAuthor1";
    private static final Long EXISTING_BOOK_GENRE_ID_1 = 1L;
    private static final String EXISTING_BOOK_GENRE_NAME_1 = "TestGenre1";
    private static final Long EXISTING_BOOK_ID_2 = 2L;
    private static final String EXISTING_BOOK_NAME_2 = "TestBook2";
    private static final Long EXISTING_BOOK_AUTHOR_ID_2 = 1L;
    private static final String EXISTING_BOOK_AUTHOR_NAME_2 = "TestAuthor1";
    private static final Long EXISTING_BOOK_GENRE_ID_2 = 2L;
    private static final String EXISTING_BOOK_GENRE_NAME_2 = "TestGenre2";
    private static final Long NON_EXISTING_BOOK_ID = 0L;
    private static final String NON_EXISTING_BOOK_NAME = "Non-existing book";

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @Test
    @DisplayName("добавить книгу")
    void shouldInsertBook() {
        bookDaoJdbc.insert(new Book(NON_EXISTING_BOOK_ID, NON_EXISTING_BOOK_NAME, new Author(EXISTING_BOOK_AUTHOR_ID_1, EXISTING_BOOK_AUTHOR_NAME_1), new Genre(EXISTING_BOOK_GENRE_ID_1, EXISTING_BOOK_GENRE_NAME_1)));
        Book book = bookDaoJdbc.findById(NON_EXISTING_BOOK_ID);

        assertEquals(NON_EXISTING_BOOK_ID, book.getId());
        assertEquals(NON_EXISTING_BOOK_NAME, book.getName());
        assertEquals(EXISTING_BOOK_AUTHOR_ID_1, book.getAuthor().getId());
        assertEquals(EXISTING_BOOK_AUTHOR_NAME_1, book.getAuthor().getName());
        assertEquals(EXISTING_BOOK_GENRE_ID_1, book.getGenre().getId());
        assertEquals(EXISTING_BOOK_GENRE_NAME_1, book.getGenre().getName());
    }

    @Test
    @DisplayName("обновить информацию о книге")
    void shouldUpdateBook() {
        bookDaoJdbc.update(new Book(EXISTING_BOOK_ID_1, NON_EXISTING_BOOK_NAME, new Author(EXISTING_BOOK_AUTHOR_ID_1, EXISTING_BOOK_AUTHOR_NAME_1), new Genre(EXISTING_BOOK_GENRE_ID_2, EXISTING_BOOK_GENRE_NAME_2)));
        Book book = bookDaoJdbc.findById(EXISTING_BOOK_ID_1);

        assertEquals(EXISTING_BOOK_ID_1, book.getId());
        assertEquals(NON_EXISTING_BOOK_NAME, book.getName());
        assertEquals(EXISTING_BOOK_AUTHOR_ID_1, book.getAuthor().getId());
        assertEquals(EXISTING_BOOK_AUTHOR_NAME_1, book.getAuthor().getName());
        assertEquals(EXISTING_BOOK_GENRE_ID_2, book.getGenre().getId());
        assertEquals(EXISTING_BOOK_GENRE_NAME_2, book.getGenre().getName());
    }

    @Test
    @DisplayName("удалить книгу")
    void shouldDeleteBook() {
        bookDaoJdbc.delete(EXISTING_BOOK_ID_1);
        List<Book> bookList = bookDaoJdbc.findAll();

        assertEquals(1, bookList.size());
        assertEquals(EXISTING_BOOK_ID_2, bookList.get(0).getId());
        assertEquals(EXISTING_BOOK_NAME_2, bookList.get(0).getName());
        assertEquals(EXISTING_BOOK_AUTHOR_ID_2, bookList.get(0).getAuthor().getId());
        assertEquals(EXISTING_BOOK_AUTHOR_NAME_2, bookList.get(0).getAuthor().getName());
        assertEquals(EXISTING_BOOK_GENRE_ID_2, bookList.get(0).getGenre().getId());
        assertEquals(EXISTING_BOOK_GENRE_NAME_2, bookList.get(0).getGenre().getName());
    }

    @Test
    @DisplayName("получить книгу по ИД")
    void shouldGetBookById() {
        Book book = bookDaoJdbc.findById(EXISTING_BOOK_ID_1);

        assertEquals(EXISTING_BOOK_ID_1, book.getId());
        assertEquals(EXISTING_BOOK_NAME_1, book.getName());
        assertEquals(EXISTING_BOOK_AUTHOR_ID_1, book.getAuthor().getId());
        assertEquals(EXISTING_BOOK_AUTHOR_NAME_1, book.getAuthor().getName());
        assertEquals(EXISTING_BOOK_GENRE_ID_1, book.getGenre().getId());
        assertEquals(EXISTING_BOOK_GENRE_NAME_1, book.getGenre().getName());
    }

    @Test
    @DisplayName("вызвать исключение при получении книги по неверному ИД")
    void shouldThrowExceptionWhenWrongId() {
        assertThrows(BookNotExistsException.class, () -> bookDaoJdbc.findById(NON_EXISTING_BOOK_ID));
    }

    @Test
    @DisplayName("получить полный список книг")
    void shouldGetGenreList() {
        List<Book> bookList = bookDaoJdbc.findAll();

        assertEquals(2, bookList.size());
        assertEquals(EXISTING_BOOK_ID_1, bookList.get(0).getId());
        assertEquals(EXISTING_BOOK_NAME_1, bookList.get(0).getName());
        assertEquals(EXISTING_BOOK_AUTHOR_ID_1, bookList.get(0).getAuthor().getId());
        assertEquals(EXISTING_BOOK_AUTHOR_NAME_1, bookList.get(0).getAuthor().getName());
        assertEquals(EXISTING_BOOK_GENRE_ID_1, bookList.get(0).getGenre().getId());
        assertEquals(EXISTING_BOOK_GENRE_NAME_1, bookList.get(0).getGenre().getName());
        assertEquals(EXISTING_BOOK_ID_2, bookList.get(1).getId());
        assertEquals(EXISTING_BOOK_NAME_2, bookList.get(1).getName());
        assertEquals(EXISTING_BOOK_AUTHOR_ID_2, bookList.get(1).getAuthor().getId());
        assertEquals(EXISTING_BOOK_AUTHOR_NAME_2, bookList.get(1).getAuthor().getName());
        assertEquals(EXISTING_BOOK_GENRE_ID_2, bookList.get(1).getGenre().getId());
        assertEquals(EXISTING_BOOK_GENRE_NAME_2, bookList.get(1).getGenre().getName());
    }
}
