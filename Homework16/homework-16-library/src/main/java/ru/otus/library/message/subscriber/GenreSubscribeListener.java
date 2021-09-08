package ru.otus.library.message.subscriber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;
import ru.otus.library.dto.ResponseGenreDto;
import ru.otus.library.service.GenreSubscribeListenerService;

import java.util.List;

@Configuration
@ConditionalOnProperty(prefix = "kafka", value = "enabled", havingValue = "true", matchIfMissing = true)
public class GenreSubscribeListener {
    private final GenreSubscribeListenerService genreSubscribeListenerService;

    @Autowired
    public GenreSubscribeListener(GenreSubscribeListenerService genreSubscribeListenerService) {
        this.genreSubscribeListenerService = genreSubscribeListenerService;
    }

    @StreamListener(GenreSubscribeChannelConstants.MSG_GENRE)
    void receiveGenre(List<ResponseGenreDto> msg) {
        genreSubscribeListenerService.receiveGenreList(msg);
    }
}
