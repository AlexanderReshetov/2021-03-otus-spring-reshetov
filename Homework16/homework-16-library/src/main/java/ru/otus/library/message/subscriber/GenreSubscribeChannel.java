package ru.otus.library.message.subscriber;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface GenreSubscribeChannel {
    @Input(GenreSubscribeChannelConstants.MSG_GENRE)
    SubscribableChannel receiveGenre();
}
