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
    private final RestOperations restOperations;
    private final String host;
    private final String port;

    @Autowired
    public TokenServiceImpl(TokenRepository tokenRepository,
                            RestOperations restOperations,
                            @Value("${loader-service.host}") String host,
                            @Value("${loader-service.port}") String port) {
        this.tokenRepository = tokenRepository;
        this.restOperations = restOperations;
        this.host = host;
        this.port = port;
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
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final RequestEntity<?> requestEntity = RequestEntity
                .get("http://" + host + ":" + port + "/token")
                .headers(headers)
                .build();
        return restOperations.exchange(requestEntity, ResponseTokenDto.class);
    }
}
