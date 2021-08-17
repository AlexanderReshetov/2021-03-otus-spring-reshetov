package ru.otus.homework7.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework7.model.Comment;
import ru.otus.homework7.repository.AuthorDaoJpa;
import ru.otus.homework7.repository.BookDaoJpa;
import ru.otus.homework7.repository.GenreDaoJpa;
import ru.otus.homework7.model.Book;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookDaoJpa bookDaoJpa;
    private final AuthorDaoJpa authorDaoJpa;
    private final GenreDaoJpa genreDaoJpa;
    private final PrintService printService;

    public BookServiceImpl(BookDaoJpa bookDaoJpa, AuthorDaoJpa authorDaoJpa, GenreDaoJpa genreDaoJpa, PrintService printService) {
        this.bookDaoJpa = bookDaoJpa;
        this.authorDaoJpa = authorDaoJpa;
        this.genreDaoJpa = genreDaoJpa;
        this.printService = printService;
    }

    @Transactional
    public void insert(String name, Long authorId, Long genreId) {
        printService.println("New id = " + bookDaoJpa.insert(new Book(name, authorDaoJpa.findById(authorId), genreDaoJpa.findById(genreId))).toString());
    }

    @Transactional
    public void update(Long id, String name, Long authorId, Long genreId) {
        bookDaoJpa.update(new Book(id, name, authorDaoJpa.findById(authorId), genreDaoJpa.findById(genreId)));
    }

    @Transactional
    public void delete(Long id) {
        bookDaoJpa.delete(id);
    }

    @Transactional(readOnly = true)
    public void printAll() {
        final List<Book> bookList = bookDaoJpa.findAll();
        int index = 0;
        for(Book book: bookList) {
            printService.println("Book " + (index + 1));
            printBook(book);
            index++;
        }
    }

    @Transactional(readOnly = true)
    public void printById(Long id) {
        printBook(bookDaoJpa.findById(id));
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
