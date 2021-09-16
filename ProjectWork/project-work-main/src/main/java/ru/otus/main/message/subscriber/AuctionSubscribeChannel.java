package ru.otus.main.message.subscriber;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface AuctionSubscribeChannel {
    @Input(AuctionSubscribeChannelConstants.MSG_AUCTION)
    SubscribableChannel receiveAuction();
}
