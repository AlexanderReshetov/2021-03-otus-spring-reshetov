package ru.otus.library.service;

import ru.otus.library.dto.ResponseCommentDto;
import ru.otus.library.dto.RequestCommentDto;

import java.util.List;

public interface CommentService {
    ResponseCommentDto insert(RequestCommentDto requestCommentDto);
    ResponseCommentDto update(Long id, RequestCommentDto requestCommentDto);
    Long delete(Long id);
    List<ResponseCommentDto> getAll();
    ResponseCommentDto getById(Long id);
}
