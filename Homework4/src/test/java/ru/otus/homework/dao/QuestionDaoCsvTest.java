package ru.otus.homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.config.QuestionConfig;
import ru.otus.homework.dao.exception.QuestionReadException;
import ru.otus.homework.domain.Question;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("Класс QuestionDaoCsv должен")
public class QuestionDaoCsvTest {
    @Mock
    private QuestionConfig questionConfig;

    @Test
    @DisplayName("получить список вопросов")
    void shouldHaveCorrectFindAllMethod() throws QuestionReadException {
        final Question[] questions = new Question[] {new Question("2+2", "4"), new Question("2*2", "4"), new Question("1*5", "5"), new Question("500*1000", "500000"), new Question("15*8", "120")};
        final List<Question> list = Arrays.asList(questions);
        given(questionConfig.getLocalizedFileName()).willReturn("question.csv");
        final QuestionDao questionDao = new QuestionDaoCsv(questionConfig);

        List<Question> foundList = questionDao.findAll();

        assertEquals(list, foundList);
    }

    @Test
    @DisplayName("выбросить QuestionReadException при отсутствии файла вопросов")
    void shouldThrowQuestionReadExceptionIfFileNotExists() throws QuestionReadException {
        given(questionConfig.getLocalizedFileName()).willReturn("non-exists-file.csv");
        final QuestionDao questionDao = new QuestionDaoCsv(questionConfig);

        assertThrows(QuestionReadException.class, () -> questionDao.findAll());
    }
}
