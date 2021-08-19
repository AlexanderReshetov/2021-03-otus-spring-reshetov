package ru.otus.homework8.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework8.model.Author;
import ru.otus.homework8.model.Book;
import ru.otus.homework8.model.Genre;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис запуска сервиса операций с книгами должен")
public class BookRunnerServiceImplTest {
    @Mock
    private BookService bookService;
    @Mock
    private PrintService printService;
    @InjectMocks
    private BookRunnerServiceImpl bookRunnerService;

    @Test
    @DisplayName("запустить добавление книги")
    void shouldRunInsertBook() {
        when(bookService.insert("TestBook", 2L, 3L))
                .thenReturn(new Book(3L, "TestBook", new Author(2L, "TestAuthor2"), new Genre(3L, "TestGenre3")));
        bookRunnerService.insert("TestBook", 2L, 3L);

        verify(printService).println("New id = 3");
    }

    @Test
    @DisplayName("запустить обновление книги")
    void shouldRunUpdateBook() {
        bookRunnerService.update(1L, "TestBook", 2L, 3L);

        verify(bookService).update(1L, "TestBook", 2L, 3L);
    }

    @Test
    @DisplayName("запустить удаление книги")
    void shouldRunDeleteBook() {
        bookRunnerService.delete(1L);

        verify(bookService).delete(1L);
    }

    @Test
    @DisplayName("запустить вывод списка всех книг")
    void shouldRunPrintAllBooks() {
        bookRunnerService.printAll();

        verify(bookService).getAll();
    }

    @Test
    @DisplayName("запустить вывод информации о книге по ИД")
    void shouldRunPrintBookById() {
        when(bookService.getById(1L))
                .thenReturn(new Book(1L, "TestBook1", new Author(1L, "TestAuthor1"), new Genre(1L, "TestGenre1")));
        bookRunnerService.printById(1L);

        verify(printService).println("Id = 1");
        verify(printService).println("Name = TestBook1");
        verify(printService).println("Author id = 1");
        verify(printService).println("Author name = TestAuthor1");
        verify(printService).println("Genre id = 1");
        verify(printService).println("Genre name = TestGenre1");
    }
}
