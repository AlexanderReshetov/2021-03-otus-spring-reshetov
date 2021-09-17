package ru.otus.main.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import ru.otus.main.domain.Token;
import ru.otus.main.dto.ResponseTokenDto;
import ru.otus.main.repository.TokenRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис получения токенов должен")
public class TokenServiceImplTest {
    @Mock
    private RestOperations restOperations;
    @Mock
    private TokenRepository tokenRepository;

    private final static Long TOKEN_ID = 1L;
    private final static String TOKEN = "token";
    private final static LocalDateTime EXPIRATION_DT = LocalDateTime.now().plusSeconds(60);

    @Test
    @DisplayName("запросить токен из загрузчика")
    void shouldGetTokenFromLoader() {
        final TokenService tokenService = tokenService();
        when(restOperations.exchange(any(), eq(ResponseTokenDto.class)))
                .thenReturn(ResponseEntity.ok(new ResponseTokenDto(TOKEN, EXPIRATION_DT)));

        final Token token = tokenService.loadToken();

        verify(restOperations).exchange(any(), eq(ResponseTokenDto.class));
        assertEquals(TOKEN, token.getToken());
        assertEquals(EXPIRATION_DT, token.getExpirationDt());
    }

    @Test
    @DisplayName("запросить токен из базы данных")
    void shouldGetTokenFromDatabase() {
        final TokenService tokenService = tokenService();
        final Token token = new Token(TOKEN_ID, TOKEN, EXPIRATION_DT);
        when(tokenRepository.findByExpirationDtAfter(any())).thenReturn(token);

        final Token receivedToken = tokenService.getToken();

        assertEquals(token.getId(), receivedToken.getId());
        assertEquals(token.getToken(), receivedToken.getToken());
        assertEquals(token.getExpirationDt(), receivedToken.getExpirationDt());
    }

    private TokenService tokenService() {
        return new TokenServiceImpl(
                tokenRepository,
                restOperations,
                "host",
                "port");
    }
}
