package ru.otus.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.main.domain.Realm;
import ru.otus.main.dto.ResponseRealmDto;
import ru.otus.main.repository.RealmRepository;
import ru.otus.main.service.exception.TokenException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RealmServiceImpl implements RealmService {
    private final TokenService tokenService;
    private final RequestEntityService requestEntityService;
    private final RealmRepository realmRepository;

    @Autowired
    public RealmServiceImpl(TokenService tokenService,
                            RequestEntityService requestEntityService,
                            RealmRepository realmRepository) {
        this.tokenService = tokenService;
        this.requestEntityService = requestEntityService;
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
        final ResponseEntity<List<ResponseRealmDto>> responseEntity = requestEntityService.getResponseRealmDtoList(token);
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
