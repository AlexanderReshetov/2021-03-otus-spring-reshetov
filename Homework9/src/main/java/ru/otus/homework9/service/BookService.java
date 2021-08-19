package ru.otus.homework9.service;

import ru.otus.homework9.dto.RequestBookDto;
import ru.otus.homework9.dto.ResponseBookDto;
import ru.otus.homework9.dto.ResponseCommentDto;

import java.util.List;

public interface BookService {
    ResponseBookDto insert(RequestBookDto requestBookDto);
    ResponseBookDto update(Long id, RequestBookDto requestBookDto);
    Long delete(Long id);
    List<ResponseBookDto> getAll();
    ResponseBookDto getById(Long id);
    List<ResponseCommentDto> getCommentsById(Long id);
}
