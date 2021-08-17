package ru.otus.homework7.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework7.model.Author;
import ru.otus.homework7.model.Book;
import ru.otus.homework7.model.Comment;
import ru.otus.homework7.model.Genre;
import ru.otus.homework7.repository.BookDaoJpa;
import ru.otus.homework7.repository.CommentDaoJpa;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис операций с комментариями должен")
public class CommentServiceImplTest {
    @Mock
    private CommentDaoJpa commentDaoJpa;
    @Mock
    private BookDaoJpa bookDaoJpa;
    @Mock
    private PrintService printService;
    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    @DisplayName("добавить комментарий")
    void shouldInsertComment() {
        commentService.insert(1L, "TestComment");

        verify(bookDaoJpa).findById(1L);
        verify(commentDaoJpa).insert(any());
    }

    @Test
    @DisplayName("редактировать комментарий")
    void shouldUpdateComment() {
        commentService.update(1L, 1L,"TestComment");

        verify(bookDaoJpa).findById(1L);
        verify(commentDaoJpa).update(any());
    }

    @Test
    @DisplayName("удалить комментарий")
    void shouldDeleteComment() {
        commentService.delete(1L);

        verify(commentDaoJpa).delete(1L);
    }

    @Test
    @DisplayName("вывести список всех комментариев")
    void shouldPrintAllComments() {
        commentService.printAll();

        verify(commentDaoJpa).findAll();
    }

    @Test
    @DisplayName("вывести информацию о комментарии по ИД")
    void shouldPrintCommentById() {
        Book book = new Book(1L, "TestBook1", new Author(1L, "TestAuthor1"), new Genre(1L, "TestGenre1"));
        Comment comment = new Comment(book, "TestComment");
        when(commentDaoJpa.findById(1L)).thenReturn(comment);

        commentService.printById(1L);

        verify(commentDaoJpa).findById(1L);
        verify(printService).println("Id = " + comment.getId());
        verify(printService).println("Book id = " + comment.getBook().getId());
        verify(printService).println("Book name = " + comment.getBook().getName());
        verify(printService).println("Text = " + comment.getText());
    }
}
