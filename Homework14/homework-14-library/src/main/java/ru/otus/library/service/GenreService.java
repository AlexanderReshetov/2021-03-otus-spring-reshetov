package ru.otus.library.service;

import ru.otus.library.dto.ResponseGenreDto;

import java.util.List;

public interface GenreService {
    List<ResponseGenreDto> getAll();
    ResponseGenreDto getById(Long id);
}
