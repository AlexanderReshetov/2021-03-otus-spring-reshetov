package ru.otus.homework10.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
    @GetMapping("/")
    public ModelAndView getMainPage() {
        return new ModelAndView("index");
    }

    @GetMapping("/index")
    public ModelAndView getIndexPage() {
        return new ModelAndView("index");
    }

    @PostMapping("/")
    public ModelAndView postLogin() {
        return new ModelAndView("index");
    }
}
