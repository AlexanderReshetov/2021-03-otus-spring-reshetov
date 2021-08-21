package ru.otus.homework10.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework10.domain.Author;
import ru.otus.homework10.domain.Book;
import ru.otus.homework10.domain.Comment;
import ru.otus.homework10.domain.Genre;
import ru.otus.homework10.dto.RequestCommentDto;
import ru.otus.homework10.dto.ResponseCommentDto;
import ru.otus.homework10.repository.BookRepository;
import ru.otus.homework10.repository.CommentRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис операций с комментариями должен")
public class CommentServiceImplTest {
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private CommentServiceImpl commentService;

    private static final Long TEST_COMMENT_ID = 4L;
    private static final String TEST_COMMENT_TEXT = "TestComment";
    private static final String UPD_COMMENT_TEXT = "UpdComment";
    private static final Long TEST_BOOK_ID = 1L;
    private static final String TEST_BOOK_NAME = "TestBook1";
    private static final Long TEST_AUTHOR_ID = 1L;
    private static final String TEST_AUTHOR_NAME = "TestAuthor1";
    private static final Long TEST_GENRE_ID = 1L;
    private static final String TEST_GENRE_NAME = "TestGenre1";

    @Test
    @DisplayName("добавить комментарий")
    void shouldInsertComment() {
        final Book book = new Book(TEST_BOOK_ID, TEST_BOOK_NAME, new Author(TEST_AUTHOR_ID, TEST_AUTHOR_NAME), new Genre(TEST_GENRE_ID, TEST_GENRE_NAME));
        final Comment comment = new Comment(TEST_COMMENT_ID, book, TEST_COMMENT_TEXT);
        when(bookRepository.findById(comment.getBook().getId())).thenReturn(Optional.of(book));
        when(commentRepository.save(any())).thenReturn(comment);

        final ResponseCommentDto newResponseCommentDto = commentService.insert(new RequestCommentDto(comment.getBook().getId(), comment.getText()));

        assertEquals(comment.getId(), newResponseCommentDto.getId());
        assertEquals(comment.getBook().getId(), newResponseCommentDto.getBookId());
        assertEquals(comment.getBook().getName(), newResponseCommentDto.getBookName());
        assertEquals(comment.getText(), newResponseCommentDto.getText());
    }

    @Test
    @DisplayName("редактировать комментарий")
    void shouldUpdateComment() {
        final Book book = new Book(TEST_BOOK_ID, TEST_BOOK_NAME, new Author(TEST_AUTHOR_ID, TEST_AUTHOR_NAME), new Genre(TEST_GENRE_ID, TEST_GENRE_NAME));
        final Comment comment = new Comment(TEST_COMMENT_ID, book, UPD_COMMENT_TEXT);
        when(bookRepository.findById(TEST_BOOK_ID)).thenReturn(Optional.of(book));
        when(commentRepository.save(any())).thenReturn(comment);

        final ResponseCommentDto updatedResponseCommentDto = commentService.update(comment.getId(), new RequestCommentDto(comment.getBook().getId(), comment.getText()));

        assertEquals(comment.getId(), updatedResponseCommentDto.getId());
        assertEquals(comment.getBook().getId(), updatedResponseCommentDto.getBookId());
        assertEquals(comment.getBook().getName(), updatedResponseCommentDto.getBookName());
        assertEquals(comment.getText(), updatedResponseCommentDto.getText());
    }

    @Test
    @DisplayName("удалить комментарий")
    void shouldDeleteComment() {
        final Book book = new Book(1L, "TestBook1", new Author(1L, "TestAuthor1"), new Genre(1L, "TestGenre1"));
        final Comment comment = new Comment(book, "TestComment");
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        commentService.delete(1L);

        verify(commentRepository).delete(any());
    }

    @Test
    @DisplayName("получить список всех комментариев")
    void shouldGetAllComments() {
        commentService.getAll();

        verify(commentRepository).findAll();
    }

    @Test
    @DisplayName("получить информацию о комментарии по ИД")
    void shouldGetCommentById() {
        final Book book = new Book(1L, "TestBook1", new Author(1L, "TestAuthor1"), new Genre(1L, "TestGenre1"));
        final Comment comment = new Comment(book, "TestComment");
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        final ResponseCommentDto newResponseCommentDto = commentService.getById(1L);

        assertEquals(comment.getId(), newResponseCommentDto.getId());
        assertEquals(comment.getBook().getId(), newResponseCommentDto.getBookId());
        assertEquals(comment.getBook().getName(), newResponseCommentDto.getBookName());
        assertEquals(comment.getText(), newResponseCommentDto.getText());
    }
}
