package ru.otus.homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Класс QuestionDaoImpl")
public class QuestionDaoTest {
    @Test
    @DisplayName("должен получить список вопросов")
    void shouldHaveCorrectFindAllMethod() throws Exception {
        QuestionDaoImpl questionDao = new QuestionDaoImpl("question.csv");
        List<Question> list = questionDao.findAll();

        assertNotNull(list);
    }
}
