package ru.otus.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.repository.GenreRepository;
import ru.otus.library.dto.ResponseGenreDto;
import ru.otus.library.service.exception.GenreNotExistsException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Transactional(readOnly = true)
    public List<ResponseGenreDto> getAll() {
        return genreRepository.findAll().stream().map(ResponseGenreDto::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ResponseGenreDto getById(Long id) {
        return ResponseGenreDto.toDto(genreRepository.findById(id).orElseThrow(() -> new GenreNotExistsException("There is no genre with id = " + id)));
    }
}
