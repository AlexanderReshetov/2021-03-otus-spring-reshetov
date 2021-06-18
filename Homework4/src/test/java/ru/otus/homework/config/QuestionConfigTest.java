package ru.otus.homework.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс конфигурации данных зачета QuestionConfig должен")
public class QuestionConfigTest {
    private QuestionConfig questionConfig;

    @Test
    @DisplayName("корректно установить путь к файлу вопросов")
    public void shouldHaveCorrectFile() {
        final String fileName = "question";
        final String fileExtension = "csv";
        final QuestionConfig questionConfig = new QuestionConfig();
        final QuestionConfig.File file = new QuestionConfig.File();

        file.setName(fileName);
        file.setExtension(fileExtension);
        questionConfig.setFile(file);

        assertEquals(questionConfig.getFile().getName(), fileName);
        assertEquals(questionConfig.getFile().getExtension(), fileExtension);
    }

    @Test
    @DisplayName("корректно установить количество вопросов для получения зачета")
    public void shouldHaveCorrectCreditCount() {
        final int creditCount = 5;
        final QuestionConfig questionConfig = new QuestionConfig();
        final QuestionConfig.Credit credit = new QuestionConfig.Credit();

        credit.setCount(creditCount);
        questionConfig.setCredit(credit);

        assertEquals(questionConfig.getCredit().getCount(), creditCount);
    }
}
