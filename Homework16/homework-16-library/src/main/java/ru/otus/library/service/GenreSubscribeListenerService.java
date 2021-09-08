package ru.otus.library.service;

import ru.otus.library.dto.ResponseGenreDto;

import java.util.List;

public interface GenreSubscribeListenerService {
    void receiveGenreList(List<ResponseGenreDto> responseGenreDtoList);
}
