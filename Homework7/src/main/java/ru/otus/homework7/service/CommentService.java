package ru.otus.homework7.service;

public interface CommentService {
    void insert(Long bookId, String text);
    void update(Long id, Long bookId, String text);
    void delete(Long id);
    void printAll();
    void printById(Long id);
}
