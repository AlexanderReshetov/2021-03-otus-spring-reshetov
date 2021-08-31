package ru.otus.homework13.service;

import ru.otus.homework13.dto.RequestBookDto;
import ru.otus.homework13.dto.ResponseBookDto;
import ru.otus.homework13.dto.ResponseCommentDto;

import java.util.List;

public interface BookService {
    ResponseBookDto insert(RequestBookDto requestBookDto);
    ResponseBookDto update(Long id, RequestBookDto requestBookDto);
    Long delete(Long id);
    List<ResponseBookDto> getAll();
    ResponseBookDto getById(Long id);
    List<ResponseCommentDto> getCommentsById(Long id);
}
