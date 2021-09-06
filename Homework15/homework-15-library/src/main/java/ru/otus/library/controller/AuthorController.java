package ru.otus.library.controller;

import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.library.dto.ResponseAuthorDto;
import ru.otus.library.service.AuthorService;
import ru.otus.library.service.LoadAuthorsService;
import ru.otus.library.service.exception.AuthenticationException;
import ru.otus.library.service.exception.AuthorNotExistsException;

import java.util.Collections;
import java.util.List;

@RestController
public class AuthorController {
    private final AuthorService authorService;
    private final LoadAuthorsService loadAuthorsService;

    @Autowired
    public AuthorController(AuthorService authorService,
                            LoadAuthorsService loadAuthorsService) {
        this.authorService = authorService;
        this.loadAuthorsService = loadAuthorsService;
    }

    @Timed("REST_GET_ALL_AUTHORS")
    @GetMapping("/authors/")
    public ResponseEntity<List<ResponseAuthorDto>> getAllAuthors() {
        try {
            return ResponseEntity.ok(loadAuthorsService.getAllAuthors());
        }
        catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }

    @Timed("REST_GET_AUTHOR_BY_ID")
    @GetMapping("/authors/{id}")
    public ResponseEntity<ResponseAuthorDto> getAuthorById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(authorService.getById(id));
    }

    @ExceptionHandler(AuthorNotExistsException.class)
    public ResponseEntity<String> authorNotExists(AuthorNotExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}