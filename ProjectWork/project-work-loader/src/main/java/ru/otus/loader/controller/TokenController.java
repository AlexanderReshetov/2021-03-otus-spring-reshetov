package ru.otus.loader.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.loader.dto.ResponseTokenDto;
import ru.otus.loader.service.GetTokenService;
import ru.otus.loader.service.exception.AuthenticationException;

@RestController
public class TokenController {
    private final GetTokenService getTokenService;

    @Autowired
    public TokenController(GetTokenService getTokenService) {
        this.getTokenService = getTokenService;
    }

    @GetMapping("/token")
    ResponseEntity<ResponseTokenDto> getToken() {
        return ResponseEntity.ok(ResponseTokenDto.toDto(getTokenService.getToken()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> errorGettingToken(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
