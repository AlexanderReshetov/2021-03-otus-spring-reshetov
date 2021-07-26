package ru.otus.homework.integration;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.homework.config.QuestionConfig;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.domain.Person;
import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.TestResult;
import ru.otus.homework.service.*;
import ru.otus.homework.service.exception.MessageSourceIOServiceException;
import ru.otus.homework.service.exception.PersonInputDataException;
import ru.otus.homework.service.exception.TestServiceException;

import java.util.List;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@DisplayName("В интеграционном тесте сервис тестирования должен")
public class IntegrationTest {
    @MockBean
    private ApplicationRunner applicationRunner;
    @MockBean
    private MessageSourceIOService messageSourceIOService;
    @Autowired
    private PersonInputDataService personInputDataService;
    @Autowired
    private TestService testService;
    @Autowired
    private QuestionConfig questionConfig;
    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private TestResultOutputService testResultOutputService;

    private Person person;
    private TestResult testResult;

    private final static String TEST_SURNAME = "TestSurname";
    private final static String TEST_NAME = "TestName";

    @Order(1)
    @Test
    @DisplayName("получить данные пользователя")
    void shouldGetPersonData() throws MessageSourceIOServiceException, PersonInputDataException {
        given(messageSourceIOService.readLine())
                .willReturn(TEST_SURNAME)
                .willReturn(TEST_NAME);

        person = personInputDataService.inputData();

        assertEquals(TEST_SURNAME, person.getSurname());
        assertEquals(TEST_NAME, person.getName());
    }

    @Order(2)
    @Test
    @DisplayName("получить ответы на тестовые вопросы")
    void shouldGetAnswersForTestQuestions() throws MessageSourceIOServiceException, TestServiceException {
        List<Question> questionList = questionDao.findAll();
        given(messageSourceIOService.readLine())
                .willReturn(questionList.get(0).getAnswer())
                .willReturn(questionList.get(1).getAnswer())
                .willReturn(questionList.get(2).getAnswer())
                .willReturn(questionList.get(3).getAnswer())
                .willReturn(questionList.get(4).getAnswer());

        testResult = testService.test(person, questionConfig.getCredit().getCount());

        assertEquals(TEST_SURNAME, testResult.getPerson().getSurname());
        assertEquals(TEST_NAME, testResult.getPerson().getName());
        assertEquals(questionConfig.getCredit().getCount(), testResult.getQuestionCreditCount());
        assertEquals(questionList.size(), testResult.getCountCorrectAnswers());
        assertEquals(questionList.size(), testResult.getCountQuestions());
    }

    @Order(3)
    @Test
    @DisplayName("вывести результаты теста")
    void shouldPrintTestResults() {
        testResultOutputService.output(testResult);

        verify(messageSourceIOService).println("message.output.person",
                testResult.getPerson().getName(), testResult.getPerson().getSurname());
        verify(messageSourceIOService).println("message.output.answer.correct.count",
                testResult.getCountCorrectAnswers(), testResult.getCountQuestions());
        verify(messageSourceIOService).println("message.output.answer.credit.count",
                testResult.getQuestionCreditCount());
        verify(messageSourceIOService).println("message.output.congratulation");
    }
}
