package ru.otus.homework10.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@DisplayName("Dto книги должен")
public class ResponseBookDtoTest {
    @Test
    @DisplayName("корректно создаться конструктором")
    void shouldHaveCorrectConstructor() {
        final ResponseBookDto responseBookDto = new ResponseBookDto(1L, "TestBook", new ResponseAuthorDto(1L, "TestAuthor"), new ResponseGenreDto(1L, "TestGenre"), Collections.singletonList(new ResponseCommentDto(1L, 1L, "TestBook", "TestComment")));

        assertEquals(1L, responseBookDto.getId());
        assertEquals("TestBook", responseBookDto.getName());
        assertEquals(1L, responseBookDto.getAuthorDto().getId());
        assertEquals("TestAuthor", responseBookDto.getAuthorDto().getName());
        assertEquals(1L, responseBookDto.getGenreDto().getId());
        assertEquals("TestGenre", responseBookDto.getGenreDto().getName());
        assertEquals(1, responseBookDto.getCommentDtoList().size());
        assertEquals(1L, responseBookDto.getCommentDtoList().get(0).getId());
        assertEquals(1L, responseBookDto.getCommentDtoList().get(0).getBookId());
        assertEquals("TestBook", responseBookDto.getCommentDtoList().get(0).getBookName());
        assertEquals("TestComment", responseBookDto.getCommentDtoList().get(0).getText());
    }
}
