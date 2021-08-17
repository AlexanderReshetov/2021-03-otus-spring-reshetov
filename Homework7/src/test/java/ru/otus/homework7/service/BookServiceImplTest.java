package ru.otus.homework7.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework7.repository.AuthorDaoJpa;
import ru.otus.homework7.repository.BookDaoJpa;
import ru.otus.homework7.repository.GenreDaoJpa;
import ru.otus.homework7.repository.exception.BookNotExistsException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        when(bookDaoJpa.findById(1L)).thenThrow(BookNotExistsException.class);

        try {
            bookService.printById(1L);
        }
        catch (BookNotExistsException e) {
        }

        verify(bookDaoJpa).findById(1L);
    }
}
