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
import ru.otus.homework8.repository.AuthorRepository;
import ru.otus.homework8.repository.BookRepository;
import ru.otus.homework8.repository.GenreRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис операций с книгами должен")
public class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private GenreRepository genreRepository;
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("добавить книгу")
    void shouldInsertBook() {
        final Author author = new Author(2L, "TestAuthor2");
        final Genre genre = new Genre(3L, "TestGenre3");
        final Book book = new Book(3L, "TestBook", author, genre);
        when(authorRepository.findById(2L)).thenReturn(Optional.of(author));
        when(genreRepository.findById(3L)).thenReturn(Optional.of(genre));
        when(bookRepository.save(any())).thenReturn(book);

        final Book newBook = bookService.insert("TestBook", 2L, 3L);

        assertEquals(book.getId(), newBook.getId());
        assertEquals(book.getName(), newBook.getName());
        assertEquals(book.getAuthor().getId(), newBook.getAuthor().getId());
        assertEquals(book.getAuthor().getName(), newBook.getAuthor().getName());
        assertEquals(book.getGenre().getId(), newBook.getGenre().getId());
        assertEquals(book.getGenre().getName(), newBook.getGenre().getName());
    }

    @Test
    @DisplayName("обновить книгу")
    void shouldUpdateBook() {
        final Author author = new Author(2L, "TestAuthor2");
        final Genre genre = new Genre(3L, "TestGenre3");
        when(authorRepository.findById(2L)).thenReturn(Optional.of(author));
        when(genreRepository.findById(3L)).thenReturn(Optional.of(genre));

        bookService.update(1L, "TestBook", 2L, 3L);

        verify(bookRepository).save(any());
    }

    @Test
    @DisplayName("удалить книгу")
    void shouldDeleteBook() {
        final Author author = new Author(1L, "TestAuthor1");
        final Genre genre = new Genre(1L, "TestGenre1");
        final Book book = new Book(1L, "TestBook1", author, genre);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.delete(1L);

        verify(bookRepository).delete(any());
    }

    @Test
    @DisplayName("вывести список всех книг")
    void shouldPrintAllBooks() {
        bookService.getAll();

        verify(bookRepository).findAll();
    }

    @Test
    @DisplayName("вывести информацию о книге по ИД")
    void shouldPrintBookById() {
        final Book book = new Book(1L, "TestBook1", new Author(1L, "TestAuthor1"), new Genre(1L, "TestGenre1"));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        final Book newBook = bookService.getById(1L);

        assertEquals(book.getId(), newBook.getId());
        assertEquals(book.getName(), newBook.getName());
        assertEquals(book.getAuthor().getId(), newBook.getAuthor().getId());
        assertEquals(book.getAuthor().getName(), newBook.getAuthor().getName());
        assertEquals(book.getGenre().getId(), newBook.getGenre().getId());
        assertEquals(book.getGenre().getName(), newBook.getGenre().getName());
    }
}
