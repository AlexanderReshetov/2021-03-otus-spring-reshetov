package ru.otus.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestOperations;
import ru.otus.main.domain.Token;
import ru.otus.main.dto.ResponseTokenDto;
import ru.otus.main.repository.TokenRepository;
import ru.otus.main.service.exception.TokenException;

import java.time.LocalDateTime;

@Service
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;
    private final RequestEntityService requestEntityService;

    @Autowired
    public TokenServiceImpl(TokenRepository tokenRepository,
                            RequestEntityService requestEntityService) {
        this.tokenRepository = tokenRepository;
        this.requestEntityService = requestEntityService;
    }

    public Token loadToken() {
        ResponseEntity<ResponseTokenDto> responseEntity = getTokenFromBlizzard();
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            updateTokenInDatabase(ResponseTokenDto.toDomain(responseEntity.getBody()));
            return ResponseTokenDto.toDomain(responseEntity.getBody());
        }
        else {
            throw new TokenException("Cannot get token from Blizzard API!");
        }
    }

    public Token getToken() {
        Token token = getTokenFromDatabase();
        if (token != null) {
            return token;
        }
        else {
            ResponseEntity<ResponseTokenDto> responseEntity = getTokenFromBlizzard();
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                updateTokenInDatabase(ResponseTokenDto.toDomain(responseEntity.getBody()));
                return ResponseTokenDto.toDomain(responseEntity.getBody());
            }
            else {
                throw new TokenException("Cannot get token from Blizzard API!");
            }
        }
    }

    @Transactional(readOnly = true)
    private Token getTokenFromDatabase() {
        return tokenRepository.findByExpirationDtAfter(LocalDateTime.now().plusSeconds(1));
    }

    @Transactional
    private void updateTokenInDatabase(Token token) {
        tokenRepository.deleteAll();
        tokenRepository.save(token);
    }

    private ResponseEntity<ResponseTokenDto> getTokenFromBlizzard() {
        return requestEntityService.getResponseTokenDto();
    }
}
