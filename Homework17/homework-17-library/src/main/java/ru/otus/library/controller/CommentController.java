package ru.otus.library.controller;

import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.dto.RequestCommentDto;
import ru.otus.library.dto.ResponseCommentDto;
import ru.otus.library.service.CommentService;
import ru.otus.library.service.exception.CommentNotExistsException;

import java.util.List;

@RestController
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Timed("REST_POST_COMMENT")
    @PostMapping("/comments")
    public ResponseEntity<ResponseCommentDto> addComment(@RequestBody RequestCommentDto requestCommentDto) {
        return ResponseEntity.ok(commentService.insert(requestCommentDto));
    }

    @Timed("REST_PUT_COMMENT")
    @PutMapping(value = "/comments/{id}")
    public ResponseEntity<ResponseCommentDto> editComment(@PathVariable("id") Long id,
                                                         @RequestBody RequestCommentDto requestCommentDto) {
        return ResponseEntity.ok(commentService.update(id, requestCommentDto));
    }

    @Timed("REST_DELETE_COMMENT")
    @DeleteMapping(value = "/comments/{id}")
    public ResponseEntity<Long> removeComment(@PathVariable("id") Long id) {
        return ResponseEntity.ok(commentService.delete(id));
    }

    @Timed("REST_GET_ALL_COMMENTS")
    @GetMapping("/comments/")
    public ResponseEntity<List<ResponseCommentDto>> getAllComments() {
        return ResponseEntity.ok(commentService.getAll());
    }

    @Timed("REST_GET_COMMENT_BY_ID")
    @GetMapping("/comments/{id}")
    public ResponseEntity<ResponseCommentDto> getCommentById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(commentService.getById(id));
    }

    @ExceptionHandler(CommentNotExistsException.class)
    public ResponseEntity<String> commentNotExists(CommentNotExistsException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
