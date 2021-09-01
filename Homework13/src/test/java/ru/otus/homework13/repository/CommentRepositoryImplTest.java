package ru.otus.homework13.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.homework13.domain.Author;
import ru.otus.homework13.domain.Book;
import ru.otus.homework13.domain.Comment;
import ru.otus.homework13.domain.Genre;
import ru.otus.homework13.service.exception.CommentNotExistsException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Dao комментариев должен")
public class CommentRepositoryImplTest {
    private static final Long EXISTING_BOOK_ID_1 = 1L;
    private static final String EXISTING_BOOK_NAME_1 = "TestBook1";
    private static final Long EXISTING_BOOK_AUTHOR_ID_1 = 1L;
    private static final String EXISTING_BOOK_AUTHOR_NAME_1 = "TestAuthor1";
    private static final Long EXISTING_BOOK_GENRE_ID_1 = 1L;
    private static final String EXISTING_BOOK_GENRE_NAME_1 = "TestGenre1";

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("добавить комментарий и получить его по ИД")
    void shouldAddCommentAndGetItById() {
        final Book book = new Book(
                EXISTING_BOOK_ID_1,
                EXISTING_BOOK_NAME_1,
                new Author(EXISTING_BOOK_AUTHOR_ID_1, EXISTING_BOOK_AUTHOR_NAME_1),
                new Genre(EXISTING_BOOK_GENRE_ID_1, EXISTING_BOOK_GENRE_NAME_1));
        final String newCommentText = "New Comment";
        Comment comment = new Comment(null, book, newCommentText);
        comment = commentRepository.save(comment);
        Long newId = comment.getId();
        comment = commentRepository.findById(comment.getId()).orElseThrow(() -> new CommentNotExistsException("There is no comment with id = " + newId));

        assertEquals(newCommentText, comment.getText());
        assertEquals(EXISTING_BOOK_ID_1, comment.getBook().getId());
        assertEquals(EXISTING_BOOK_NAME_1, comment.getBook().getName());
        assertEquals(EXISTING_BOOK_AUTHOR_ID_1, comment.getBook().getAuthor().getId());
        assertEquals(EXISTING_BOOK_AUTHOR_NAME_1, comment.getBook().getAuthor().getName());
        assertEquals(EXISTING_BOOK_GENRE_ID_1, comment.getBook().getGenre().getId());
        assertEquals(EXISTING_BOOK_GENRE_NAME_1, comment.getBook().getGenre().getName());
    }
}
