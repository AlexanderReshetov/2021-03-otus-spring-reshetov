package ru.otus.main.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import ru.otus.main.dto.ResponseAuctionDto;
import ru.otus.main.dto.ResponseItemDto;
import ru.otus.main.dto.ResponseRealmDto;
import ru.otus.main.dto.ResponseTokenDto;

import java.util.Collections;
import java.util.List;

@Service
public class RequestEntityServiceImpl implements RequestEntityService {
    private final RestOperations restOperations;
    private final String host;
    private final String port;
    final HttpHeaders headers = new HttpHeaders();

    public RequestEntityServiceImpl(RestOperations restOperations,
                                    @Value("${loader-service.host}") String host,
                                    @Value("${loader-service.port}") String port) {
        this.restOperations = restOperations;
        this.host = host;
        this.port = port;
        headers.setContentType(MediaType.APPLICATION_JSON);
   }

    public ResponseEntity<ResponseItemDto> getResponseItemDto(String token, Long id) {
        headers.put("Authorization", Collections.singletonList(token));
        final RequestEntity<?> requestEntity = RequestEntity
                .get("http://" + host + ":" + port + "/items/" + id)
                .headers(headers)
                .build();
        return restOperations.exchange(requestEntity, ResponseItemDto.class);
    }

    public ResponseEntity<List<ResponseAuctionDto>> getResponseAuctionDtoList(String token, Long realmId) {
        headers.put("Authorization", Collections.singletonList(token));
        final RequestEntity<?> requestEntity = RequestEntity
                .get("http://" + host + ":" + port + "/auctions/" + realmId)
                .headers(headers)
                .build();
        return restOperations.exchange(requestEntity, new ParameterizedTypeReference<List<ResponseAuctionDto>>() {
        });
    }

    public ResponseEntity<List<ResponseRealmDto>> getResponseRealmDtoList(String token) {
        headers.put("Authorization", Collections.singletonList(token));
        final RequestEntity<?> requestEntity = RequestEntity
                .get("http://" + host + ":" + port + "/realms")
                .headers(headers)
                .build();
        return restOperations.exchange(requestEntity, new ParameterizedTypeReference<List<ResponseRealmDto>>() {
        });
    }

    public ResponseEntity<ResponseTokenDto> getResponseTokenDto() {
        final RequestEntity<?> requestEntity = RequestEntity
                .get("http://" + host + ":" + port + "/token")
                .headers(headers)
                .build();
        return restOperations.exchange(requestEntity,ResponseTokenDto.class);
    }
}
