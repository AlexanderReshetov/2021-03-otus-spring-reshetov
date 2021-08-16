package ru.otus.homework6.service;

import org.springframework.stereotype.Service;
import ru.otus.homework6.dao.AuthorDao;
import ru.otus.homework6.dao.BookDao;
import ru.otus.homework6.dao.GenreDao;
import ru.otus.homework6.domain.Book;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final PrintService printService;

    public BookServiceImpl(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao, PrintService printService) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.printService = printService;
    }

    public void insert(String name, Long authorId, Long genreId) {
        bookDao.insert(new Book(name, authorDao.findById(authorId), genreDao.findById(genreId)));
    }

    public void update(Long id, String name, Long authorId, Long genreId) {
        bookDao.update(new Book(id, name, authorDao.findById(authorId), genreDao.findById(genreId)));
    }

    public void delete(Long id) {
        bookDao.delete(id);
    }

    public void printAll() {
        final List<Book> bookList = bookDao.findAll();
        int index = 0;
        for(Book book: bookList) {
            printService.println("Book " + (index + 1));
            printBook(book);
            index++;
        }
    }

    public void printById(Long id) {
        printBook(bookDao.findById(id));
    }

    private void printBook(Book book) {
        printService.println("Id = " + book.getId());
        printService.println("Name = " + book.getName());
        printService.println("Author id = " + book.getAuthor().getId());
        printService.println("Author name = " + book.getAuthor().getName());
        printService.println("Genre id = " + book.getGenre().getId());
        printService.println("Genre name = " + book.getGenre().getName());
    }
}
