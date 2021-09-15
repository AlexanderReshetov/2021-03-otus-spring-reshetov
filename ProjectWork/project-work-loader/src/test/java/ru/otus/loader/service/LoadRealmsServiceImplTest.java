package ru.otus.loader.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import ru.otus.loader.dto.BlizzardNameDto;
import ru.otus.loader.dto.BlizzardRealmDto;
import ru.otus.loader.dto.BlizzardRealmsDto;
import ru.otus.loader.service.exception.RealmException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис загрузки игровых миров должен")
public class LoadRealmsServiceImplTest {
    @Mock
    private RestOperations restOperations;

    @Test
    @DisplayName("вернуть данные миров")
    void shouldReturnItemById() {
        when(restOperations.exchange(any(), eq(BlizzardRealmsDto.class)))
                .thenReturn(ResponseEntity.ok(
                        new BlizzardRealmsDto(Collections.singletonList(
                                new BlizzardRealmDto(1L,
                                        new BlizzardNameDto("Goldrinn", "Голдринн"))))));
        final LoadRealmsService loadRealmsService = loadRealmsService();

        final BlizzardRealmsDto blizzardRealmsDto = loadRealmsService.getAllRealms("token");

        assertEquals(1L, blizzardRealmsDto.getBlizzardRealmDtoList().size());
        assertEquals(1L, blizzardRealmsDto.getBlizzardRealmDtoList().get(0).getId());
        assertEquals("Goldrinn", blizzardRealmsDto.getBlizzardRealmDtoList().get(0).getBlizzardNameDto().getEn_US());
        assertEquals("Голдринн", blizzardRealmsDto.getBlizzardRealmDtoList().get(0).getBlizzardNameDto().getRu_RU());
    }

    @Test
    @DisplayName("вызвать исключение, если данные миров не получены")
    void shouldThrowExceptionIfCannotLoadRealms() {
        when(restOperations.exchange(any(), eq(BlizzardRealmsDto.class)))
                .thenReturn(ResponseEntity.badRequest().body(null));
        final LoadRealmsService loadRealmsService = loadRealmsService();

        assertThrows(RealmException.class, () -> loadRealmsService.getAllRealms("token"));
    }

    private LoadRealmsService loadRealmsService() {
        return new LoadRealmsServiceImpl(restOperations, "");
    }
}