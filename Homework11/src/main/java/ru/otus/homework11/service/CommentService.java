package ru.otus.homework11.service;

import ru.otus.homework11.dto.RequestCommentDto;
import ru.otus.homework11.dto.ResponseCommentDto;

import java.util.List;

public interface CommentService {
    ResponseCommentDto insert(RequestCommentDto requestCommentDto);
    ResponseCommentDto update(Long id, RequestCommentDto requestCommentDto);
    Long delete(Long id);
    List<ResponseCommentDto> getAll();
    ResponseCommentDto getById(Long id);
}
