package ru.otus.homework8.service;

import org.springframework.stereotype.Service;
import ru.otus.homework8.repository.AuthorRepository;
import ru.otus.homework8.repository.BookRepository;
import ru.otus.homework8.repository.GenreRepository;
import ru.otus.homework8.model.Book;
import ru.otus.homework8.service.exception.AuthorNotExistsException;
import ru.otus.homework8.service.exception.BookNotExistsException;
import ru.otus.homework8.service.exception.GenreNotExistsException;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    public Book insert(String name, Long authorId, Long genreId) {
        return bookRepository.save(new Book(
                name,
                authorRepository.findById(authorId).orElseThrow(() -> new AuthorNotExistsException("There is no author with id = " + authorId)),
                genreRepository.findById(genreId).orElseThrow(() -> new GenreNotExistsException("There is no genre with id = " + genreId))));
    }

    public void update(Long id, String name, Long authorId, Long genreId) {
        bookRepository.save(new Book(
                id,
                name,
                authorRepository.findById(authorId).orElseThrow(() -> new AuthorNotExistsException("There is no author with id = " + authorId)),
                genreRepository.findById(genreId).orElseThrow(() -> new GenreNotExistsException("There is no genre with id = " + genreId))));
    }

    public void delete(Long id) {
        bookRepository.delete(bookRepository.findById(id).orElseThrow(() -> new BookNotExistsException("There is no book with id = " + id)));
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book getById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotExistsException("There is no book with id = " + id));
    }
}
