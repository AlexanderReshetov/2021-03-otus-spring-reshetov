package ru.otus.homework6.service;

public interface BookService {
    void insert(String name, Long authorId, Long genreId);
    void update(Long id, String name, Long authorId, Long genreId);
    void delete(Long id);
    void printAll();
    void printById(Long id);
}
