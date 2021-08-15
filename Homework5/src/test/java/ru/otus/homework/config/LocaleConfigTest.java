package ru.otus.homework.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс конфигурации локали LocaleConfig должен")
public class LocaleConfigTest {
    private LocaleConfig localeConfig;

    @Test
    @DisplayName("корректно установить имя локали")
    void shouldHaveCorrectNameSetter() {
        final String testName = "testName";
        localeConfig = new LocaleConfig();

        localeConfig.setName(testName);

        assertEquals(localeConfig.getName(), testName);
    }

    @Test
    @DisplayName("корректно создать локаль")
    void shouldCreateLocale() {
        final String testName = "ru";
        localeConfig = new LocaleConfig();

        localeConfig.setName(testName);

        assertEquals(localeConfig.getLocale().getLanguage(), testName);
    }
}
