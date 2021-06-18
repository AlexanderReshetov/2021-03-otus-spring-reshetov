package ru.otus.homework.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.config.LocaleConfig;
import ru.otus.homework.config.QuestionConfig;
import ru.otus.homework.domain.Question;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("Класс QuestionDaoImpl должен")
public class QuestionDaoImplTest {
    @Mock
    private QuestionConfig questionConfig;
    @Mock
    private LocaleConfig localeConfig;
    @Mock
    private QuestionConfig.File file;

    @Test
    @DisplayName("получить список вопросов")
    void shouldHaveCorrectFindAllMethod() throws QuestionReadException {
        final Question[] questions = new Question[] {new Question("2+2", "4"), new Question("2*2", "4"), new Question("1*5", "5"), new Question("500*1000", "500000"), new Question("15*8", "120")};
        final List<Question> list = Arrays.asList(questions);
        given(questionConfig.getFile()).willReturn(file);
        given(file.getName()).willReturn("question");
        given(file.getExtension()).willReturn("csv");
        given(localeConfig.getName()).willReturn("");
        final QuestionDao questionDao = new QuestionDaoImpl(questionConfig, localeConfig);

        List<Question> foundList = questionDao.findAll();

        assertEquals(list, foundList);
    }

    @Test
    @DisplayName("выбросить QuestionReadException при отсутствии файла вопросов")
    void shouldThrowQuestionReadExceptionIfFileNotExists() throws QuestionReadException {
        given(questionConfig.getFile()).willReturn(file);
        given(file.getName()).willReturn("non-exists-file");
        given(file.getExtension()).willReturn("csv");
        given(localeConfig.getName()).willReturn("");
        final QuestionDao questionDao = new QuestionDaoImpl(questionConfig, localeConfig);

        assertThrows(QuestionReadException.class, () -> questionDao.findAll());
    }
}
