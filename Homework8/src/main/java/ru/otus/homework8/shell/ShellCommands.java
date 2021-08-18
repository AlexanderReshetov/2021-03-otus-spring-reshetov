package ru.otus.homework8.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework8.service.BookRunnerService;
import ru.otus.homework8.service.CommentRunnerService;

@ShellComponent
public class ShellCommands {
    private final BookRunnerService bookRunnerService;
    private final CommentRunnerService commentRunnerService;

    @Autowired
    public ShellCommands(BookRunnerService bookRunnerService, CommentRunnerService commentRunnerService) {
        this.bookRunnerService = bookRunnerService;
        this.commentRunnerService = commentRunnerService;
    }

    @ShellMethod(key = "insert", value = "Insert book with <Book name> <Author id> <Genre id>")
    public void insert(@ShellOption("name") String name, @ShellOption("authorId") Long authorId, @ShellOption("genreId") Long genreId) {
        bookRunnerService.insert(name, authorId, genreId);
    }

    @ShellMethod(key = "update", value = "Update book by <Book id> with <Book name> <Author id> <Genre id>")
    public void update(@ShellOption("id") Long id, @ShellOption("name") String name, @ShellOption("authorId") Long authorId, @ShellOption("genreId") Long genreId) {
        bookRunnerService.update(id, name, authorId, genreId);
    }

    @ShellMethod(key = "delete", value = "Delete book by <id>")
    public void deleteById(@ShellOption("id") Long id) {
        bookRunnerService.delete(id);
    }

    @ShellMethod(key = "show all", value = "Show all books")
    public void showAll() {
        bookRunnerService.printAll();
    }

    @ShellMethod(key = "show", value = "Show book by <id>")
    public void showById(@ShellOption("id") Long id) {
        bookRunnerService.printById(id);
    }

    @ShellMethod(key = "add comment", value = "Add comment for <Book id> with <Text>")
    public void addComment(@ShellOption("book_id") Long bookId, @ShellOption("text") String text) {
        commentRunnerService.insert(bookId, text);
    }

    @ShellMethod(key = "edit comment", value = "Edit comment by <id> with <Book id>, <Text>")
    public void editComment(@ShellOption("id") Long id, @ShellOption("book_id") Long bookId, @ShellOption("text") String text) {
        commentRunnerService.update(id, bookId, text);
    }

    @ShellMethod(key = "remove comment", value = "Remove comment by <id>")
    public void removeComment(@ShellOption("id") Long id) {
        commentRunnerService.delete(id);
    }

    @ShellMethod(key = "show all comments", value = "Show all comments")
    public void showAllComments() {
        commentRunnerService.printAll();
    }

    @ShellMethod(key = "show comment", value = "Show comment by <id>")
    public void showCommentById(@ShellOption("id") Long id) {
        commentRunnerService.printById(id);
    }
}
