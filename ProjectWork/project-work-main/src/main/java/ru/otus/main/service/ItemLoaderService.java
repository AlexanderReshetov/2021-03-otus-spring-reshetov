package ru.otus.main.service;

import ru.otus.main.domain.Auction;

import java.util.List;

public interface ItemLoaderService {
    void loadItemsByAuctions(List<Auction> auctionList);
}
