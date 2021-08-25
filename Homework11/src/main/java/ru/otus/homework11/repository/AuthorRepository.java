package ru.otus.homework11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework11.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
