package ru.otus.homework10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.homework10.dto.ResponseGenreDto;
import ru.otus.homework10.service.GenreService;

@Controller
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres/show")
    public ModelAndView getShowPage(@RequestParam(value = "id", required = false) Long id) {
        ModelAndView modelAndView;
        if (id == null) {
            modelAndView = new ModelAndView("show-genre-id");
        } else {
            final ResponseGenreDto responseGenreDto = genreService.getById(id);
            modelAndView = new ModelAndView("show-genre");
            modelAndView.addObject("genre", responseGenreDto);
        }
        return modelAndView;
    }

    @GetMapping("/genres/show-all")
    public ModelAndView getShowAllPage() {
        final ModelAndView modelAndView = new ModelAndView("show-all-genres");
        modelAndView.addObject("genres", genreService.getAll());
        return modelAndView;
    }
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(Exception e) {
        final ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }
}
