package ru.otus.homework11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework11.dto.RequestCommentDto;
import ru.otus.homework11.dto.ResponseCommentDto;
import ru.otus.homework11.service.CommentService;
import ru.otus.homework11.service.exception.CommentNotExistsException;

import java.util.List;

@RestController
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comments")
    public ResponseEntity<ResponseCommentDto> addComment(@RequestBody RequestCommentDto requestCommentDto) {
        return ResponseEntity.ok(commentService.insert(requestCommentDto));
    }

    @PutMapping(value = "/comments/{id}")
    public ResponseEntity<ResponseCommentDto> editComment(@PathVariable("id") Long id,
                                                         @RequestBody RequestCommentDto requestCommentDto) {
        return ResponseEntity.ok(commentService.update(id, requestCommentDto));
    }

    @DeleteMapping(value = "/comments/{id}")
    public ResponseEntity<Long> removeComment(@PathVariable("id") Long id) {
        return ResponseEntity.ok(commentService.delete(id));
    }

    @GetMapping("/comments/")
    public ResponseEntity<List<ResponseCommentDto>> getAllComments() {
        return ResponseEntity.ok(commentService.getAll());
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<ResponseCommentDto> getCommentById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(commentService.getById(id));
    }

    @ExceptionHandler(CommentNotExistsException.class)
    public ResponseEntity<String> commentNotExists(CommentNotExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
