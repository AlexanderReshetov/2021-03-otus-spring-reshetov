package ru.otus.author.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.author.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
