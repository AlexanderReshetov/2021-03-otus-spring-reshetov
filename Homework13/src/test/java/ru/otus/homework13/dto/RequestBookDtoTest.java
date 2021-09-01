package ru.otus.homework13.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@DisplayName("Внешний Dto книги должен")
public class RequestBookDtoTest {
    @Test
    @DisplayName("корректно создаться конструктором")
    void shouldHaveCorrectConstructor() {
        final RequestBookDto requestBookDto = new RequestBookDto("TestBook", 2L, 3L);

        assertEquals("TestBook", requestBookDto.getName());
        assertEquals(2L, requestBookDto.getAuthorId());
        assertEquals(3L, requestBookDto.getGenreId());
    }
}
