package ru.otus.homework10.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework10.domain.Comment;
import ru.otus.homework10.dto.RequestCommentDto;
import ru.otus.homework10.dto.ResponseCommentDto;
import ru.otus.homework10.repository.BookRepository;
import ru.otus.homework10.repository.CommentRepository;
import ru.otus.homework10.service.exception.BookNotExistsException;
import ru.otus.homework10.service.exception.CommentNotExistsException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public ResponseCommentDto insert(RequestCommentDto requestCommentDto) {
        return ResponseCommentDto.toDto(commentRepository.save(new Comment(
                bookRepository.findById(requestCommentDto.getBookId()).orElseThrow(() -> new BookNotExistsException("There is no book with id = " + requestCommentDto.getBookId())),
                requestCommentDto.getText())));
    }

    @Transactional
    public ResponseCommentDto update(Long id, RequestCommentDto requestCommentDto) {
        return ResponseCommentDto.toDto(commentRepository.save(new Comment(
                id,
                bookRepository.findById(requestCommentDto.getBookId()).orElseThrow(() -> new BookNotExistsException("There is no book with id = " + requestCommentDto.getBookId())),
                requestCommentDto.getText())));
    }

    @Transactional
    public Long delete(Long id) {
        commentRepository.delete(commentRepository.findById(id).orElseThrow(() -> new CommentNotExistsException("There is no comment with id = " + id)));
        return id;
    }

    @Transactional(readOnly = true)
    public List<ResponseCommentDto> getAll() {
        return commentRepository.findAll().stream().map(ResponseCommentDto::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ResponseCommentDto getById(Long id) {
        return ResponseCommentDto.toDto(commentRepository.findById(id).orElseThrow(() -> new CommentNotExistsException("There is no comment with id = " + id)));
    }
}
