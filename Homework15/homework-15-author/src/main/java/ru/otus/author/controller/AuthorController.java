package ru.otus.author.controller;

import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.author.service.AuthorService;
import ru.otus.author.dto.ResponseAuthorDto;
import ru.otus.author.service.exception.AuthorNotExistsException;

import java.util.List;

@RestController
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Timed("REST_GET_ALL_AUTHORS")
    @GetMapping("/authors/")
    public ResponseEntity<List<ResponseAuthorDto>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAll());
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