package ru.otus.main.service;

import ru.otus.main.dto.ResponseAuctionDto;

public interface AuctionSubscribeListenerService {
    void receiveAuction(ResponseAuctionDto responseAuctionDto);
}