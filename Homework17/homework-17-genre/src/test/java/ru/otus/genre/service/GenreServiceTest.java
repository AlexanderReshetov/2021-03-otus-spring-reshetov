package ru.otus.genre.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.genre.message.publisher.GenrePublishGateway;
import ru.otus.genre.repository.GenreRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис операций с жанрами должен")
public class GenreServiceTest {
    @Mock
    private GenreRepository genreRepository;
    @Mock
    private GenrePublishGateway genrePublishGateway;
    @InjectMocks
    private GenreService genreService;

    @Test
    @DisplayName("отправить список жанров в кафку")
    void shouldGetGenreById() {
        genreService.broadcastGenres();

        verify(genreRepository).findAll();
        verify(genrePublishGateway).genrePublish(any());
    }
}