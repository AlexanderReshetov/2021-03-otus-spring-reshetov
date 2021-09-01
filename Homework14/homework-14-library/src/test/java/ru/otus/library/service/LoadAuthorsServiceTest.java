package ru.otus.library.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.library.controller.AuthorController;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис загрузки авторов должен")
public class LoadAuthorsServiceTest {
    @Mock
    private AuthorController authorController;
    @InjectMocks
    private LoadAuthorsService loadAuthorsService;

    @Test
    @DisplayName("загрузить список авторов")
    void shouldLoadAuthors() {
        loadAuthorsService.loadAuthors();

        verify(authorController).getAllAuthors();
    }
}
