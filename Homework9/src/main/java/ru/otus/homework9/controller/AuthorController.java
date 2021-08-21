package ru.otus.homework9.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework9.dto.ResponseAuthorDto;
import ru.otus.homework9.service.AuthorService;
import ru.otus.homework9.service.exception.AuthorNotExistsException;

import java.util.List;

@RestController
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors/")
    public ResponseEntity<List<ResponseAuthorDto>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAll());
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<ResponseAuthorDto> getAuthorById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(authorService.getById(id));
    }

    @ExceptionHandler(AuthorNotExistsException.class)
    public ResponseEntity<String> authorNotExists(AuthorNotExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}