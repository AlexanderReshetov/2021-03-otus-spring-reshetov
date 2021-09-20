package ru.otus.main.service;

import ru.otus.main.domain.AuctionLot;
import ru.otus.main.dto.ResponseAuctionDto;

import java.util.List;

public interface AuctionService {
    List<AuctionLot> loadAllAuctionsByRealmId(Long realmId);
    void save(ResponseAuctionDto responseAuctionDto);
}
