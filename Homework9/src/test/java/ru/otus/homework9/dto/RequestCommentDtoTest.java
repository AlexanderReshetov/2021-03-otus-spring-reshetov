package ru.otus.homework9.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@DisplayName("Внешний Dto комментария должен")
public class RequestCommentDtoTest {
    @Test
    @DisplayName("корректно создаться конструктором")
    void shouldHaveCorrectConstructor() {
        final RequestCommentDto requestCommentDto = new RequestCommentDto(2L, "TestComment");

        assertEquals(2L, requestCommentDto.getBookId());
        assertEquals("TestComment", requestCommentDto.getText());
    }
}
