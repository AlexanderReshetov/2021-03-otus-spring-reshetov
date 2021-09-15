package ru.otus.loader.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import ru.otus.loader.dto.BlizzardTokenDto;
import ru.otus.loader.service.exception.AuthenticationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис получения токена должен")
public class GetTokenServiceImplTest {
    @Mock
    private RestOperations restOperations;

    @Test
    @DisplayName("вернуть токен")
    void shouldReturnItemById() {
        when(restOperations.exchange(any(), eq(BlizzardTokenDto.class)))
                .thenReturn(ResponseEntity.ok(new BlizzardTokenDto("token", 86399L)));
        final GetTokenService getTokenService = getTokenService();

        BlizzardTokenDto blizzardTokenDto = getTokenService.getToken();

        assertEquals("token", blizzardTokenDto.getToken());
        assertEquals(86399L, blizzardTokenDto.getExpiresIn());
    }

    @Test
    @DisplayName("вызвать исключение, если токен не получен")
    void shouldThrowExceptionIfCannotGetToken() {
        when(restOperations.exchange(any(), eq(BlizzardTokenDto.class)))
                .thenReturn(ResponseEntity.badRequest().body(null));
        final GetTokenService getTokenService = getTokenService();

        assertThrows(AuthenticationException.class, () -> getTokenService.getToken());
    }

    private GetTokenService getTokenService() {
        return new GetTokenServiceImpl(restOperations, "https://eu.api.blizzard.com/oauth/token", "client_id", "client_secret");
    }
}
