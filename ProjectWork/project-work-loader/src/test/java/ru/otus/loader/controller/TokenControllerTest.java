package ru.otus.loader.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.otus.loader.dto.*;
import ru.otus.loader.service.GetTokenService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Контроллер токенов должен")
public class TokenControllerTest {
    @Mock
    private GetTokenService getTokenService;
    @InjectMocks
    private TokenController tokenController;

    @Test
    @DisplayName("запросить токен и вернуть его")
    void shouldAskRealmsAndReturnData() {
        when(getTokenService.getToken())
                .thenReturn(new BlizzardTokenDto("token", 86399L));

        ResponseEntity<BlizzardTokenDto> responseEntity = tokenController.getToken();

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("token", responseEntity.getBody().getToken());
        assertEquals(86399L, responseEntity.getBody().getExpiresIn());
    }
}