package ru.otus.homework.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.service.TestRunnerService;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Spring Shell команды запуска должны")
public class ShellRunCommandsTest {
    @Mock
    private TestRunnerService testRunnerService;
    @Mock
    private ShellModeCommands shellModeCommands;
    @InjectMocks
    private ShellRunCommands shellRunCommands;

    @Test
    @DisplayName("запускать тестирование")
    void shouldRunTest() {
        shellRunCommands.runTest();

        verify(testRunnerService).run();
    }

    @Test
    @DisplayName("запускать тестирование с предварительным указанием фамилии и имени")
    void shouldRunTestFor() {
        final String testSurname = "TestSurname";
        final String testName = "TestName";

        shellRunCommands.run(testSurname, testName);

        verify(testRunnerService).run(testSurname, testName);
    }

    @Test
    @DisplayName("запускать тестирование в shell режиме")
    void shouldRunTestShell() {
        shellRunCommands.runShell();

        verify(shellModeCommands).activateShellMode();
    }
}