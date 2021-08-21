package ru.otus.homework9.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework9.domain.Comment;
import ru.otus.homework9.dto.*;
import ru.otus.homework9.domain.Author;
import ru.otus.homework9.domain.Book;
import ru.otus.homework9.domain.Genre;
import ru.otus.homework9.repository.AuthorRepository;
import ru.otus.homework9.repository.BookRepository;
import ru.otus.homework9.repository.GenreRepository;

import java.util.Collections;
import java.util.List;
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

        final ResponseBookDto newResponseBookDto = bookService.insert(new RequestBookDto("TestBook", 2L, 3L));

        assertEquals(book.getId(), newResponseBookDto.getId());
        assertEquals(book.getName(), newResponseBookDto.getName());
        assertEquals(book.getAuthor().getId(), newResponseBookDto.getAuthorDto().getId());
        assertEquals(book.getAuthor().getName(), newResponseBookDto.getAuthorDto().getName());
        assertEquals(book.getGenre().getId(), newResponseBookDto.getGenreDto().getId());
        assertEquals(book.getGenre().getName(), newResponseBookDto.getGenreDto().getName());
    }

    @Test
    @DisplayName("обновить книгу")
    void shouldUpdateBook() {
        final Author author = new Author(2L, "TestAuthor2");
        final Genre genre = new Genre(3L, "TestGenre3");
        final Book book = new Book(3L, "UpdBook", author, genre);
        when(authorRepository.findById(2L)).thenReturn(Optional.of(author));
        when(genreRepository.findById(3L)).thenReturn(Optional.of(genre));
        when(bookRepository.save(any())).thenReturn(book);

        ResponseBookDto updatedResponseBookDto = bookService.update(book.getId(), new RequestBookDto(book.getName(), book.getAuthor().getId(), book.getGenre().getId()));

        verify(bookRepository).save(any());
        assertEquals(book.getId(), updatedResponseBookDto.getId());
        assertEquals(book.getName(), updatedResponseBookDto.getName());
        assertEquals(book.getAuthor().getId(), updatedResponseBookDto.getAuthorDto().getId());
        assertEquals(book.getAuthor().getName(), updatedResponseBookDto.getAuthorDto().getName());
        assertEquals(book.getGenre().getId(), updatedResponseBookDto.getGenreDto().getId());
        assertEquals(book.getGenre().getName(), updatedResponseBookDto.getGenreDto().getName());
    }

    @Test
    @DisplayName("удалить книгу")
    void shouldDeleteBook() {
        final Author author = new Author(1L, "TestAuthor1");
        final Genre genre = new Genre(1L, "TestGenre1");
        final Book book = new Book(1L, "TestBook1", author, genre);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Long deletedId = bookService.delete(1L);

        verify(bookRepository).delete(any());
        assertEquals(1L, deletedId);
    }

    @Test
    @DisplayName("получить список всех книг")
    void shouldGetAllBooks() {
        bookService.getAll();

        verify(bookRepository).findAll();
    }

    @Test
    @DisplayName("получить информацию о книге по ИД")
    void shouldGetBookById() {
        final Book book = new Book(1L, "TestBook1", new Author(1L, "TestAuthor1"), new Genre(1L, "TestGenre1"));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        final ResponseBookDto newResponseBookDto = bookService.getById(1L);

        assertEquals(book.getId(), newResponseBookDto.getId());
        assertEquals(book.getName(), newResponseBookDto.getName());
        assertEquals(book.getAuthor().getId(), newResponseBookDto.getAuthorDto().getId());
        assertEquals(book.getAuthor().getName(), newResponseBookDto.getAuthorDto().getName());
        assertEquals(book.getGenre().getId(), newResponseBookDto.getGenreDto().getId());
        assertEquals(book.getGenre().getName(), newResponseBookDto.getGenreDto().getName());
    }

    @Test
    @DisplayName("получить список комментариев к книге по ее ИД")
    void shouldGetCommentsForBookById() {
        final Book book = new Book(1L, "TestBook1", new Author(1L, "TestAuthor1"), new Genre(1L, "TestGenre1"));
        book.setComments(Collections.singletonList(new Comment(1L, book, "TestComment")));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        final List<ResponseCommentDto> responseCommentDtoList = bookService.getCommentsById(1L);

        assertEquals(1, responseCommentDtoList.size());
        assertEquals(1L, responseCommentDtoList.get(0).getId());
        assertEquals("TestComment", responseCommentDtoList.get(0).getText());
        assertEquals(1L, responseCommentDtoList.get(0).getBookId());
        assertEquals("TestBook1", responseCommentDtoList.get(0).getBookName());
    }
}