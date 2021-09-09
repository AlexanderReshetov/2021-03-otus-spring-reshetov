package ru.otus.library.controller;

import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.dto.RequestBookDto;
import ru.otus.library.dto.ResponseBookDto;
import ru.otus.library.dto.ResponseCommentDto;
import ru.otus.library.service.BookService;
import ru.otus.library.service.exception.BookNotExistsException;

import java.util.List;

@RestController
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Timed("REST_POST_BOOK")
    @PostMapping("/books")
    public ResponseEntity<ResponseBookDto> insertBook(@RequestBody RequestBookDto requestBookDto) {
        return ResponseEntity.ok(bookService.insert(requestBookDto));
    }

    @Timed("REST_PUT_BOOK")
    @PutMapping(value = "/books/{id}")
    public ResponseEntity<ResponseBookDto> updateBook(@PathVariable("id") Long id,
                                                      @RequestBody RequestBookDto requestBookDto) {
        return ResponseEntity.ok(bookService.update(id, requestBookDto));
    }

    @Timed("REST_DELETE_BOOK")
    @DeleteMapping(value = "/books/{id}")
    public ResponseEntity<Long> deleteBook(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookService.delete(id));
    }

    @Timed("REST_GET_ALL_BOOKS")
    @GetMapping("/books/")
    public ResponseEntity<List<ResponseBookDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAll());
    }

    @Timed("REST_GET_BOOK_BY_ID")
    @GetMapping("/books/{id}")
    public ResponseEntity<ResponseBookDto> getBookById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookService.getById(id));
    }

    @Timed("REST_GET_ALL_COMMENTS_BY_BOOK_ID")
    @GetMapping("/books/{bookId}/comments/")
    public ResponseEntity<List<ResponseCommentDto>> getCommentsById(@PathVariable("bookId") Long id) {
        return ResponseEntity.ok(bookService.getCommentsById(id));
    }

    @ExceptionHandler(BookNotExistsException.class)
    public ResponseEntity<String> bookNotExists(BookNotExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
