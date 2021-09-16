package ru.otus.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestOperations;
import ru.otus.main.domain.Realm;
import ru.otus.main.dto.ResponseRealmDto;
import ru.otus.main.repository.RealmRepository;
import ru.otus.main.service.exception.TokenException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RealmServiceImpl implements RealmService {
    private final TokenService tokenService;
    private final RestOperations restOperations;
    private final String host;
    private final String port;
    private final RealmRepository realmRepository;

    @Autowired
    public RealmServiceImpl(TokenService tokenService,
                            RestOperations restOperations,
                            @Value("${loader-service.host}") String host,
                            @Value("${loader-service.port}") String port,
                            RealmRepository realmRepository) {
        this.tokenService = tokenService;
        this.restOperations = restOperations;
        this.host = host;
        this.port = port;
        this.realmRepository = realmRepository;
    }

    public List<Realm> loadAllRealms() {
        try {
            return getRealmsFromBlizzard(tokenService.getToken().getToken());
        }
        catch (TokenException e) {
            return getRealmsFromDatabase();
        }
    }

    @Transactional(readOnly = true)
    private List<Realm> getRealmsFromDatabase() {
        return realmRepository.findAll();
    }

    private List<Realm> getRealmsFromBlizzard(String token) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.put("Authorization", Collections.singletonList(token));
        final RequestEntity<?> requestEntity = RequestEntity
                .get("http://" + host + ":" + port + "/realms")
                .headers(headers)
                .build();
        ResponseEntity<List<ResponseRealmDto>> responseEntity = restOperations.exchange(requestEntity, new ParameterizedTypeReference<List<ResponseRealmDto>>() {
        });
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            List<Realm> realmList = responseEntity.getBody().stream().map(ResponseRealmDto::toDomain).collect(Collectors.toList());
            updateRealmRepository(realmList);
            return realmList;
        }
        else {
            return getRealmsFromDatabase();
        }
    }

    @Transactional
    private void updateRealmRepository(List<Realm> realmList) {
        realmRepository.deleteAll();
        realmRepository.saveAll(realmList);
    }
}
