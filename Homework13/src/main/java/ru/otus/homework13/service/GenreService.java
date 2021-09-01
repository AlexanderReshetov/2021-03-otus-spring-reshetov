package ru.otus.homework13.service;

import ru.otus.homework13.dto.ResponseGenreDto;

import java.util.List;

public interface GenreService {
    List<ResponseGenreDto> getAll();
    ResponseGenreDto getById(Long id);
}
