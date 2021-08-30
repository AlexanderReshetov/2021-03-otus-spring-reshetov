package ru.otus.homework11.service;

import ru.otus.homework11.dto.RequestBookDto;
import ru.otus.homework11.dto.ResponseBookDto;
import ru.otus.homework11.dto.ResponseCommentDto;

import java.util.List;

public interface BookService {
    ResponseBookDto insert(RequestBookDto requestBookDto);
    ResponseBookDto update(Long id, RequestBookDto requestBookDto);
    Long delete(Long id);
    List<ResponseBookDto> getAll();
    ResponseBookDto getById(Long id);
    List<ResponseCommentDto> getCommentsById(Long id);
}
