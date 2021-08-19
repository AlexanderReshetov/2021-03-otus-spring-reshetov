package ru.otus.homework8.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework8.service.BookRunnerService;
import ru.otus.homework8.service.CommentRunnerService;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Spring Shell команды должны")
public class ShellCommandsTest {
    @Mock
    private BookRunnerService bookRunnerService;
    @Mock
    private CommentRunnerService commentRunnerService;
    @InjectMocks
    private ShellCommands shellCommands;

    @Test
    @DisplayName("добавить новую книгу")
    void shouldInsertBook() {
        shellCommands.insert("Book", 2L, 3L);

        verify(bookRunnerService).insert("Book", 2L, 3L);
    }

    @Test
    @DisplayName("обновить информацию по существующей книге")
    void shouldUpdateBook() {
        shellCommands.update(1L, "Book", 2L, 3L);

        verify(bookRunnerService).update(1L, "Book", 2L, 3L);
    }

    @Test
    @DisplayName("удалить книгу")
    void shouldDeleteBook() {
        shellCommands.deleteById(1L);

        verify(bookRunnerService).delete(1L);
    }

    @Test
    @DisplayName("отобразить список всех книг")
    void shouldShowAllBooks() {
        shellCommands.showAll();

        verify(bookRunnerService).printAll();
    }

    @Test
    @DisplayName("отобразить информацию о книге по ИД")
    void shouldShowBookById() {
        shellCommands.showById(1L);

        verify(bookRunnerService).printById(1L);
    }

    @Test
    @DisplayName("добавить комментарий")
    void shouldAddComment() {
        shellCommands.addComment(1L, "NewComment");

        verify(commentRunnerService).insert(1L, "NewComment");
    }

    @Test
    @DisplayName("редактировать комментарий")
    void shouldEditComment() {
        shellCommands.editComment(1L, 2L, "TestComment");

        verify(commentRunnerService).update(1L, 2L, "TestComment");
    }

    @Test
    @DisplayName("удалить комментарий")
    void shouldDeleteComment() {
        shellCommands.removeComment(1L);

        verify(commentRunnerService).delete(1L);
    }

    @Test
    @DisplayName("отобразить список всех комментариев")
    void shouldShowAllComments() {
        shellCommands.showAllComments();

        verify(commentRunnerService).printAll();
    }

    @Test
    @DisplayName("отобразить информацию о комментарии по ИД")
    void shouldShowCommentById() {
        shellCommands.showCommentById(1L);

        verify(commentRunnerService).printById(1L);
    }
}
