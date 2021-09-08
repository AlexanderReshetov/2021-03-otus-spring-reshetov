package ru.otus.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.library.dto.ResponseGenreDto;

import java.util.List;

@Service
public class GenreSubscriberListenerServiceImpl implements GenreSubscribeListenerService {
    private final GenreService genreService;

    @Autowired
    public GenreSubscriberListenerServiceImpl(GenreService genreService) {
        this.genreService = genreService;
    }

    public void receiveGenreList(List<ResponseGenreDto> responseGenreDtoList) {
        genreService.saveAll(responseGenreDtoList);
    }
}
