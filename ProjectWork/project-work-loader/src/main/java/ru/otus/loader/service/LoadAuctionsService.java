package ru.otus.loader.service;

import ru.otus.loader.dto.BlizzardAuctionsDto;

public interface LoadAuctionsService {
    BlizzardAuctionsDto getAllAuctionsByRealmId(Long realmId);
}
