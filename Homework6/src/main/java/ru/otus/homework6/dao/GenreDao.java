package ru.otus.homework6.dao;

import ru.otus.homework6.domain.Genre;

import java.util.List;

public interface GenreDao {
    List<Genre> findAll();
    Genre findById(Long id);
}
