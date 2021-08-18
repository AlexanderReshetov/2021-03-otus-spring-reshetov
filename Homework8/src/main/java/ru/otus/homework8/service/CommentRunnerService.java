package ru.otus.homework8.service;

import ru.otus.homework8.model.Comment;

public interface CommentRunnerService {
    void insert(Long bookId, String text);
    void update(Long id, Long bookId, String text);
    void delete(Long id);
    void printAll();
    void printById(Long id);
}
