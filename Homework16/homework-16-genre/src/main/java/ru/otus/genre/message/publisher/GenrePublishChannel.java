package ru.otus.genre.message.publisher;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface GenrePublishChannel {
    @Output(GenrePublishChannelConstants.MSG_GENRE)
    MessageChannel GenreSend();
}