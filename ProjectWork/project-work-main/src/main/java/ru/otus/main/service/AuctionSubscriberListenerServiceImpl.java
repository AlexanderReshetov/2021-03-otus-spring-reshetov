package ru.otus.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.main.dto.ResponseAuctionDto;

@Service
public class AuctionSubscriberListenerServiceImpl implements AuctionSubscribeListenerService {
    private final AuctionService auctionService;

    @Autowired
    public AuctionSubscriberListenerServiceImpl(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    public void receiveAuction(ResponseAuctionDto responseAuctionDto) {
        auctionService.save(responseAuctionDto);
    }
}
