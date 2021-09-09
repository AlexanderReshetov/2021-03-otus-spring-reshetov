package ru.otus.genre.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.genre.dto.ResponseGenreDto;
import ru.otus.genre.message.publisher.GenrePublishGateway;
import ru.otus.genre.repository.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {
    private final GenreRepository genreRepository;
    private final GenrePublishGateway genrePublishGateway;

    @Autowired
    public GenreService(GenreRepository genreRepository, GenrePublishGateway genrePublishGateway) {
        this.genreRepository = genreRepository;
        this.genrePublishGateway = genrePublishGateway;
    }

    public void broadcastGenres() {
        List<ResponseGenreDto> responseGenreDtoList = genreRepository.findAll().stream().map(ResponseGenreDto::toDto).collect(Collectors.toList());
        genrePublishGateway.genrePublish(responseGenreDtoList);
    }
}
