package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import ru.otus.homework.config.LocaleConfig;

import java.util.Locale;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис-обертка над MessageSource должен")
public class MessageSourceServiceImplTest {
    @Mock
    private MessageSource messageSource;
    @Mock
    private LocaleConfig localeConfig;

    private MessageSourceServiceImpl messageSourceService;

    @Test
    @DisplayName("запустить метод getMessage класса MessageSource")
    public void shouldExecuteGetMessageMethod() {
        final String messageName = "testMessage";
        final Locale locale = new Locale("en");
        given(localeConfig.getLocale()).willReturn(locale);
        messageSourceService = new MessageSourceServiceImpl(messageSource, localeConfig);

        messageSourceService.getMessage(messageName, null);

        verify(messageSource).getMessage(eq(messageName), isNull(), any());
    }
}
