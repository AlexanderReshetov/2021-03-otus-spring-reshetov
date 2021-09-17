package ru.otus.main.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.main.domain.Token;
import ru.otus.main.service.exception.TokenNotExistsException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DisplayName("Dao токенов должен")
public class TokenRepositoryTest {
    @Autowired
    private TokenRepository tokenRepository;

    @Test
    @DisplayName("добавить токен и получить его по ИД")
    void shouldAddTokenAndGetHimById() {
        final String tokenString = "token";
        final LocalDateTime localDateTime = LocalDateTime.now();
        Token token = new Token(null, tokenString, localDateTime);

        token = tokenRepository.save(token);
        final Long newId = token.getId();
        token = tokenRepository.findById(token.getId()).orElseThrow(() -> new TokenNotExistsException("There is no token with id = " + newId));

        assertEquals(tokenString, token.getToken());
        assertEquals(localDateTime, token.getExpirationDt());
    }
}