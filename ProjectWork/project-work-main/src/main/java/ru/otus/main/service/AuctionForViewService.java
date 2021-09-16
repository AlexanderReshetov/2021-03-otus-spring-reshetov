package ru.otus.main.service;

import ru.otus.main.domain.AuctionForView;

import java.util.List;

public interface AuctionForViewService {
    List<AuctionForView> getAllAuctionsByRealmId(Long realmId);
}
