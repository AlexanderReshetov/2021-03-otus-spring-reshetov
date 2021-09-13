package ru.otus.loader.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.otus.loader.dto.BlizzardAuctionsDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис загрузки данных аукционов должен")
public class LoadAuctionsServiceImplTest {
    @Mock
    private WebClient.Builder webClientBuilder;

    @Test
    @DisplayName("вернуть данные аукционов")
    void shouldReturnAuctions() {
        final WebClient webClient = WebClient.builder()
                .exchangeFunction(clientRequest ->
                        Mono.just(ClientResponse.create(HttpStatus.OK)
                                .header("content-type", "application/json")
                                .body("{\"auctions\": [{\"id\": 917654519,\"item\": {\"id\": 118711},\"quantity\": 60,\"unit_price\": 500000}]}")
                                .build())
                ).build();
        when(webClientBuilder.baseUrl(any())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);

        LoadAuctionsService loadAuctionsService = new LoadAuctionsServiceImpl(webClientBuilder, "");
        BlizzardAuctionsDto blizzardAuctionsDto = loadAuctionsService.getAllAuctionsByRealmId(1L);

        assertEquals(1, blizzardAuctionsDto.getBlizzardAuctionDtoList().size());
        assertEquals(917654519L, blizzardAuctionsDto.getBlizzardAuctionDtoList().get(0).getId());
        assertEquals(118711L, blizzardAuctionsDto.getBlizzardAuctionDtoList().get(0).getBlizzardItemDto().getId());
        assertEquals(500000L, blizzardAuctionsDto.getBlizzardAuctionDtoList().get(0).getItemPrice());
        assertEquals(60L, blizzardAuctionsDto.getBlizzardAuctionDtoList().get(0).getCount());
    }
}
