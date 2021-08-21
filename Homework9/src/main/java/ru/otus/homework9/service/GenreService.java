package ru.otus.homework9.service;

import ru.otus.homework9.dto.ResponseGenreDto;

import java.util.List;

public interface GenreService {
    List<ResponseGenreDto> getAll();
    ResponseGenreDto getById(Long id);
}
