package ru.otus.main.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import ru.otus.main.domain.Realm;
import ru.otus.main.domain.Token;
import ru.otus.main.dto.ResponseRealmDto;
import ru.otus.main.repository.RealmRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис получения игровых миров должен")
public class RealmServiceImplTest {
    @Mock
    private TokenService tokenService;
    @Mock
    private RestOperations restOperations;
    @Mock
    private RealmRepository realmRepository;

    private final static Long BLIZZARD_ID = 1L;
    private final static String EN_NAME = "Goldrinn";
    private final static String RU_NAME = "Голдринн";
    private final static LocalDateTime LOCAL_DATE_TIME = LocalDateTime.now();
    private final static String TOKEN = "token";

    @Test
    @DisplayName("запросить игровой мир из загрузчика по Blizzard ИД")
    void shouldGetRealmFromLoaderByBlizzardId() {
        final RealmService realmService = realmService();
        when(tokenService.getToken()).thenReturn(new Token(null, TOKEN, LOCAL_DATE_TIME));
        when(restOperations.exchange(any(), Matchers.<ParameterizedTypeReference<List<ResponseRealmDto>>>any()))
                .thenReturn(ResponseEntity.ok(Collections.singletonList(new ResponseRealmDto(BLIZZARD_ID, EN_NAME, RU_NAME))));

        List<Realm> realmList = realmService.loadAllRealms();

        verify(tokenService).getToken();
        verify(restOperations).exchange(any(), Matchers.<ParameterizedTypeReference<List<ResponseRealmDto>>>any());
        assertEquals(1, realmList.size());
        assertEquals(BLIZZARD_ID, realmList.get(0).getBlizzardId());
        assertEquals(EN_NAME, realmList.get(0).getEnName());
        assertEquals(RU_NAME, realmList.get(0).getRuName());
    }

    private RealmService realmService() {
        return new RealmServiceImpl(
                tokenService,
                restOperations,
                "host",
                "port",
                realmRepository);
    }
}