package ru.otus.homework9.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

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
}
