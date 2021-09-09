package ru.otus.genre.message.publisher;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.genre.dto.ResponseGenreDto;

import java.util.List;

@MessagingGateway
public interface GenrePublishGateway {
    @Gateway(requestChannel = GenrePublishChannelConstants.MSG_GENRE)
    void genrePublish(List<ResponseGenreDto> msg);
}

