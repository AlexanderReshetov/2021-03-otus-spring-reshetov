package ru.otus.library.service;

import ru.otus.library.dto.ResponseAuthorDto;

import java.util.List;

public interface LoadAuthorsService {
    List<ResponseAuthorDto> getAllAuthors();
}
