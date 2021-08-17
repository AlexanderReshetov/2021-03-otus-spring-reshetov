package ru.otus.homework6.dao;

import ru.otus.homework6.domain.Book;

import java.util.List;

public interface BookDao {
    void insert(Book book);
    void update(Book book);
    void delete(Long id);
    List<Book> findAll();
    Book findById(Long id);
}
