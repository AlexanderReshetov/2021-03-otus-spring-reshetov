package ru.otus.homework10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.homework10.dto.RequestCommentDto;
import ru.otus.homework10.dto.ResponseCommentDto;
import ru.otus.homework10.service.CommentService;

@Controller
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments/add")
    public ModelAndView getAddPage(@RequestParam(value = "bookId", required = false) Long bookId,
                                      @RequestParam(value = "text", required = false) String text) {
        ModelAndView modelAndView;
        if (text == null) {
            modelAndView = new ModelAndView("add-comment");
        } else {
            ResponseCommentDto responseCommentDto = commentService.insert(new RequestCommentDto(bookId, text));
            if (responseCommentDto != null) {
                modelAndView = new ModelAndView("show-comment");
                modelAndView.addObject("comment", commentService.getById(responseCommentDto.getId()));
            } else {
                modelAndView = new ModelAndView("error");
                modelAndView.addObject("message", "There was the error during adding new comment!");
            }
        }
        return modelAndView;
    }

    @GetMapping("/comments/edit")
    public ModelAndView getEditPage(@RequestParam(value = "id", required = false) Long id,
                                      @RequestParam(value = "bookId", required = false) Long bookId,
                                      @RequestParam(value = "text", required = false) String text) {
        ModelAndView modelAndView;
        if (id == null) {
            modelAndView = new ModelAndView("edit-comment");
        } else {
            ResponseCommentDto responseCommentDto = commentService.update(id, new RequestCommentDto(bookId, text));
            if (responseCommentDto != null) {
                modelAndView = new ModelAndView("show-comment");
                modelAndView.addObject("comment", commentService.getById(responseCommentDto.getId()));
            } else {
                modelAndView = new ModelAndView("error");
                modelAndView.addObject("message", "There was the error during editing the comment!");
            }
        }
        return modelAndView;
    }

    @GetMapping("/comments/remove")
    public ModelAndView getRemovePage(@RequestParam(value = "id", required = false) Long id) {
        ModelAndView modelAndView;
        if (id == null) {
            modelAndView = new ModelAndView("remove-comment-id");
        } else {
            commentService.delete(id);
            modelAndView = new ModelAndView("remove-comment");
            modelAndView.addObject("id", id);
        }
        return modelAndView;
    }

    @GetMapping("/comments/show")
    public ModelAndView getShowPage(@RequestParam(value = "id", required = false) Long id) {
        ModelAndView modelAndView;
        if (id == null) {
            modelAndView = new ModelAndView("show-comment-id");
        } else {
            final ResponseCommentDto responseCommentDto = commentService.getById(id);
            modelAndView = new ModelAndView("show-comment");
            modelAndView.addObject("comment", responseCommentDto);
        }
        return modelAndView;
    }

    @GetMapping("/comments/show-all")
    public ModelAndView getShowAllPage() {
        final ModelAndView modelAndView = new ModelAndView("show-all-comments");
        modelAndView.addObject("comments", commentService.getAll());
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(Exception e) {
        final ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }
}
