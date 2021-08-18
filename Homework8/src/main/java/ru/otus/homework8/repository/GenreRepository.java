package ru.otus.homework8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework8.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
