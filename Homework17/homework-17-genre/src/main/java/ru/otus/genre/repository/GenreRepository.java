package ru.otus.genre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.genre.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
