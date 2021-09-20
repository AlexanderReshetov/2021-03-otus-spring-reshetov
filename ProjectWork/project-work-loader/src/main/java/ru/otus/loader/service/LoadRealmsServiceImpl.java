package ru.otus.loader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import ru.otus.loader.dto.BlizzardRealmsDto;
import ru.otus.loader.service.exception.RealmException;

import java.util.Collections;

@Service
public class LoadRealmsServiceImpl implements LoadRealmsService {
    private final RestOperations restOperations;
    private final String host;

    @Autowired
    public LoadRealmsServiceImpl(RestOperations restOperations,
                                 @Value("${blizzard.host}") String host) {
        this.restOperations = restOperations;
        this.host = host;
    }

    public BlizzardRealmsDto getAllRealms(String token) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.put("Authorization", Collections.singletonList(token));
        final RequestEntity<?> requestEntity = RequestEntity
                .get(host + "/data/wow/realm/index?namespace=dynamic-eu")
                .headers(headers)
                .build();
        ResponseEntity<BlizzardRealmsDto> responseEntity = restOperations.exchange(requestEntity, BlizzardRealmsDto.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        }
        else {
            throw new RealmException("Can't get realm list from Blizzard Developer API!");
        }
    }
}