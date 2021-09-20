package ru.otus.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.main.domain.Token;

import java.time.LocalDateTime;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByExpirationDtAfter(LocalDateTime localDateTime);
}
