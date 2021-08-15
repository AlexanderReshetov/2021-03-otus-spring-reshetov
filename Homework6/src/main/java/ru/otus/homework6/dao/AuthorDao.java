package ru.otus.homework6.dao;

import ru.otus.homework6.domain.Author;

import java.util.List;

public interface AuthorDao {
    List<Author> findAll();
    Author findById(Long id);
}
