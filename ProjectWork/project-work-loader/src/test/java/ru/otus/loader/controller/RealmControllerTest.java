package ru.otus.loader.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.otus.loader.dto.BlizzardNameDto;
import ru.otus.loader.dto.BlizzardRealmDto;
import ru.otus.loader.dto.BlizzardRealmsDto;
import ru.otus.loader.dto.ResponseRealmDto;
import ru.otus.loader.service.LoadRealmsService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Контроллер игровых миров должен")
public class RealmControllerTest {
    @Mock
    private LoadRealmsService loadRealmsService;
    @InjectMocks
    private RealmController realmController;

    @Test
    @DisplayName("запросить данные игровых миров и вернуть их")
    void shouldAskRealmsAndReturnData() {
        when(loadRealmsService.getAllRealms("token"))
                .thenReturn(new BlizzardRealmsDto(Collections.singletonList(new BlizzardRealmDto(1L, new BlizzardNameDto("Goldrinn", "Голдринн")))));

        ResponseEntity<List<ResponseRealmDto>> responseEntity = realmController.getAllRealms("token");

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals(1L, responseEntity.getBody().get(0).getId());
        assertEquals("Goldrinn", responseEntity.getBody().get(0).getEnName());
        assertEquals("Голдринн", responseEntity.getBody().get(0).getRuName());
    }
}