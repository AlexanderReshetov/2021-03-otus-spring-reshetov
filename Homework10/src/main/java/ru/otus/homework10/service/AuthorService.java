package ru.otus.homework10.service;

import ru.otus.homework10.dto.ResponseAuthorDto;

import java.util.List;

public interface AuthorService {
    List<ResponseAuthorDto> getAll();
    ResponseAuthorDto getById(Long id);
}
