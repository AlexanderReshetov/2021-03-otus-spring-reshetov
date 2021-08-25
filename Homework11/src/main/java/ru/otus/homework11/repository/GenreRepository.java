package ru.otus.homework11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework11.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
