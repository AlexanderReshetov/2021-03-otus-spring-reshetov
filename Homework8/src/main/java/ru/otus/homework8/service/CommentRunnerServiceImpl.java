package ru.otus.homework8.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework8.model.Comment;

import java.util.List;

@Service
public class CommentRunnerServiceImpl implements CommentRunnerService {
    private final CommentService commentService;
    private final PrintService printService;

    public CommentRunnerServiceImpl(CommentService commentService, PrintService printService) {
        this.commentService = commentService;
        this.printService = printService;
    }

    @Transactional
    public void insert(Long bookId, String text) {
        printService.println("New id = " + commentService.insert(bookId, text).getId());
    }

    @Transactional
    public void update(Long id, Long bookId, String text) {
        commentService.update(id, bookId, text);
    }

    @Transactional
    public void delete(Long id) {
        commentService.delete(id);
    }

    @Transactional(readOnly = true)
    public void printAll() {
        final List<Comment> commentList = commentService.getAll();
        int index = 0;
        for (Comment comment : commentList) {
            printService.println("Comment " + (index + 1));
            printComment(comment);
            index++;
        }
    }

    @Transactional(readOnly = true)
    public void printById(Long id) {
        printComment(commentService.getById(id));
    }

    private void printComment(Comment comment) {
        printService.println("Id = " + comment.getId());
        printService.println("Book id = " + comment.getBook().getId());
        printService.println("Book name = " + comment.getBook().getName());
        printService.println("Text = " + comment.getText());
    }
}
