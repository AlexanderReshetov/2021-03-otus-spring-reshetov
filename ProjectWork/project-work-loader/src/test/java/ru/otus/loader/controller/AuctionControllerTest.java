package ru.otus.loader.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.otus.loader.dto.BlizzardAuctionDto;
import ru.otus.loader.dto.BlizzardAuctionItemDto;
import ru.otus.loader.dto.BlizzardAuctionsDto;
import ru.otus.loader.dto.ResponseAuctionDto;
import ru.otus.loader.service.LoadAuctionsService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Контроллер аукциона должен")
public class AuctionControllerTest {
    @Mock
    private LoadAuctionsService loadAuctionsService;
    @InjectMocks
    private AuctionController auctionController;

    @Test
    @DisplayName("запросить данные аукциона и вернуть их")
    void shouldAskAuctionAndReturnData() {
        when(loadAuctionsService.getAllAuctionsByRealmId(1L))
                .thenReturn(new BlizzardAuctionsDto(Collections.singletonList(new BlizzardAuctionDto(1L, new BlizzardAuctionItemDto(2L), 3L, 4L))));

        ResponseEntity<List<ResponseAuctionDto>> responseEntity = auctionController.getAllAuctionsByRealmId(1L);

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals(1L, responseEntity.getBody().get(0).getId());
        assertEquals(2L, responseEntity.getBody().get(0).getItemId());
        assertEquals(3L, responseEntity.getBody().get(0).getItemPrice());
        assertEquals(4L, responseEntity.getBody().get(0).getCount());
    }
}
