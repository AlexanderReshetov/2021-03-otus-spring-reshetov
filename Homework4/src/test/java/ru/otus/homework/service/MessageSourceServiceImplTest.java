package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import ru.otus.homework.config.LocaleConfig;

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

    private MessageSourceService messageSourceService;

    @Test
    @DisplayName("запустить метод getMessage класса MessageSource")
    public void shouldExecuteGetMessageMethod() {
        final String messageName = "testMessage";
        given(localeConfig.getName()).willReturn("en");
        messageSourceService = new MessageSourceServiceImpl(messageSource, localeConfig);

        messageSourceService.getMessage(messageName, null);

        verify(messageSource).getMessage(eq(messageName), isNull(), any());
    }
}
