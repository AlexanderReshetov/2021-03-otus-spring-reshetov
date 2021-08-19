package ru.otus.homework8.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework8.model.Book;
import ru.otus.homework8.model.Comment;

import java.util.List;

@Service
public class BookRunnerServiceImpl implements BookRunnerService {
    private final BookService bookService;
    private final PrintService printService;

    public BookRunnerServiceImpl(BookService bookService, PrintService printService) {
        this.bookService = bookService;
        this.printService = printService;
    }

    @Transactional
    public void insert(String name, Long authorId, Long genreId) {
        printService.println("New id = " + bookService.insert(name, authorId, genreId).getId());
    }

    @Transactional
    public void update(Long id, String name, Long authorId, Long genreId) {
        bookService.update(id, name, authorId, genreId);
    }

    @Transactional
    public void delete(Long id) {
        bookService.delete(id);
    }

    @Transactional(readOnly = true)
    public void printAll() {
        final List<Book> bookList = bookService.getAll();
        int index = 0;
        for (Book book : bookList) {
            printService.println("Book " + (index + 1));
            printBook(book);
            index++;
        }
    }

    @Transactional(readOnly = true)
    public void printById(Long id) {
        printBook(bookService.getById(id));
    }

    private void printBook(Book book) {
        printService.println("Id = " + book.getId());
        printService.println("Name = " + book.getName());
        printService.println("Author id = " + book.getAuthor().getId());
        printService.println("Author name = " + book.getAuthor().getName());
        printService.println("Genre id = " + book.getGenre().getId());
        printService.println("Genre name = " + book.getGenre().getName());
        int index = 0;
        if (book.getComments() != null) {
            for (Comment comment : book.getComments()) {
                printService.println("Comment " + (index + 1));
                printService.println("Comment id = " + comment.getId());
                printService.println("Comment text = " + comment.getText());
                index++;
            }
        }
    }
}
