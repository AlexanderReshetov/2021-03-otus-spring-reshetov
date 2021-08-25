package ru.otus.homework10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.homework10.dto.ResponseAuthorDto;
import ru.otus.homework10.service.AuthorService;

@Controller
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
    @GetMapping("/authors/show")
    public ModelAndView getShowPage(@RequestParam(value = "id", required = false) Long id) {
        ModelAndView modelAndView;
        if (id == null) {
            modelAndView = new ModelAndView("show-author-id");
        } else {
            final ResponseAuthorDto responseAuthorDto = authorService.getById(id);
            modelAndView = new ModelAndView("show-author");
            modelAndView.addObject("author", responseAuthorDto);
        }
        return modelAndView;
    }

    @GetMapping("/authors/show-all")
    public ModelAndView getShowAllPage() {
        final ModelAndView modelAndView = new ModelAndView("show-all-authors");
        modelAndView.addObject("authors", authorService.getAll());
        return modelAndView;
    }
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(Exception e) {
        final ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }
}
