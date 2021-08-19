package ru.otus.homework8.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework8.model.Author;
import ru.otus.homework8.model.Book;
import ru.otus.homework8.model.Comment;
import ru.otus.homework8.model.Genre;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис запуска сервиса операций с комментариями должен")
public class CommentRunnerServiceImplTest {
    @Mock
    private CommentService commentService;
    @Mock
    private PrintService printService;
    @InjectMocks
    private CommentRunnerServiceImpl commentRunnerService;

    private static final Long TEST_COMMENT_ID = 4L;
    private static final String TEST_COMMENT_TEXT = "TestComment";
    private static final Long TEST_BOOK_ID = 1L;
    private static final String TEST_BOOK_NAME = "TestBook1";
    private static final Long TEST_AUTHOR_ID = 1L;
    private static final String TEST_AUTHOR_NAME = "TestAuthor1";
    private static final Long TEST_GENRE_ID = 1L;
    private static final String TEST_GENRE_NAME = "TestGenre1";

    @Test
    @DisplayName("запустить добавление комментария")
    void shouldRunInsertComment() {
        final Book book = new Book(TEST_BOOK_ID, TEST_BOOK_NAME, new Author(TEST_AUTHOR_ID, TEST_AUTHOR_NAME), new Genre(TEST_GENRE_ID, TEST_GENRE_NAME));
        final Comment comment = new Comment(TEST_COMMENT_ID, book, TEST_COMMENT_TEXT);
        when(commentService.insert(TEST_BOOK_ID, TEST_COMMENT_TEXT)).thenReturn(comment);

        commentRunnerService.insert(TEST_BOOK_ID, TEST_COMMENT_TEXT);

        verify(printService).println("New id = " + TEST_COMMENT_ID);
    }

    @Test
    @DisplayName("запустить редактирование комментария")
    void shouldRunUpdateComment() {
        commentRunnerService.update(TEST_COMMENT_ID, TEST_BOOK_ID,"NewComment");

        verify(commentService).update(TEST_COMMENT_ID, TEST_BOOK_ID,"NewComment");
    }

    @Test
    @DisplayName("запустить удаление комментария")
    void shouldRunDeleteComment() {
        commentRunnerService.delete(TEST_COMMENT_ID);

        verify(commentService).delete(TEST_COMMENT_ID);
    }

    @Test
    @DisplayName("запустить вывод списка всех комментариев")
    void shouldRunPrintAllComments() {
        commentRunnerService.printAll();

        verify(commentService).getAll();
    }

    @Test
    @DisplayName("вывести информацию о комментарии по ИД")
    void shouldRunPrintCommentById() {
        final Book book = new Book(TEST_BOOK_ID, TEST_BOOK_NAME, new Author(TEST_AUTHOR_ID, TEST_AUTHOR_NAME), new Genre(TEST_GENRE_ID, TEST_GENRE_NAME));
        final Comment comment = new Comment(TEST_COMMENT_ID, book, TEST_COMMENT_TEXT);
        when(commentService.getById(TEST_COMMENT_ID)).thenReturn(comment);

        commentRunnerService.printById(TEST_COMMENT_ID);

        verify(printService).println("Id = " + comment.getId());
        verify(printService).println("Book id = " + comment.getBook().getId());
        verify(printService).println("Book name = " + comment.getBook().getName());
        verify(printService).println("Text = " + comment.getText());
    }
}
