package ru.otus.homework8.service;

import org.springframework.stereotype.Service;
import ru.otus.homework8.model.Comment;
import ru.otus.homework8.repository.BookRepository;
import ru.otus.homework8.repository.CommentRepository;
import ru.otus.homework8.service.exception.BookNotExistsException;
import ru.otus.homework8.service.exception.CommentNotExistsException;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    public CommentServiceImpl(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    public Comment insert(Long bookId, String text) {
        return commentRepository.save(new Comment(
                bookRepository.findById(bookId).orElseThrow(() -> new BookNotExistsException("There is no book with id = " + bookId)),
                text));
    }

    public void update(Long id, Long bookId, String text) {
        commentRepository.save(new Comment(
                id,
                bookRepository.findById(bookId).orElseThrow(() -> new BookNotExistsException("There is no book with id = " + bookId)),
                text));
    }

    public void delete(Long id) {
        commentRepository.delete(commentRepository.findById(id).orElseThrow(() -> new CommentNotExistsException("There is no comment with id = " + id)));
    }

    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    public Comment getById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new CommentNotExistsException("There is no comment with id = " + id));
    }
}
