package ru.otus.main.message.subscriber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;
import ru.otus.main.dto.ResponseAuctionDto;
import ru.otus.main.service.AuctionSubscribeListenerService;

@Configuration
@ConditionalOnProperty(prefix = "kafka", value = "enabled", havingValue = "true", matchIfMissing = true)
public class AuctionSubscribeListener {
    private final AuctionSubscribeListenerService auctionSubscribeListenerService;

    @Autowired
    public AuctionSubscribeListener(AuctionSubscribeListenerService auctionSubscribeListenerService) {
        this.auctionSubscribeListenerService = auctionSubscribeListenerService;
    }

    @StreamListener(AuctionSubscribeChannelConstants.MSG_AUCTION)
    void receiveAuction(ResponseAuctionDto msg) {
        auctionSubscribeListenerService.receiveAuction(msg);
    }
}
