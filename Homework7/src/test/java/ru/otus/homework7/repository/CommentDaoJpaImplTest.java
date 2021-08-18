package ru.otus.homework7.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework7.model.Author;
import ru.otus.homework7.model.Book;
import ru.otus.homework7.model.Comment;
import ru.otus.homework7.model.Genre;
import ru.otus.homework7.repository.exception.CommentNotExistsException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Import(CommentDaoJpaImpl.class)
@DataJpaTest
@DisplayName("Dao комментариев должен")
public class CommentDaoJpaImplTest {
    private static final Long EXISTING_BOOK_ID_1 = 1L;
    private static final String EXISTING_BOOK_NAME_1 = "TestBook1";
    private static final Long EXISTING_BOOK_AUTHOR_ID_1 = 1L;
    private static final String EXISTING_BOOK_AUTHOR_NAME_1 = "TestAuthor1";
    private static final Long EXISTING_BOOK_GENRE_ID_1 = 1L;
    private static final String EXISTING_BOOK_GENRE_NAME_1 = "TestGenre1";
    private static final Long EXISTING_BOOK_COMMENT_1_ID_1 = 1L;
    private static final String EXISTING_BOOK_COMMENT_1_TEXT_1 = "TestComment1ForBook1";
    private static final Long EXISTING_BOOK_ID_2 = 2L;
    private static final Long EXISTING_BOOK_GENRE_ID_2 = 2L;
    private static final String EXISTING_BOOK_GENRE_NAME_2 = "TestGenre2";
    private static final Long EXISTING_BOOK_COMMENT_1_ID_2 = 2L;
    private static final String EXISTING_BOOK_COMMENT_1_TEXT_2 = "TestComment1ForBook2";
    private static final Long EXISTING_BOOK_COMMENT_2_ID_2 = 3L;
    private static final String EXISTING_BOOK_COMMENT_2_TEXT_2 = "TestComment2ForBook2";
    private static final Long NON_EXISTING_COMMENT_ID = 0L;
    private static final String NON_EXISTING_COMMENT_TEXT = "Non-existing comment";

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private CommentDaoJpa commentDaoJpa;

    @Test
    @DisplayName("добавить комментарий")
    void shouldInsertComment() {
        Long newCommentId = commentDaoJpa.insert(new Comment(new Book(EXISTING_BOOK_NAME_1, new Author(EXISTING_BOOK_AUTHOR_ID_1, EXISTING_BOOK_AUTHOR_NAME_1), new Genre(EXISTING_BOOK_GENRE_ID_1, EXISTING_BOOK_GENRE_NAME_1)), NON_EXISTING_COMMENT_TEXT));
        Comment comment = testEntityManager.find(Comment.class, newCommentId);

        assertEquals(NON_EXISTING_COMMENT_TEXT, comment.getText());
        assertEquals(EXISTING_BOOK_NAME_1, comment.getBook().getName());
        assertEquals(EXISTING_BOOK_AUTHOR_ID_1, comment.getBook().getAuthor().getId());
        assertEquals(EXISTING_BOOK_AUTHOR_NAME_1, comment.getBook().getAuthor().getName());
        assertEquals(EXISTING_BOOK_GENRE_ID_1, comment.getBook().getGenre().getId());
        assertEquals(EXISTING_BOOK_GENRE_NAME_1, comment.getBook().getGenre().getName());
    }

    @Test
    @DisplayName("редактировать комментарий")
    void shouldUpdateComment() {
        commentDaoJpa.update(new Comment(EXISTING_BOOK_COMMENT_1_ID_1, new Book(EXISTING_BOOK_ID_1, EXISTING_BOOK_NAME_1, new Author(EXISTING_BOOK_AUTHOR_ID_1, EXISTING_BOOK_AUTHOR_NAME_1), new Genre(EXISTING_BOOK_GENRE_ID_2, EXISTING_BOOK_GENRE_NAME_2)), NON_EXISTING_COMMENT_TEXT));
        Comment comment = testEntityManager.find(Comment.class, EXISTING_BOOK_COMMENT_1_ID_1);

        assertEquals(NON_EXISTING_COMMENT_TEXT, comment.getText());
        assertEquals(EXISTING_BOOK_ID_1, comment.getBook().getId());
        assertEquals(EXISTING_BOOK_NAME_1, comment.getBook().getName());
        assertEquals(EXISTING_BOOK_AUTHOR_ID_1, comment.getBook().getAuthor().getId());
        assertEquals(EXISTING_BOOK_AUTHOR_NAME_1, comment.getBook().getAuthor().getName());
        assertEquals(EXISTING_BOOK_GENRE_ID_2, comment.getBook().getGenre().getId());
        assertEquals(EXISTING_BOOK_GENRE_NAME_2, comment.getBook().getGenre().getName());
    }

    @Test
    @DisplayName("удалить комментарий")
    void shouldDeleteComment() {
        commentDaoJpa.delete(EXISTING_BOOK_COMMENT_1_ID_1);

        assertNull(testEntityManager.find(Comment.class, EXISTING_BOOK_COMMENT_1_ID_1));
    }

    @Test
    @DisplayName("получить комментарий по ИД")
    void shouldGetCommentById() {
        Comment entityManagerComment = testEntityManager.find(Comment.class, EXISTING_BOOK_COMMENT_1_ID_1);
        Comment comment = commentDaoJpa.findById(EXISTING_BOOK_COMMENT_1_ID_1);

        assertEquals(entityManagerComment.getId(), comment.getId());
        assertEquals(entityManagerComment.getText(), comment.getText());
        assertEquals(entityManagerComment.getBook().getId(), comment.getBook().getId());
        assertEquals(entityManagerComment.getBook().getName(), comment.getBook().getName());
        assertEquals(entityManagerComment.getBook().getAuthor().getId(), comment.getBook().getAuthor().getId());
        assertEquals(entityManagerComment.getBook().getAuthor().getName(), comment.getBook().getAuthor().getName());
        assertEquals(entityManagerComment.getBook().getGenre().getId(), comment.getBook().getGenre().getId());
        assertEquals(entityManagerComment.getBook().getGenre().getName(), comment.getBook().getGenre().getName());
    }

    @Test
    @DisplayName("вызвать исключение при получении комментария по неверному ИД")
    void shouldThrowExceptionWhenWrongId() {
        assertThrows(CommentNotExistsException.class, () -> commentDaoJpa.findById(NON_EXISTING_COMMENT_ID));
    }

    @Test
    @DisplayName("получить полный список комментариев")
    void shouldGetCommentList() {
        List<Comment> commentList = commentDaoJpa.findAll();

        assertEquals(3, commentList.size());
        assertEquals(EXISTING_BOOK_COMMENT_1_ID_1, commentList.get(0).getId());
        assertEquals(EXISTING_BOOK_COMMENT_1_TEXT_1, commentList.get(0).getText());
        assertEquals(EXISTING_BOOK_ID_1, commentList.get(0).getBook().getId());
        assertEquals(EXISTING_BOOK_COMMENT_1_ID_2, commentList.get(1).getId());
        assertEquals(EXISTING_BOOK_COMMENT_1_TEXT_2, commentList.get(1).getText());
        assertEquals(EXISTING_BOOK_ID_2, commentList.get(1).getBook().getId());
        assertEquals(EXISTING_BOOK_COMMENT_2_ID_2, commentList.get(2).getId());
        assertEquals(EXISTING_BOOK_COMMENT_2_TEXT_2, commentList.get(2).getText());
        assertEquals(EXISTING_BOOK_ID_2, commentList.get(2).getBook().getId());
    }
}
