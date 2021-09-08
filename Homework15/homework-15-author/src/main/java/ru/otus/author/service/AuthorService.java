package ru.otus.author.service;

import ru.otus.author.dto.ResponseAuthorDto;

import java.util.List;

public interface AuthorService {
    List<ResponseAuthorDto> getAll();
    ResponseAuthorDto getById(Long id);
}
