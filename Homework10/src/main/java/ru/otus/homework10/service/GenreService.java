package ru.otus.homework10.service;

import ru.otus.homework10.dto.ResponseGenreDto;

import java.util.List;

public interface GenreService {
    List<ResponseGenreDto> getAll();
    ResponseGenreDto getById(Long id);
}
