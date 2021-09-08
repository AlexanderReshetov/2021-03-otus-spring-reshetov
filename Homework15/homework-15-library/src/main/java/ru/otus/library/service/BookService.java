package ru.otus.library.service;

import ru.otus.library.dto.ResponseBookDto;
import ru.otus.library.dto.ResponseCommentDto;
import ru.otus.library.dto.RequestBookDto;

import java.util.List;

public interface BookService {
    ResponseBookDto insert(RequestBookDto requestBookDto);
    ResponseBookDto update(Long id, RequestBookDto requestBookDto);
    Long delete(Long id);
    List<ResponseBookDto> getAll();
    ResponseBookDto getById(Long id);
    List<ResponseCommentDto> getCommentsById(Long id);
}
