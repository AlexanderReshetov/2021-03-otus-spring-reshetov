package ru.otus.homework13.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework13.domain.Author;
import ru.otus.homework13.dto.ResponseAuthorDto;
import ru.otus.homework13.repository.AuthorRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис операций с авторами должен")
public class AuthorServiceImplTest {
    @Mock
    private AuthorRepository authorRepository;
    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    @DisplayName("получить список всех авторов")
    void shouldGetAllAuthors() {
        authorService.getAll();

        verify(authorRepository).findAll();
    }

    @Test
    @DisplayName("получить информацию об авторе по ИД")
    void shouldGetAuthorById() {
        final Author author = new Author(1L, "TestAuthor1");
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        final ResponseAuthorDto newResponseAuthorDto = authorService.getById(1L);

        assertEquals(author.getId(), newResponseAuthorDto.getId());
        assertEquals(author.getName(), newResponseAuthorDto.getName());
    }
}