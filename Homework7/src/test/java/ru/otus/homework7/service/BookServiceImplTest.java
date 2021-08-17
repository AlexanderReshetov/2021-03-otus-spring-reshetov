package ru.otus.homework7.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework7.model.Author;
import ru.otus.homework7.model.Book;
import ru.otus.homework7.model.Genre;
import ru.otus.homework7.repository.AuthorDaoJpa;
import ru.otus.homework7.repository.BookDaoJpa;
import ru.otus.homework7.repository.GenreDaoJpa;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис операций с книгами должен")
public class BookServiceImplTest {
    @Mock
    private BookDaoJpa bookDaoJpa;
    @Mock
    private AuthorDaoJpa authorDaoJpa;
    @Mock
    private GenreDaoJpa genreDaoJpa;
    @Mock
    private PrintService printService;
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("добавить книгу")
    void shouldInsertBook() {
        bookService.insert("TestBook", 2L, 3L);

        verify(authorDaoJpa).findById(2L);
        verify(genreDaoJpa).findById(3L);
        verify(bookDaoJpa).insert(any());
    }

    @Test
    @DisplayName("обновить книгу")
    void shouldUpdateBook() {
        bookService.update(1L, "TestBook", 2L, 3L);

        verify(authorDaoJpa).findById(2L);
        verify(genreDaoJpa).findById(3L);
        verify(bookDaoJpa).update(any());
    }

    @Test
    @DisplayName("удалить книгу")
    void shouldDeleteBook() {
        bookService.delete(1L);

        verify(bookDaoJpa).delete(1L);
    }

    @Test
    @DisplayName("вывести список всех книг")
    void shouldPrintAllBooks() {
        bookService.printAll();

        verify(bookDaoJpa).findAll();
    }

    @Test
    @DisplayName("вывести информацию о книге по ИД")
    void shouldPrintBookById() {
        Book book = new Book(1L, "TestBook1", new Author(1L, "TestAuthor1"), new Genre(1L, "TestGenre1"));
        when(bookDaoJpa.findById(1L)).thenReturn(book);

        bookService.printById(1L);

        verify(bookDaoJpa).findById(1L);
        verify(printService).println("Id = " + book.getId());
        verify(printService).println("Name = " + book.getName());
        verify(printService).println("Author id = " + book.getAuthor().getId());
        verify(printService).println("Author name = " + book.getAuthor().getName());
        verify(printService).println("Genre id = " + book.getGenre().getId());
        verify(printService).println("Genre name = " + book.getGenre().getName());
    }
}
