package ru.otus.homework.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс TestResult должен")
public class TestResultTest {
    private static final int QUESTION_CREDIT_COUNT = 5;
    private static final int COUNT_CORRECT_ANSWERS = 4;
    private static final int COUNT_QUESTIONS = 5;

    @Test
    @DisplayName("корректно создаться конструктором")
    void shouldHaveCorrectConstructor() {
        final Person person = new Person("TestSurname", "TestName");
        final TestResult testResult = new TestResult(person, QUESTION_CREDIT_COUNT, COUNT_CORRECT_ANSWERS, COUNT_QUESTIONS);

        assertEquals(testResult.getPerson(), person);
        assertEquals(testResult.getQuestionCreditCount(), QUESTION_CREDIT_COUNT);
        assertEquals(testResult.getCountCorrectAnswers(), COUNT_CORRECT_ANSWERS);
        assertEquals(testResult.getCountQuestions(), COUNT_QUESTIONS);
    }
}
