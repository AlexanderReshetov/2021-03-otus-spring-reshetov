package ru.otus.library.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@DisplayName("Dto комментария должен")
public class ResponseCommentDtoTest {
    @Test
    @DisplayName("корректно создаться конструктором")
    void shouldHaveCorrectConstructor() {
        final ResponseCommentDto responseCommentDto = new ResponseCommentDto(1L, 1L, "TestBook", "TestComment");

        assertEquals(1L, responseCommentDto.getId());
        assertEquals("TestComment", responseCommentDto.getText());
        assertEquals(1L, responseCommentDto.getBookId());
        assertEquals("TestBook", responseCommentDto.getBookName());
    }

    @Test
    @DisplayName("корректно создаться из доменного объекта")
    void shouldHaveCorrectToDtoMethod() {
        final Book book = new Book(1L, "TestBook", new Author(1L, "TestAuthor"), new Genre(1L, "TestGenre"));
        final ResponseCommentDto responseCommentDto = ResponseCommentDto.toDto(new Comment(1L, book, "TestComment"));

        assertEquals(1L, responseCommentDto.getId());
        assertEquals("TestComment", responseCommentDto.getText());
        assertEquals(1L, responseCommentDto.getBookId());
        assertEquals("TestBook", responseCommentDto.getBookName());
    }
}