package ru.otus.homework7.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework7.service.BookService;
import ru.otus.homework7.service.CommentService;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Spring Shell команды должны")
public class ShellCommandsTest {
    @Mock
    private BookService bookService;
    @Mock
    private CommentService commentService;
    @InjectMocks
    private ShellCommands shellCommands;

    @Test
    @DisplayName("добавить новую книгу")
    void shouldInsertBook() {
        shellCommands.insert("Book", 2L, 3L);

        verify(bookService).insert("Book", 2L, 3L);
    }

    @Test
    @DisplayName("обновить информацию по существующей книге")
    void shouldUpdateBook() {
        shellCommands.update(1L, "Book", 2L, 3L);

        verify(bookService).update(1L, "Book", 2L, 3L);
    }

    @Test
    @DisplayName("удалить книгу")
    void shouldDeleteBook() {
        shellCommands.deleteById(1L);

        verify(bookService).delete(1L);
    }

    @Test
    @DisplayName("отобразить список всех книг")
    void shouldShowAllBooks() {
        shellCommands.showAll();

        verify(bookService).printAll();
    }

    @Test
    @DisplayName("отобразить информацию о книге по ИД")
    void shouldShowBookById() {
        shellCommands.showById(1L);

        verify(bookService).printById(1L);
    }

    @Test
    @DisplayName("добавить комментарий")
    void shouldAddComment() {
        shellCommands.addComment(1L, "NewComment");

        verify(commentService).insert(1L, "NewComment");
    }

    @Test
    @DisplayName("редактировать комментарий")
    void shouldEditComment() {
        shellCommands.editComment(1L, 2L, "TestComment");

        verify(commentService).update(1L, 2L, "TestComment");
    }

    @Test
    @DisplayName("удалить комментарий")
    void shouldDeleteComment() {
        shellCommands.removeComment(1L);

        verify(commentService).delete(1L);
    }

    @Test
    @DisplayName("отобразить список всех комментариев")
    void shouldShowAllComments() {
        shellCommands.showAllComments();

        verify(commentService).printAll();
    }

    @Test
    @DisplayName("отобразить информацию о комментарии по ИД")
    void shouldShowCommentById() {
        shellCommands.showCommentById(1L);

        verify(commentService).printById(1L);
    }
}
