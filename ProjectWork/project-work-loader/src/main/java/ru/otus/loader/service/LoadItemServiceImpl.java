package ru.otus.loader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import ru.otus.loader.dto.BlizzardItemDto;
import ru.otus.loader.service.exception.ItemException;

import java.util.Collections;

@Service
public class LoadItemServiceImpl implements LoadItemService {
    private final RestOperations restOperations;
    private final String host;

    @Autowired
    public LoadItemServiceImpl(RestOperations restOperations,
                               @Value("${blizzard.host}") String host) {
        this.restOperations = restOperations;
        this.host = host;
    }

    public BlizzardItemDto getItemById(String token, Long id) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.put("Authorization", Collections.singletonList(token));
        final RequestEntity<?> requestEntity = RequestEntity
                .get(host + "/data/wow/item/" + id + "?namespace=static-eu")
                .headers(headers)
                .build();
        ResponseEntity<BlizzardItemDto> responseEntity = restOperations.exchange(requestEntity, BlizzardItemDto.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        }
        else {
            throw new ItemException("Can't get item from Blizzard Developer API!");
        }
    }
}
