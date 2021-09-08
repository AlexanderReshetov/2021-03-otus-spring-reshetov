package ru.otus.library.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.library.dto.ResponseGenreDto;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис обновления таблицы жанров по сообщению кафки должен")
public class GenreSubscriberListenerServiceImplTest {
    @Mock
    private GenreService genreService;
    @InjectMocks
    private GenreSubscriberListenerServiceImpl genreSubscribeListenerService;

    @Test
    @DisplayName("сохранить полученные данные в таблице жанров")
    void shouldSaveMessageInGenreTable() {
        final List<ResponseGenreDto> responseGenreDtoList = Collections.singletonList(new ResponseGenreDto(1L, "TestGenre1"));
        genreSubscribeListenerService.receiveGenreList(responseGenreDtoList);

        verify(genreService).saveAll(responseGenreDtoList);
    }
}
