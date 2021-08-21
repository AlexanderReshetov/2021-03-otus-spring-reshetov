package ru.otus.homework9.service;

import ru.otus.homework9.dto.RequestCommentDto;
import ru.otus.homework9.dto.ResponseCommentDto;

import java.util.List;

public interface CommentService {
    ResponseCommentDto insert(RequestCommentDto requestCommentDto);
    ResponseCommentDto update(Long id, RequestCommentDto requestCommentDto);
    Long delete(Long id);
    List<ResponseCommentDto> getAll();
    ResponseCommentDto getById(Long id);
}
