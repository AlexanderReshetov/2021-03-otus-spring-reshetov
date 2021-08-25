package ru.otus.homework11.service;

import ru.otus.homework11.dto.ResponseAuthorDto;

import java.util.List;

public interface AuthorService {
    List<ResponseAuthorDto> getAll();
    ResponseAuthorDto getById(Long id);
}
