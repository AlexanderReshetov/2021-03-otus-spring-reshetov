package ru.otus.homework11.service;

import ru.otus.homework11.dto.ResponseGenreDto;

import java.util.List;

public interface GenreService {
    List<ResponseGenreDto> getAll();
    ResponseGenreDto getById(Long id);
}
