package ru.otus.loader.publisher;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.loader.dto.ResponseAuctionDto;

@MessagingGateway
public interface AuctionPublishGateway {
    @Gateway(requestChannel = AuctionPublishChannelConstants.MSG_AUCTION)
    void auctionPublish(ResponseAuctionDto msg);
}

