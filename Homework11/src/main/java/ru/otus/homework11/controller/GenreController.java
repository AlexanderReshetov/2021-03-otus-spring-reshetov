package ru.otus.homework11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework11.dto.ResponseGenreDto;
import ru.otus.homework11.service.GenreService;
import ru.otus.homework11.service.exception.GenreNotExistsException;

import java.util.List;

@RestController
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres/")
    public ResponseEntity<List<ResponseGenreDto>> getAllGenres() {
        return ResponseEntity.ok(genreService.getAll());
    }

    @GetMapping("/genres/{id}")
    public ResponseEntity<ResponseGenreDto> getGenreById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(genreService.getById(id));
    }

    @ExceptionHandler(GenreNotExistsException.class)
    public ResponseEntity<String> genreNotExists(GenreNotExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}