package ru.otus.homework7.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework7.repository.exception.BookNotExistsException;
import ru.otus.homework7.model.Author;
import ru.otus.homework7.model.Book;
import ru.otus.homework7.model.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Import(BookDaoJpaImpl.class)
@DataJpaTest
@DisplayName("Dao книг должен")
public class BookDaoJpaImplTest {
    private static final Long EXISTING_BOOK_ID_1 = 1L;
    private static final String EXISTING_BOOK_NAME_1 = "TestBook1";
    private static final Long EXISTING_BOOK_AUTHOR_ID_1 = 1L;
    private static final String EXISTING_BOOK_AUTHOR_NAME_1 = "TestAuthor1";
    private static final Long EXISTING_BOOK_GENRE_ID_1 = 1L;
    private static final String EXISTING_BOOK_GENRE_NAME_1 = "TestGenre1";
    private static final Long EXISTING_BOOK_COMMENT_1_ID_1 = 1L;
    private static final String EXISTING_BOOK_COMMENT_1_TEXT_1 = "TestComment1ForBook1";
    private static final Long EXISTING_BOOK_ID_2 = 2L;
    private static final String EXISTING_BOOK_NAME_2 = "TestBook2";
    private static final Long EXISTING_BOOK_AUTHOR_ID_2 = 1L;
    private static final String EXISTING_BOOK_AUTHOR_NAME_2 = "TestAuthor1";
    private static final Long EXISTING_BOOK_GENRE_ID_2 = 2L;
    private static final String EXISTING_BOOK_GENRE_NAME_2 = "TestGenre2";
    private static final Long EXISTING_BOOK_COMMENT_1_ID_2 = 2L;
    private static final String EXISTING_BOOK_COMMENT_1_TEXT_2 = "TestComment1ForBook2";
    private static final Long EXISTING_BOOK_COMMENT_2_ID_2 = 3L;
    private static final String EXISTING_BOOK_COMMENT_2_TEXT_2 = "TestComment2ForBook2";
    private static final Long NON_EXISTING_BOOK_ID = 3L;
    private static final String NON_EXISTING_BOOK_NAME = "Non-existing book";

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private BookDaoJpa bookDaoJpa;

    @Test
    @DisplayName("добавить книгу")
    void shouldInsertBook() {
        Long newBookId = bookDaoJpa.insert(new Book(NON_EXISTING_BOOK_NAME, new Author(EXISTING_BOOK_AUTHOR_ID_1, EXISTING_BOOK_AUTHOR_NAME_1), new Genre(EXISTING_BOOK_GENRE_ID_1, EXISTING_BOOK_GENRE_NAME_1)));
        Book book = testEntityManager.find(Book.class, newBookId);

        assertEquals(NON_EXISTING_BOOK_NAME, book.getName());
        assertEquals(EXISTING_BOOK_AUTHOR_ID_1, book.getAuthor().getId());
        assertEquals(EXISTING_BOOK_AUTHOR_NAME_1, book.getAuthor().getName());
        assertEquals(EXISTING_BOOK_GENRE_ID_1, book.getGenre().getId());
        assertEquals(EXISTING_BOOK_GENRE_NAME_1, book.getGenre().getName());
    }

    @Test
    @DisplayName("обновить информацию о книге")
    void shouldUpdateBook() {
        bookDaoJpa.update(new Book(EXISTING_BOOK_ID_1, NON_EXISTING_BOOK_NAME, new Author(EXISTING_BOOK_AUTHOR_ID_1, EXISTING_BOOK_AUTHOR_NAME_1), new Genre(EXISTING_BOOK_GENRE_ID_2, EXISTING_BOOK_GENRE_NAME_2)));
        Book book = testEntityManager.find(Book.class, EXISTING_BOOK_ID_1);

        assertEquals(NON_EXISTING_BOOK_NAME, book.getName());
        assertEquals(EXISTING_BOOK_AUTHOR_ID_1, book.getAuthor().getId());
        assertEquals(EXISTING_BOOK_AUTHOR_NAME_1, book.getAuthor().getName());
        assertEquals(EXISTING_BOOK_GENRE_ID_2, book.getGenre().getId());
        assertEquals(EXISTING_BOOK_GENRE_NAME_2, book.getGenre().getName());
    }

    @Test
    @DisplayName("удалить книгу")
    void shouldDeleteBook() {
        bookDaoJpa.delete(EXISTING_BOOK_ID_1);

        assertNull(testEntityManager.find(Book.class, EXISTING_BOOK_ID_1));
    }

    @Test
    @DisplayName("получить книгу по ИД")
    void shouldGetBookById() {
        Book entityManagerBook = testEntityManager.find(Book.class, EXISTING_BOOK_ID_1);
        Book book = bookDaoJpa.findById(EXISTING_BOOK_ID_1);

        assertEquals(entityManagerBook.getId(), book.getId());
        assertEquals(entityManagerBook.getName(), book.getName());
        assertEquals(entityManagerBook.getAuthor().getId(), book.getAuthor().getId());
        assertEquals(entityManagerBook.getAuthor().getName(), book.getAuthor().getName());
        assertEquals(entityManagerBook.getGenre().getId(), book.getGenre().getId());
        assertEquals(entityManagerBook.getGenre().getName(), book.getGenre().getName());
        assertEquals(entityManagerBook.getComments().size(), entityManagerBook.getComments().size());
        assertEquals(entityManagerBook.getComments().get(0).getId(), entityManagerBook.getComments().get(0).getId());
        assertEquals(entityManagerBook.getComments().get(0).getText(), entityManagerBook.getComments().get(0).getText());
    }

    @Test
    @DisplayName("вызвать исключение при получении книги по неверному ИД")
    void shouldThrowExceptionWhenWrongId() {
        assertThrows(BookNotExistsException.class, () -> bookDaoJpa.findById(NON_EXISTING_BOOK_ID));
    }

    @Test
    @DisplayName("получить полный список книг")
    void shouldGetGenreList() {
        List<Book> bookList = bookDaoJpa.findAll();

        assertEquals(2, bookList.size());
        assertEquals(EXISTING_BOOK_ID_1, bookList.get(0).getId());
        assertEquals(EXISTING_BOOK_NAME_1, bookList.get(0).getName());
        assertEquals(EXISTING_BOOK_AUTHOR_ID_1, bookList.get(0).getAuthor().getId());
        assertEquals(EXISTING_BOOK_AUTHOR_NAME_1, bookList.get(0).getAuthor().getName());
        assertEquals(EXISTING_BOOK_GENRE_ID_1, bookList.get(0).getGenre().getId());
        assertEquals(EXISTING_BOOK_GENRE_NAME_1, bookList.get(0).getGenre().getName());
        assertEquals(EXISTING_BOOK_COMMENT_1_ID_1, bookList.get(0).getComments().get(0).getId());
        assertEquals(EXISTING_BOOK_COMMENT_1_TEXT_1, bookList.get(0).getComments().get(0).getText());
        assertEquals(EXISTING_BOOK_ID_2, bookList.get(1).getId());
        assertEquals(EXISTING_BOOK_NAME_2, bookList.get(1).getName());
        assertEquals(EXISTING_BOOK_AUTHOR_ID_2, bookList.get(1).getAuthor().getId());
        assertEquals(EXISTING_BOOK_AUTHOR_NAME_2, bookList.get(1).getAuthor().getName());
        assertEquals(EXISTING_BOOK_GENRE_ID_2, bookList.get(1).getGenre().getId());
        assertEquals(EXISTING_BOOK_GENRE_NAME_2, bookList.get(1).getGenre().getName());
        assertEquals(EXISTING_BOOK_COMMENT_1_ID_2, bookList.get(1).getComments().get(0).getId());
        assertEquals(EXISTING_BOOK_COMMENT_1_TEXT_2, bookList.get(1).getComments().get(0).getText());
        assertEquals(EXISTING_BOOK_COMMENT_2_ID_2, bookList.get(1).getComments().get(1).getId());
        assertEquals(EXISTING_BOOK_COMMENT_2_TEXT_2, bookList.get(1).getComments().get(1).getText());
    }
}
