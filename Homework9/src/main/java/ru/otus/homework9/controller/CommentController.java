package ru.otus.homework9.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework9.dto.RequestCommentDto;
import ru.otus.homework9.dto.ResponseCommentDto;
import ru.otus.homework9.service.CommentService;
import ru.otus.homework9.service.exception.CommentNotExistsException;

import java.util.List;

@RestController
@RequestMapping("comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<ResponseCommentDto> addComment(@RequestBody RequestCommentDto requestCommentDto) {
        return ResponseEntity.ok(commentService.insert(requestCommentDto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseCommentDto> editComment(@PathVariable("id") Long id,
                                                         @RequestBody RequestCommentDto requestCommentDto) {
        return ResponseEntity.ok(commentService.update(id, requestCommentDto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Long> removeComment(@PathVariable("id") Long id) {
        return ResponseEntity.ok(commentService.delete(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<ResponseCommentDto>> getAllComments() {
        return ResponseEntity.ok(commentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseCommentDto> getCommentById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(commentService.getById(id));
    }

    @ExceptionHandler(CommentNotExistsException.class)
    public ResponseEntity<String> commentNotExists(CommentNotExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
