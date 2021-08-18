package ru.otus.homework8.service;

import ru.otus.homework8.model.Book;

import java.util.List;

public interface BookService {
    Book insert(String name, Long authorId, Long genreId);
    void update(Long id, String name, Long authorId, Long genreId);
    void delete(Long id);
    List<Book> getAll();
    Book getById(Long id);
}
