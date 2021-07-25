package ru.otus.homework.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.service.TestRunnerService;

@ShellComponent
public class ShellRunCommands {
    private final TestRunnerService testRunnerService;
    private final ShellModeCommands shellModeCommands;

    public ShellRunCommands(TestRunnerService testRunnerService, ShellModeCommands shellModeCommands) {
        this.testRunnerService = testRunnerService;
        this.shellModeCommands = shellModeCommands;
    }

    @ShellMethod(key = "run test", value = "Run a testing application")
    public void runTest() {
        testRunnerService.run();
    }

    @ShellMethod(key = "run test for", value = "Run a testing application for <Lastname> <Firstname>")
    public void run(@ShellOption("lastname") String surname, @ShellOption("firstname") String name) {
        testRunnerService.run(surname, name);
    }

    @ShellMethod(key = "run test shell", value = "Run a testing application in shell mode")
    public void runShell() {
        shellModeCommands.activateShellMode();
    }
}
