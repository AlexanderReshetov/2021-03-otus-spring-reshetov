package ru.otus.loader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.otus.loader.dto.BlizzardItemDto;
import ru.otus.loader.dto.BlizzardRealmsDto;

@Service
public class LoadItemServiceImpl implements LoadItemService {
    private final WebClient webClient;

    @Autowired
    public LoadItemServiceImpl(@Qualifier("oauth-webclient-builder") WebClient.Builder webClientBuilder,
                               @Value("${blizzard.api.host}") String host) {
        this.webClient = webClientBuilder.baseUrl(host).build();
    }

    public BlizzardItemDto getItemById(Long id) {
        return webClient.get()
                .uri("/data/wow/item/" + id + "?namespace=static-eu")
                .retrieve()
                .bodyToMono(BlizzardItemDto.class)
                .block();
    }
}
