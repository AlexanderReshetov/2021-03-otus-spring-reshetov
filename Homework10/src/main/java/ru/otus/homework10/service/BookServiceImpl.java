package ru.otus.homework10.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework10.dto.RequestBookDto;
import ru.otus.homework10.dto.ResponseBookDto;
import ru.otus.homework10.dto.ResponseCommentDto;
import ru.otus.homework10.repository.AuthorRepository;
import ru.otus.homework10.repository.BookRepository;
import ru.otus.homework10.repository.GenreRepository;
import ru.otus.homework10.domain.Book;
import ru.otus.homework10.service.exception.AuthorNotExistsException;
import ru.otus.homework10.service.exception.BookNotExistsException;
import ru.otus.homework10.service.exception.GenreNotExistsException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Transactional
    public ResponseBookDto insert(RequestBookDto requestBookDto) {
        return ResponseBookDto.toDto(bookRepository.save(new Book(
                requestBookDto.getName(),
                authorRepository.findById(requestBookDto.getAuthorId()).orElseThrow(() -> new AuthorNotExistsException("There is no author with id = " + requestBookDto.getAuthorId())),
                genreRepository.findById(requestBookDto.getGenreId()).orElseThrow(() -> new GenreNotExistsException("There is no genre with id = " + requestBookDto.getGenreId())))));
    }

    @Transactional
    public ResponseBookDto update(Long id, RequestBookDto requestBookDto) {
        return ResponseBookDto.toDto(bookRepository.save(new Book(
                id,
                requestBookDto.getName(),
                authorRepository.findById(requestBookDto.getAuthorId()).orElseThrow(() -> new AuthorNotExistsException("There is no author with id = " + requestBookDto.getAuthorId())),
                genreRepository.findById(requestBookDto.getGenreId()).orElseThrow(() -> new GenreNotExistsException("There is no genre with id = " + requestBookDto.getGenreId())))));
    }

    @Transactional
    public Long delete(Long id) {
        bookRepository.delete(bookRepository.findById(id).orElseThrow(() -> new BookNotExistsException("There is no book with id = " + id)));
        return id;
    }

    @Transactional(readOnly = true)
    public List<ResponseBookDto> getAll() {
        return bookRepository.findAll().stream().map(ResponseBookDto::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ResponseBookDto getById(Long id) {
        return ResponseBookDto.toDto(bookRepository.findById(id).orElseThrow(() -> new BookNotExistsException("There is no book with id = " + id)));
    }

    @Transactional(readOnly = true)
    public List<ResponseCommentDto> getCommentsById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotExistsException("There is no book with id = " + id))
                .getComments().stream().map(ResponseCommentDto::toDto).collect(Collectors.toList());
    }
}
