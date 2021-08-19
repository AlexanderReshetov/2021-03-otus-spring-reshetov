package ru.otus.homework9.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework9.dto.RequestBookDto;
import ru.otus.homework9.dto.ResponseBookDto;
import ru.otus.homework9.dto.ResponseCommentDto;
import ru.otus.homework9.service.BookService;
import ru.otus.homework9.service.exception.BookNotExistsException;

import java.util.List;

@RestController
@RequestMapping("books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<ResponseBookDto> insertBook(@RequestBody RequestBookDto requestBookDto) {
        return ResponseEntity.ok(bookService.insert(requestBookDto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseBookDto> updateBook(@PathVariable("id") Long id,
                                                      @RequestBody RequestBookDto requestBookDto) {
        return ResponseEntity.ok(bookService.update(id, requestBookDto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Long> deleteBook(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookService.delete(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<ResponseBookDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBookDto> getBookById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookService.getById(id));
    }

    @GetMapping("/{id}/comments/")
    public ResponseEntity<List<ResponseCommentDto>> getCommentsById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookService.getCommentsById(id));
    }

    @ExceptionHandler(BookNotExistsException.class)
    public ResponseEntity<String> bookNotExists(BookNotExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
