package ru.otus.homework.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DisplayName("Класс Question должен")
public class QuestionTest {
    private static final String TEST_TEXT = "Text";
    private static final String TEST_TEXT2 = "Text2";
    private static final String TEST_ANSWER = "Answer";
    private static final String TEST_ANSWER2 = "Answer2";

    @Test
    @DisplayName("корректно создаться конструктором")
    void shouldHaveCorrectConstructor() {
        final Question question = question();

        assertEquals(question.getText(), TEST_TEXT);
        assertEquals(question.getAnswer(), TEST_ANSWER);
    }

    @Test
    @DisplayName("корректно сравнить объекты")
    void shouldHaveCorrectEqualsMethod () {
        final Question question = question();
        final Question questionEqual = new Question(TEST_TEXT, TEST_ANSWER);
        final Question questionDiffByText = new Question(TEST_TEXT2, TEST_ANSWER);
        final Question questionDiffByAnswer = new Question(TEST_TEXT, TEST_ANSWER2);
        final Question questionDiffByTextAndAnswer = new Question(TEST_TEXT2, TEST_ANSWER2);

        assertEquals(question, questionEqual);
        assertNotEquals(question, questionDiffByText);
        assertNotEquals(question, questionDiffByAnswer);
        assertNotEquals(question, questionDiffByTextAndAnswer);
    }

    private Question question() {
        return new Question(TEST_TEXT, TEST_ANSWER);
    }
}
