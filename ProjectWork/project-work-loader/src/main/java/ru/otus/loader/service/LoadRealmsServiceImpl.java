package ru.otus.loader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.otus.loader.dto.BlizzardRealmsDto;

@Service
public class LoadRealmsServiceImpl implements LoadRealmsService {
    private final WebClient webClient;

    @Autowired
    public LoadRealmsServiceImpl(@Qualifier("oauth-webclient-builder") WebClient.Builder webClientBuilder,
                                 @Value("${blizzard.api.host}") String host) {
        this.webClient = webClientBuilder.baseUrl(host).build();
    }

    public BlizzardRealmsDto getAllRealms() {
        return webClient.get()
                .uri("/data/wow/realm/index?namespace=dynamic-eu")
                .retrieve()
                .bodyToMono(BlizzardRealmsDto.class)
                .block();
    }
}