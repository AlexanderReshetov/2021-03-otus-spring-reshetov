package ru.otus.loader.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import ru.otus.loader.dto.BlizzardAuctionDto;
import ru.otus.loader.dto.BlizzardAuctionItemDto;
import ru.otus.loader.dto.BlizzardAuctionsDto;
import ru.otus.loader.service.exception.AuctionException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис загрузки данных аукционов должен")
public class LoadAuctionsServiceImplTest {
    @Mock
    private RestOperations restOperations;

    @Test
    @DisplayName("вернуть данные аукционов")
    void shouldReturnAuctions() {
        when(restOperations.exchange(any(), eq(BlizzardAuctionsDto.class)))
                .thenReturn(ResponseEntity.ok(
                        new BlizzardAuctionsDto(Collections.singletonList(
                                new BlizzardAuctionDto(1L,
                                        new BlizzardAuctionItemDto(2L), 3L, 4L)))));
        final LoadAuctionsService loadAuctionsService = loadAuctionsService();

        final BlizzardAuctionsDto blizzardAuctionsDto = loadAuctionsService.getAllAuctionsByRealmId("token", 1L);

        assertEquals(1, blizzardAuctionsDto.getBlizzardAuctionDtoList().size());
        assertEquals(1L, blizzardAuctionsDto.getBlizzardAuctionDtoList().get(0).getId());
        assertEquals(2L, blizzardAuctionsDto.getBlizzardAuctionDtoList().get(0).getBlizzardItemDto().getId());
        assertEquals(3L, blizzardAuctionsDto.getBlizzardAuctionDtoList().get(0).getItemPrice());
        assertEquals(4L, blizzardAuctionsDto.getBlizzardAuctionDtoList().get(0).getCount());
    }

    @Test
    @DisplayName("вызвать исключение, если данные аукционов не получены")
    void shouldThrowExceptionIfCannotLoadAuctions() {
        when(restOperations.exchange(any(), eq(BlizzardAuctionsDto.class)))
                .thenReturn(ResponseEntity.badRequest().body(null));
        final LoadAuctionsService loadAuctionsService = loadAuctionsService();

        assertThrows(AuctionException.class, () -> loadAuctionsService.getAllAuctionsByRealmId("token", 1L));
    }

    private LoadAuctionsService loadAuctionsService() {
        return new LoadAuctionsServiceImpl(restOperations, "");
    }
}