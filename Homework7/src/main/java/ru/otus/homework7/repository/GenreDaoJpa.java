package ru.otus.homework7.repository;

import ru.otus.homework7.model.Genre;

import java.util.List;

public interface GenreDaoJpa {
    List<Genre> findAll();
    Genre findById(Long id);
}
