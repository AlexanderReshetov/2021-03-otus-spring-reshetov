package ru.otus.main.service;

import ru.otus.main.domain.Auction;
import ru.otus.main.dto.ResponseAuctionDto;

import java.util.List;

public interface AuctionService {
    List<Auction> loadAllAuctionsByRealmId(Long realmId);
    void save(ResponseAuctionDto responseAuctionDto);
}
