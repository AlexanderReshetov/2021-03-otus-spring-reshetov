package ru.otus.homework10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.homework10.dto.RequestBookDto;
import ru.otus.homework10.dto.ResponseBookDto;
import ru.otus.homework10.dto.ResponseCommentDto;
import ru.otus.homework10.service.BookService;

import java.util.List;

@Controller
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/insert")
    public ModelAndView getInsertPage(@RequestParam(value = "name", required = false) String name,
                                      @RequestParam(value = "authorId", required = false) Long authorId,
                                      @RequestParam(value = "genreId", required = false) Long genreId) {
        ModelAndView modelAndView;
        if (name == null) {
            modelAndView = new ModelAndView("insert-book");
        } else {
            ResponseBookDto responseBookDto = bookService.insert(new RequestBookDto(name, authorId, genreId));
            if (responseBookDto != null) {
                modelAndView = new ModelAndView("show-book");
                modelAndView.addObject("book", bookService.getById(responseBookDto.getId()));
            } else {
                modelAndView = new ModelAndView("error");
                modelAndView.addObject("message", "There was the error during inserting new book!");
            }
        }
        return modelAndView;
    }

    @GetMapping("/books/update")
    public ModelAndView getUpdatePage(@RequestParam(value = "id", required = false) Long id,
                                      @RequestParam(value = "name", required = false) String name,
                                      @RequestParam(value = "authorId", required = false) Long authorId,
                                      @RequestParam(value = "genreId", required = false) Long genreId) {
        ModelAndView modelAndView;
        if (id == null) {
            modelAndView = new ModelAndView("update-book");
        } else {
            ResponseBookDto responseBookDto = bookService.update(id, new RequestBookDto(name, authorId, genreId));
            if (responseBookDto != null) {
                modelAndView = new ModelAndView("show-book");
                modelAndView.addObject("book", bookService.getById(responseBookDto.getId()));
            } else {
                modelAndView = new ModelAndView("error");
                modelAndView.addObject("message", "There was the error during updating the book!");
            }
        }
        return modelAndView;
    }

    @GetMapping("/books/delete")
    public ModelAndView getDeletePage(@RequestParam(value = "id", required = false) Long id) {
        ModelAndView modelAndView;
        if (id == null) {
            modelAndView = new ModelAndView("delete-book-id");
        } else {
            bookService.delete(id);
            modelAndView = new ModelAndView("delete-book");
            modelAndView.addObject("id", id);
        }
        return modelAndView;
    }

    @GetMapping("/books/show")
    public ModelAndView getShowPage(@RequestParam(value = "id", required = false) Long id) {
        ModelAndView modelAndView;
        if (id == null) {
            modelAndView = new ModelAndView("show-book-id");
        } else {
            final ResponseBookDto responseBookDto = bookService.getById(id);
            modelAndView = new ModelAndView("show-book");
            modelAndView.addObject("book", responseBookDto);
        }
        return modelAndView;
    }

    @GetMapping("/books/show-all")
    public ModelAndView getShowAllPage() {
        final ModelAndView modelAndView = new ModelAndView("show-all-books");
        modelAndView.addObject("books", bookService.getAll());
        return modelAndView;
    }

    @GetMapping("/books/show-comments")
    public ModelAndView getShowCommentsPage(@RequestParam(value = "id", required = false) Long id) {
        ModelAndView modelAndView;
        if (id == null) {
            modelAndView = new ModelAndView("show-comments-for-book-id");
        } else {
            final List<ResponseCommentDto> responseCommentDtoList = bookService.getCommentsById(id);
            modelAndView = new ModelAndView("show-comments-for-book");
            modelAndView.addObject("comments", responseCommentDtoList);
        }
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(Exception e) {
        final ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }
}
