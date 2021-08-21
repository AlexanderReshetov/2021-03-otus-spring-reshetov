package ru.otus.homework9.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework9.dto.ResponseGenreDto;
import ru.otus.homework9.repository.GenreRepository;
import ru.otus.homework9.service.exception.GenreNotExistsException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<ResponseGenreDto> getAll() {
        return genreRepository.findAll().stream().map(ResponseGenreDto::toDto).collect(Collectors.toList());
    }

    public ResponseGenreDto getById(Long id) {
        return ResponseGenreDto.toDto(genreRepository.findById(id).orElseThrow(() -> new GenreNotExistsException("There is no genre with id = " + id)));
    }
}
