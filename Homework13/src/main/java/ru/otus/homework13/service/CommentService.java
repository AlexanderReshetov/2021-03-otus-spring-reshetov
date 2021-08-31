package ru.otus.homework13.service;

import ru.otus.homework13.dto.RequestCommentDto;
import ru.otus.homework13.dto.ResponseCommentDto;

import java.util.List;

public interface CommentService {
    ResponseCommentDto insert(RequestCommentDto requestCommentDto);
    ResponseCommentDto update(Long id, RequestCommentDto requestCommentDto);
    Long delete(Long id);
    List<ResponseCommentDto> getAll();
    ResponseCommentDto getById(Long id);
}
