package ru.otus.homework7.repository;

import ru.otus.homework7.model.Book;

import java.util.List;

public interface BookDaoJpa {
    Long insert(Book book);
    void update(Book book);
    void delete(Long id);
    List<Book> findAll();
    Book findById(Long id);
}
