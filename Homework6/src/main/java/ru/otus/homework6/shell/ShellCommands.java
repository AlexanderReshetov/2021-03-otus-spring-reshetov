package ru.otus.homework6.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework6.service.BookService;

@ShellComponent
public class ShellCommands {
    private final BookService bookService;

    public ShellCommands(BookService bookService) {
        this.bookService = bookService;
    }

    @ShellMethod(key = "insert", value = "Insert book with <Book id> <Book name> <Author id> <Genre id>")
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
}
