package ru.otus.homework6.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework6.dao.AuthorDao;
import ru.otus.homework6.dao.BookDao;
import ru.otus.homework6.dao.GenreDao;
import ru.otus.homework6.dao.exception.BookNotExistsException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис операций с книгами должен")
public class BookServiceImplTest {
    @Mock
    private BookDao bookDao;
    @Mock
    private AuthorDao authorDao;
    @Mock
    private GenreDao genreDao;
    @Mock
    private PrintService printService;
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("добавить книгу")
    void shouldInsertBook() {
        bookService.insert("TestBook", 2L, 3L);

        verify(authorDao).findById(2L);
        verify(genreDao).findById(3L);
        verify(bookDao).insert(any());
    }

    @Test
    @DisplayName("обновить книгу")
    void shouldUpdateBook() {
        bookService.update(1L, "TestBook", 2L, 3L);

        verify(authorDao).findById(2L);
        verify(genreDao).findById(3L);
        verify(bookDao).update(any());
    }

    @Test
    @DisplayName("удалить книгу")
    void shouldDeleteBook() {
        bookService.delete(1L);

        verify(bookDao).delete(1L);
    }

    @Test
    @DisplayName("вывести список всех книг")
    void shouldPrintAllBooks() {
        bookService.printAll();

        verify(bookDao).findAll();
    }

    @Test
    @DisplayName("вывести информацию о книге по ИД")
    void shouldPrintBookById() {
        when(bookDao.findById(1L)).thenThrow(BookNotExistsException.class);

        try {
            bookService.printById(1L);
        }
        catch (BookNotExistsException e) {
        }

        verify(bookDao).findById(1L);
    }
}
