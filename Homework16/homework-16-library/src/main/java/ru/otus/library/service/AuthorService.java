package ru.otus.library.service;

import ru.otus.library.dto.ResponseAuthorDto;

import java.util.List;

public interface AuthorService {
    List<ResponseAuthorDto> getAll();
    ResponseAuthorDto getById(Long id);
    void saveAll(List<ResponseAuthorDto> responseAuthorDtoList);
}
