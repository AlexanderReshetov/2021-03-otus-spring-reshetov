package ru.otus.homework7.repository;

import ru.otus.homework7.model.Author;

import java.util.List;

public interface AuthorDaoJpa {
    List<Author> findAll();
    Author findById(Long id);
}
