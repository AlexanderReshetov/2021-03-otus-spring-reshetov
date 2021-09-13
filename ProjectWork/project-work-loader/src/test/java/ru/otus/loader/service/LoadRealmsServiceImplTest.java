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
import ru.otus.loader.dto.BlizzardRealmsDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис загрузки игровых миров должен")
public class LoadRealmsServiceImplTest {
    @Mock
    private WebClient.Builder webClientBuilder;

    @Test
    @DisplayName("вернуть данные миров")
    void shouldReturnItemById() {
        final WebClient webClient = WebClient.builder()
                .exchangeFunction(clientRequest ->
                        Mono.just(ClientResponse.create(HttpStatus.OK)
                                .header("content-type", "application/json")
                                .body("{\"realms\": [{\"id\": 1,\"name\": {\"en_US\": \"Goldrinn\",\"ru_RU\": \"Голдринн\"}}]}")
                                .build())
                ).build();
        when(webClientBuilder.baseUrl(any())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);

        LoadRealmsService loadRealmsService = new LoadRealmsServiceImpl(webClientBuilder, "");
        BlizzardRealmsDto blizzardRealmsDto = loadRealmsService.getAllRealms();

        assertEquals(1L, blizzardRealmsDto.getBlizzardRealmDtoList().size());
        assertEquals(1L, blizzardRealmsDto.getBlizzardRealmDtoList().get(0).getId());
        assertEquals("Goldrinn", blizzardRealmsDto.getBlizzardRealmDtoList().get(0).getBlizzardNameDto().getEn_US());
        assertEquals("Голдринн", blizzardRealmsDto.getBlizzardRealmDtoList().get(0).getBlizzardNameDto().getRu_RU());
    }
}
