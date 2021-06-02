package ru.otus.homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.domain.Question;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс QuestionDaoImpl")
public class QuestionDaoTest {
    @Test
    @DisplayName("должен получить список вопросов")
    void shouldHaveCorrectFindAllMethod() throws QuestionReadException {
        Question[] questions = new Question[] {new Question("2+2", "4"), new Question("2*2", "4"), new Question("1*5", "5"), new Question("500*1000", "500000"), new Question("15*8", "120")};
        List<Question> list = Arrays.asList(questions);
        QuestionDaoImpl questionDao = new QuestionDaoImpl("question.csv");
        List<Question> foundList = questionDao.findAll();

        assertEquals(list, foundList);
    }
}
