package ru.otus.homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс QuestionDaoImpl")
public class QuestionDaoTest {
    @Test
    @DisplayName("должен получить список вопросов")
    void shouldHaveCorrectFindAllMethod() throws QuestionReadException {
        QuestionDaoImpl questionDao = new QuestionDaoImpl("question.csv");
        List<Question> list = questionDao.findAll();

        assertNotNull(list);
        assertEquals(list.size(), 5);
        assertEquals(list.get(0).getText(),"2+2");
        assertEquals(list.get(0).getAnswer(),"4");
        assertEquals(list.get(1).getText(),"2*2");
        assertEquals(list.get(1).getAnswer(),"4");
        assertEquals(list.get(2).getText(),"1*5");
        assertEquals(list.get(2).getAnswer(),"5");
        assertEquals(list.get(3).getText(),"500*1000");
        assertEquals(list.get(3).getAnswer(),"500000");
        assertEquals(list.get(4).getText(),"15*8");
        assertEquals(list.get(4).getAnswer(),"120");
    }
}
