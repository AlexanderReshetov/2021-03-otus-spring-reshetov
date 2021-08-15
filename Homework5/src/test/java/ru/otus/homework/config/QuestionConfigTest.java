package ru.otus.homework.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("Класс конфигурации данных зачета QuestionConfig должен")
public class QuestionConfigTest {
    @Mock
    private LocaleConfig localeConfig;

    @Test
    @DisplayName("корректно установить имя и расширение файла")
    void shouldHaveCorrectFileAndExtension() {
        final String fileName = "question";
        final String fileExtension = "csv";
        final QuestionConfig.File file = new QuestionConfig.File();

        file.setName(fileName);
        file.setExtension(fileExtension);
        QuestionConfig questionConfig = new QuestionConfig(file, null);

        assertEquals(questionConfig.getFile().getName(), fileName);
        assertEquals(questionConfig.getFile().getExtension(), fileExtension);
    }

    @Test
    @DisplayName("рассчитать локализованное имя файла")
    void shouldCalculateLocalizedFileName() {
        final String fileName = "question";
        final String fileExtension = "csv";
        final QuestionConfig.File file = new QuestionConfig.File();
        final String localeName = "ru";

        file.setName(fileName);
        file.setExtension(fileExtension);
        QuestionConfig questionConfig = new QuestionConfig(file, null);
        questionConfig.setLocaleConfig(localeConfig);
        given(localeConfig.getName()).willReturn(localeName);

        assertEquals(questionConfig.getLocalizedFileName(), fileName + "_" + localeName + "." + fileExtension);
    }

    @Test
    @DisplayName("корректно установить количество вопросов для получения зачета")
    void shouldHaveCorrectCreditCount() {
        final int creditCount = 5;
        final QuestionConfig.Credit credit = new QuestionConfig.Credit();

        credit.setCount(creditCount);
        QuestionConfig questionConfig = new QuestionConfig(null, credit);

        assertEquals(questionConfig.getCredit().getCount(), creditCount);
    }

}
