package ru.otus.loader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import ru.otus.loader.config.BlizzardSettings;
import ru.otus.loader.dto.BlizzardTokenDto;
import ru.otus.loader.service.exception.AuthenticationException;

@Service
public class GetTokenServiceImpl implements GetTokenService {
    private final RestOperations restOperations;
    private final BlizzardSettings blizzardSettings;

    @Autowired
    public GetTokenServiceImpl(RestOperations restOperations,
                               BlizzardSettings blizzardSettings) {
        this.restOperations = restOperations;
        this.blizzardSettings = blizzardSettings;
    }

    public BlizzardTokenDto getToken() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final RequestEntity<?> loginRequestEntity = RequestEntity
                .post(blizzardSettings.getToken().get("url") + "?grant_type=client_credentials&client_id=" + blizzardSettings.getToken().get("id") + "&client_secret=" + blizzardSettings.getToken().get("secret"))
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