package ru.otus.homework.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.commands.Quit;

@ShellComponent
public class QuitCommand implements Quit.Command{
    @ShellMethod("Quit from an application")
    public void quit() {
        System.exit(0);
    }
}
