package ru.otus.loader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.otus.loader.dto.BlizzardAuctionsDto;

@Service
public class LoadAuctionsServiceImpl implements LoadAuctionsService {
    private final WebClient webClient;

    @Autowired
    public LoadAuctionsServiceImpl(@Qualifier("oauth-webclient-builder") WebClient.Builder webClientBuilder,
                                   @Value("${blizzard.api.host}") String host) {
        this.webClient = webClientBuilder.baseUrl(host).build();
    }

    public BlizzardAuctionsDto getAllAuctionsByRealmId(Long realmId) {
        return webClient.get()
                .uri("/data/wow/connected-realm/" + realmId + "/auctions?namespace=dynamic-eu")
                .retrieve()
                .bodyToMono(BlizzardAuctionsDto.class)
                .block();
    }
}
