package ru.otus.homework8.service;

import ru.otus.homework8.model.Comment;

import java.util.List;

public interface CommentService {
    Comment insert(Long bookId, String text);
    void update(Long id, Long bookId, String text);
    void delete(Long id);
    List<Comment> getAll();
    Comment getById(Long id);
}
