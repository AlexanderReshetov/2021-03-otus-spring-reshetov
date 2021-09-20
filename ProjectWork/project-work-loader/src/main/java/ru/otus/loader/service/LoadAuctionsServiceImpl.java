package ru.otus.loader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import ru.otus.loader.dto.BlizzardAuctionsDto;
import ru.otus.loader.service.exception.AuctionException;

import java.util.Collections;

@Service
public class LoadAuctionsServiceImpl implements LoadAuctionsService {
    private final RestOperations restOperations;
    private final String host;

    @Autowired
    public LoadAuctionsServiceImpl(RestOperations restOperations,
                               @Value("${blizzard.host}") String host) {
        this.restOperations = restOperations;
        this.host = host;
    }

    public BlizzardAuctionsDto getAllAuctionsByRealmId(String token, Long realmId) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.put("Authorization", Collections.singletonList(token));
        final RequestEntity<?> requestEntity = RequestEntity
                .get(host + "/data/wow/connected-realm/" + realmId + "/auctions?namespace=dynamic-eu")
                .headers(headers)
                .build();
        ResponseEntity<BlizzardAuctionsDto> responseEntity = restOperations.exchange(requestEntity, BlizzardAuctionsDto.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        }
        else {
            throw new AuctionException("Can't get auction from Blizzard Developer API!");
        }
    }
}
