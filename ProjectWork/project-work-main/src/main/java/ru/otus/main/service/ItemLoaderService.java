package ru.otus.main.service;

import ru.otus.main.domain.AuctionLot;

import java.util.List;

public interface ItemLoaderService {
    void loadItemsByAuctions(List<AuctionLot> auctionLotList);
}
