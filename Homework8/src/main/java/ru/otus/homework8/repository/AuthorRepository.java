package ru.otus.homework8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework8.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
