package ru.otus.homework9.service;

import ru.otus.homework9.dto.ResponseAuthorDto;

import java.util.List;

public interface AuthorService {
    List<ResponseAuthorDto> getAll();
    ResponseAuthorDto getById(Long id);
}
