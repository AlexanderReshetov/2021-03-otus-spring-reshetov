package ru.otus.homework7.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework7.model.Comment;
import ru.otus.homework7.repository.BookDaoJpa;
import ru.otus.homework7.repository.CommentDaoJpa;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentDaoJpa commentDaoJpa;
    private final BookDaoJpa bookDaoJpa;
    private final PrintService printService;

    public CommentServiceImpl(CommentDaoJpa commentDaoJpa, BookDaoJpa bookDaoJpa, PrintService printService) {
        this.commentDaoJpa = commentDaoJpa;
        this.bookDaoJpa = bookDaoJpa;
        this.printService = printService;
    }

    @Transactional
    public void insert(Long bookId, String text) {
        printService.println("New id = " + commentDaoJpa.insert(new Comment(bookDaoJpa.findById(bookId), text)).toString());
    }

    @Transactional
    public void update(Long id, Long bookId, String text) {
        commentDaoJpa.update(new Comment(id, bookDaoJpa.findById(bookId), text));
    }

    @Transactional
    public void delete(Long id) {
        commentDaoJpa.delete(id);
    }

    @Transactional(readOnly = true)
    public void printAll() {
        final List<Comment> commentList = commentDaoJpa.findAll();
        int index = 0;
        for(Comment comment: commentList) {
            printService.println("Comment " + (index + 1));
            printComment(comment);
            index++;
        }
    }

    @Transactional(readOnly = true)
    public void printById(Long id) {
        printComment(commentDaoJpa.findById(id));
    }

    private void printComment(Comment comment) {
        printService.println("Id = " + comment.getId());
        printService.println("Book id = " + comment.getBook().getId());
        printService.println("Book name = " + comment.getBook().getName());
        printService.println("Text = " + comment.getText());
    }
}
