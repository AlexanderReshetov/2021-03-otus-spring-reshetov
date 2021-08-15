package ru.otus.homework.shell;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.config.QuestionConfig;
import ru.otus.homework.service.MessageSourceIOService;
import ru.otus.homework.service.TestResultOutputService;
import ru.otus.homework.service.TestService;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Spring Shell команды тестирования должны")
public class ShellModeCommandsTest {
    @Mock
    private QuestionConfig questionConfig;
    @Mock
    private TestService testService;
    @Mock
    private MessageSourceIOService messageSourceIOService;
    @Mock
    private TestResultOutputService testResultOutputService;
    @Mock
    private QuestionConfig.Credit credit;

    private ShellModeCommands shellModeCommands;

    private static final int QUESTION_CREDIT_COUNT = 5;

    @BeforeEach
    void setUp() {
        given(questionConfig.getCredit()).willReturn(credit);
        given(credit.getCount()).willReturn(QUESTION_CREDIT_COUNT);
        shellModeCommands = new ShellModeCommands(questionConfig, testService, messageSourceIOService, testResultOutputService);
    }

    @Test
    @DisplayName("активировать shell режим")
    void shouldActivateShellMode() {
        shellModeCommands.activateShellMode();

        verify(messageSourceIOService).println("message.output.shell.activate");
        verify(testService).getQuestionList();
        assertTrue(shellModeCommands.isShellModeAvailable().isAvailable());
    }

    @Test
    @DisplayName("заполнять личные данные пользователя")
    void shouldInputPersonData() {
        shellModeCommands.activateShellMode();
        shellModeCommands.inputPerson("TestSurname", "TestName");

        assertTrue(shellModeCommands.isPersonDataAvailable().isAvailable());
    }

    @Test
    @DisplayName("запрашивать следующий тестовый вопрос")
    void shouldAskNextQuestion() {
        shellModeCommands.answerNextQuestion();

        verify(testService).answerNextQuestion(any());
    }

    @Test
    @DisplayName("запрашивать тестовый вопрос по номеру")
    void shouldAskQuestionByNumber() {
        shellModeCommands.answerQuestionByNumber(1);

        verify(testService).answerQuestionByNumber(any(), eq(0));
    }

    @Test
    @DisplayName("завершать тестирование и выводить результаты")
    void shouldFinishTestAndPrintResults() {
        shellModeCommands.finishTest();

        verify(testService).testFromList(any(), any(), eq(QUESTION_CREDIT_COUNT));
        verify(testResultOutputService).output(any());
    }
}
