package ru.otus.homework7.repository;

import ru.otus.homework7.model.Comment;

import java.util.List;

public interface CommentDaoJpa {
    Long insert(Comment comment);
    void update(Comment comment);
    void delete(Long id);
    List<Comment> findAll();
    Comment findById(Long id);
}
