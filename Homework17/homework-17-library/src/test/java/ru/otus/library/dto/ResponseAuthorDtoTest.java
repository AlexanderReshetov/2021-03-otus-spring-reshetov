package ru.otus.library.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.library.domain.Author;

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

    @Test
    @DisplayName("корректно создаться из доменного объекта")
    void shouldHaveCorrectToDtoMethod() {
        final ResponseAuthorDto responseAuthorDto = ResponseAuthorDto.toDto(new Author(1L, "TestAuthor"));

        assertEquals(1L, responseAuthorDto.getId());
        assertEquals("TestAuthor", responseAuthorDto.getName());
    }

    @Test
    @DisplayName("корректно создать доменный объект")
    void shouldHaveCorrectToDomainMethod() {
        final Author author = ResponseAuthorDto.toDomain(new ResponseAuthorDto(1L, "TestAuthor"));

        assertEquals(1L, author.getId());
        assertEquals("TestAuthor", author.getName());
    }
}
