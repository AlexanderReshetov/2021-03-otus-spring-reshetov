package ru.otus.homework11.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework11.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(value = "User.roles")
    User findByLogin(String login);
}
