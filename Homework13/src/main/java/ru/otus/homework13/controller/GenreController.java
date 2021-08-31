package ru.otus.homework13.controller;

import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework13.dto.ResponseGenreDto;
import ru.otus.homework13.service.GenreService;
import ru.otus.homework13.service.exception.GenreNotExistsException;

import java.util.List;

@RestController
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @Timed("REST_GET_ALL_GENRES")
    @GetMapping("/genres/")
    public ResponseEntity<List<ResponseGenreDto>> getAllGenres() {
        return ResponseEntity.ok(genreService.getAll());
    }

    @Timed("REST_GET_GENRE_BY_ID")
    @GetMapping("/genres/{id}")
    public ResponseEntity<ResponseGenreDto> getGenreById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(genreService.getById(id));
    }

    @ExceptionHandler(GenreNotExistsException.class)
    public ResponseEntity<String> genreNotExists(GenreNotExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}