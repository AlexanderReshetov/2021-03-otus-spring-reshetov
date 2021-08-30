package ru.otus.homework11.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@DisplayName("Dto автора должен")
public class ResponseAuthorDtoTest {
    @Test
    @DisplayName("корректно создаться конструктором")
    void shouldHaveCorrectConstructor() {
        final ResponseAuthorDto responseAuthorDto = new ResponseAuthorDto(1L, "TestAuthor");

        assertEquals(1L, responseAuthorDto.getId());
        assertEquals("TestAuthor", responseAuthorDto.getName());
    }
}
