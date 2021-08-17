package ru.otus.homework7.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework7.service.BookService;
import ru.otus.homework7.service.CommentService;

@ShellComponent
public class ShellCommands {
    private final BookService bookService;
    private final CommentService commentService;

    @Autowired
    public ShellCommands(BookService bookService, CommentService commentService) {
        this.bookService = bookService;
        this.commentService = commentService;
    }

    @ShellMethod(key = "insert", value = "Insert book with <Book name> <Author id> <Genre id>")
    public void insert(@ShellOption("name") String name, @ShellOption("authorId") Long authorId, @ShellOption("genreId") Long genreId) {
        bookService.insert(name, authorId, genreId);
    }

    @ShellMethod(key = "update", value = "Update book by <Book id> with <Book name> <Author id> <Genre id>")
    public void update(@ShellOption("id") Long id, @ShellOption("name") String name, @ShellOption("authorId") Long authorId, @ShellOption("genreId") Long genreId) {
        bookService.update(id, name, authorId, genreId);
    }

    @ShellMethod(key = "delete", value = "Delete book by <id>")
    public void deleteById(@ShellOption("id") Long id) {
        bookService.delete(id);
    }

    @ShellMethod(key = "show all", value = "Show all books")
    public void showAll() {
        bookService.printAll();
    }

    @ShellMethod(key = "show", value = "Show book by <id>")
    public void showById(@ShellOption("id") Long id) {
        bookService.printById(id);
    }

    @ShellMethod(key = "add comment", value = "Add comment for <Book id> with <Text>")
    public void addComment(@ShellOption("book_id") Long bookId, @ShellOption("text") String text) {
        commentService.insert(bookId, text);
    }

    @ShellMethod(key = "edit comment", value = "Edit comment by <id> with <Book id>, <Text>")
    public void editComment(@ShellOption("id") Long id, @ShellOption("book_id") Long bookId, @ShellOption("text") String text) {
        commentService.update(id, bookId, text);
    }

    @ShellMethod(key = "remove comment", value = "Remove comment by <id>")
    public void removeComment(@ShellOption("id") Long id) {
        commentService.delete(id);
    }

    @ShellMethod(key = "show all comments", value = "Show all comments")
    public void showAllComments() {
        commentService.printAll();
    }

    @ShellMethod(key = "show comment", value = "Show comment by <id>")
    public void showCommentById(@ShellOption("id") Long id) {
        commentService.printById(id);
    }
}
