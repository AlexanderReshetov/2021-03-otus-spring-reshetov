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
import ru.otus.loader.dto.BlizzardItemDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис загрузки данных предмета должен")
public class LoadItemServiceImplTest {
    @Mock
    private WebClient.Builder webClientBuilder;

    @Test
    @DisplayName("вернуть данные предмета по идентификатору")
    void shouldReturnItemById() {
        final WebClient webClient = WebClient.builder()
                .exchangeFunction(clientRequest ->
                        Mono.just(ClientResponse.create(HttpStatus.OK)
                                .header("content-type", "application/json")
                                .body("{\"id\": 1,\"name\": {\"en_US\": \"item\",\"ru_RU\": \"предмет\"}}")
                                .build())
                ).build();
        when(webClientBuilder.baseUrl(any())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);

        LoadItemService loadItemService = new LoadItemServiceImpl(webClientBuilder, "");
        BlizzardItemDto blizzardItemDto = loadItemService.getItemById(1L);

        assertEquals(1L, blizzardItemDto.getId());
        assertEquals("item", blizzardItemDto.getBlizzardNameDto().getEn_US());
        assertEquals("предмет", blizzardItemDto.getBlizzardNameDto().getRu_RU());
    }
}
