package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.homework.config.LocaleConfig;

@Service
public class MessageSourceServiceImpl implements MessageSourceService{
    private final MessageSource messageSource;
    private final LocaleConfig localeConfig;

    @Autowired
    MessageSourceServiceImpl(MessageSource messageSource, LocaleConfig localeConfig) {
        this.messageSource = messageSource;
        this.localeConfig = localeConfig;
    }

    public String getMessage(String name, Object...varArgs) {
        return messageSource.getMessage(name, varArgs, localeConfig.getLocale());
    }
}
