package ru.otus.loader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import ru.otus.loader.dto.BlizzardTokenDto;
import ru.otus.loader.service.exception.AuthenticationException;

@Service
public class GetTokenServiceImpl implements GetTokenService {
    private final RestOperations restOperations;
    private final String tokenUrl;
    private final String clientId;
    private final String clientSecret;

    @Autowired
    public GetTokenServiceImpl(RestOperations restOperations,
                               @Value("${blizzard.token-url}") String tokenUrl,
                               @Value("${blizzard.client-id}") String clientId,
                               @Value("${blizzard.client-secret}") String clientSecret) {
        this.restOperations = restOperations;
        this.tokenUrl = tokenUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public BlizzardTokenDto getToken() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final RequestEntity<?> loginRequestEntity = RequestEntity
                .post(tokenUrl + "?grant_type=client_credentials&client_id=" + clientId + "&client_secret=" + clientSecret)
                .headers(headers)
                .body("");
        ResponseEntity<BlizzardTokenDto> responseEntity = restOperations.exchange(loginRequestEntity, BlizzardTokenDto.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        }
        else {
            throw new AuthenticationException("Can't get token from Blizzard Developer API!");
        }
    }
}