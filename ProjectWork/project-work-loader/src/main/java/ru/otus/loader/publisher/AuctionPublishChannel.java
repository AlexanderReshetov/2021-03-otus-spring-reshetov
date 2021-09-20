package ru.otus.loader.publisher;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface AuctionPublishChannel {
    @Output(AuctionPublishChannelConstants.MSG_AUCTION)
    MessageChannel AuctionSend();
}