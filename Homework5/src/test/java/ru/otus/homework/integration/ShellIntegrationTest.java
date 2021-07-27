package ru.otus.homework.integration;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework.config.QuestionConfig;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.domain.Question;
import ru.otus.homework.service.MessageSourceIOService;
import ru.otus.homework.service.TestRunnerService;
import ru.otus.homework.service.TestService;
import ru.otus.homework.service.exception.MessageSourceIOServiceException;
import ru.otus.homework.shell.ShellModeCommands;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@DisplayName("В интеграционном тесте shell команды должны")
public class ShellIntegrationTest {
    @MockBean
    private ApplicationRunner applicationRunner;
    @MockBean
    private TestRunnerService testRunnerService;
    @MockBean
    private MessageSourceIOService messageSourceIOService;
    @Autowired
    private TestService testService;
    @Autowired
    private QuestionConfig questionConfig;
    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private ShellModeCommands shellModeCommands;
    @Autowired
    private Shell shell;

    @Order(1)
    @Test
    @DisplayName("запускать тестирование")
    void shouldRunTest() {
        shell.evaluate(() -> "run test");

        verify(testRunnerService).run();
    }

    @Order(2)
    @Test
    @DisplayName("запускать тестирование с предварительным указанием фамилии и имени")
    void shouldRunTestFor() {
        shell.evaluate(() -> "run test for Testing Student");

        verify(testRunnerService).run("Testing", "Student");
    }

    @Order(3)
    @Test
    @DisplayName("запускать тестирование в shell режиме")
    void shouldRunTestShell() {
        List<Question> questionList = questionDao.findAll();

        shell.evaluate(() -> "run test shell");

        verify(messageSourceIOService).println("message.output.shell.activate");
        assertEquals(questionList, shellModeCommands.getQuestionList());
        assertTrue(shellModeCommands.isShellModeAvailable().isAvailable());
    }

    @Order(4)
    @Test
    @DisplayName("заполнять личные данные пользователя")
    void shouldInputPersonData() {
        shell.evaluate(() -> "p Testing Student");

        assertEquals("Testing", shellModeCommands.getPerson().getSurname());
        assertEquals("Student", shellModeCommands.getPerson().getName());
    }

    @Order(5)
    @Test
    @DisplayName("запрашивать следующий тестовый вопрос")
    void shouldAskNextQuestion() throws MessageSourceIOServiceException {
        given(messageSourceIOService.readLine())
                .willReturn(shellModeCommands.getQuestionList().get(0).getAnswer())
                .willReturn(shellModeCommands.getQuestionList().get(1).getAnswer())
                .willReturn(shellModeCommands.getQuestionList().get(2).getAnswer());

        shell.evaluate(() -> "q next");
        shell.evaluate(() -> "q next");
        shell.evaluate(() -> "q next");

        assertEquals(shellModeCommands.getQuestionList().get(0).getAnswer(), shellModeCommands.getQuestionList().get(0).getUserAnswer());
        assertEquals(shellModeCommands.getQuestionList().get(1).getAnswer(), shellModeCommands.getQuestionList().get(1).getUserAnswer());
        assertEquals(shellModeCommands.getQuestionList().get(2).getAnswer(), shellModeCommands.getQuestionList().get(2).getUserAnswer());
    }

    @Order(6)
    @Test
    @DisplayName("запрашивать тестовый вопрос по номеру")
    void shouldAskQuestionByNumber() throws MessageSourceIOServiceException {
        given(messageSourceIOService.readLine())
                .willReturn(shellModeCommands.getQuestionList().get(3).getAnswer())
                .willReturn(shellModeCommands.getQuestionList().get(4).getAnswer());

        shell.evaluate(() -> "q 4");
        shell.evaluate(() -> "q 5");

        assertEquals(shellModeCommands.getQuestionList().get(3).getAnswer(), shellModeCommands.getQuestionList().get(3).getUserAnswer());
        assertEquals(shellModeCommands.getQuestionList().get(4).getAnswer(), shellModeCommands.getQuestionList().get(4).getUserAnswer());
    }

    @Order(7)
    @Test
    @DisplayName("завершать тестирование и выводить результаты")
    void shouldFinishTestAndPrintResults() {
        shell.evaluate(() -> "finish");

        verify(messageSourceIOService).println("message.output.person",
                shellModeCommands.getPerson().getName(), shellModeCommands.getPerson().getSurname());
        verify(messageSourceIOService).println("message.output.answer.correct.count", 5, 5);
        verify(messageSourceIOService).println("message.output.answer.credit.count",
                questionConfig.getCredit().getCount());
        verify(messageSourceIOService).println("message.output.congratulation");
    }
}
