package ru.otus.loader.service;

import ru.otus.loader.dto.BlizzardAuctionsDto;

public interface LoadAuctionsService {
    BlizzardAuctionsDto getAllAuctionsByRealmId(String token, Long realmId);
}
