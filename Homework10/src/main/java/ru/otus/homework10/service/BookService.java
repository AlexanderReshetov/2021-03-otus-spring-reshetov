package ru.otus.homework10.service;

import ru.otus.homework10.dto.RequestBookDto;
import ru.otus.homework10.dto.ResponseBookDto;
import ru.otus.homework10.dto.ResponseCommentDto;

import java.util.List;

public interface BookService {
    ResponseBookDto insert(RequestBookDto requestBookDto);
    ResponseBookDto update(Long id, RequestBookDto requestBookDto);
    Long delete(Long id);
    List<ResponseBookDto> getAll();
    ResponseBookDto getById(Long id);
    List<ResponseCommentDto> getCommentsById(Long id);
}
